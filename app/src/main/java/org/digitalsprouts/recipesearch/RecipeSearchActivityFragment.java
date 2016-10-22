package org.digitalsprouts.recipesearch;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.digitalsprouts.recipesearch.imageloader.ImageLoader;
import org.digitalsprouts.recipesearch.imageloader.PicassoImageLoader;
import org.digitalsprouts.recipesearchclient.BuildConfig;
import org.digitalsprouts.recipesearchclient.RecipeSearchClient;
import org.digitalsprouts.recipesearchclient.model.Recipe;
import org.digitalsprouts.recipesearchclient.model.RecipeSearchResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RecipeSearchActivityFragment extends Fragment {

    private static final String TAG = "RecipeListActivity";
    private EditText queryInputView;
    private RecyclerView recyclerView;
    private ImageLoader imageLoader;

    private OnRecipeRowClickListener clickListener = new OnRecipeRowClickListener() {
        @Override
        public void onItemClick(Recipe item) {
            Intent showRecipeDetails = new StartRecipeDetailActivityIntent(getContext(), item);
            startActivity(showRecipeDetails);
        }
    };

    private RecipeSearchClient searchServiceClient;
    private RecipeListAdapter recipeListadapter;

    private TextView.OnEditorActionListener submitQueryListener = new TextView.OnEditorActionListener() {
        @Override
        public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                String queryString = queryInputView.getText().toString();
                submitSearch(queryString);
                return true;
            }
            return false;
        }
    };

    public RecipeSearchActivityFragment() {
        // required fragment constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_recipe_search, container, false);

        initViews(rootView);

        return rootView;
    }

    private void initViews(final View rootView) {
        queryInputView = (EditText) rootView.findViewById(R.id.recipe_search_query);
        queryInputView.setImeActionLabel(getString(R.string.recipe_search_submit), KeyEvent.KEYCODE_ENTER);
        queryInputView.setOnEditorActionListener(submitQueryListener);

        recyclerView = (RecyclerView) rootView.findViewById(R.id.recipe_recycler_view);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);

        // Could add endless scrolling, using pagination
        // recyclerView.addOnScrollListener
        // http://stackoverflow.com/questions/26543131/how-to-implement-endless-list-with-recyclerview
    }

    private void submitSearch(final String queryString) {
        if (TextUtils.isEmpty(queryString)) {
            return;
        }
        // TODO Could check network connectivity before searching

        Toast.makeText(getContext(), "I'm searching for : " + queryString, Toast.LENGTH_LONG).show();

        searchServiceClient.searchForRecipes(queryString, new Callback<RecipeSearchResponse>() {

            @Override
            public void onResponse(Call<RecipeSearchResponse> call, Response<RecipeSearchResponse> response) {
                if (!response.isSuccessful()) {
                    reportSearchError("Server returned an error");
                    Log.e(TAG, String.format("Error response in recipe search: %1$s (%2$d)", response.message(), response.code()));
                    return;
                }

                setRecipeDataFromResponse(response.body().getRecipes());
            }

            private void setRecipeDataFromResponse(final List<Recipe> recipeData) {
                recipeListadapter = new RecipeListAdapter(imageLoader, recipeData, clickListener);
                recyclerView.setAdapter(recipeListadapter);
            }

            @Override
            public void onFailure(Call<RecipeSearchResponse> call, Throwable t) {
                reportSearchError(t.getMessage());
                Log.e(TAG, "Recipe search error", t);
            }

            private void reportSearchError(String message) {
                // TODO a production app would have more customer friendly error handling, perhaps retry requests,
                // errors could be logged remotely, Crashlytics etc
                Toast.makeText(getContext(), "Search failure:\n" + message, Toast.LENGTH_LONG).show();
            }
        });
    }

    @NonNull
    private ImageLoader createDownloader(Context context) {
        return new PicassoImageLoader(context);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.searchServiceClient = new RecipeSearchClient(BuildConfig.F2F_API_KEY);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        // When the context changes, we must re-create the ImageLoader
        imageLoader = createDownloader(context);
        if (recipeListadapter != null) {
            // We were re-attached after initial create
            recipeListadapter.setImageLoader(imageLoader);
            // TODO see if this state-management cannot be removed after a redesign
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();

        // Make sure we clear away the no longer valid context
        imageLoader = null;
        recipeListadapter.setImageLoader(null);
    }

}
