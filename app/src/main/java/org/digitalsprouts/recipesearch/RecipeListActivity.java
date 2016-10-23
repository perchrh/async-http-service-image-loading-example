package org.digitalsprouts.recipesearch;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import org.digitalsprouts.recipesearch.imageloader.ImageLoader;
import org.digitalsprouts.recipesearch.imageloader.PicassoImageLoader;
import org.digitalsprouts.recipesearchclient.RecipeSearchClient;
import org.digitalsprouts.recipesearchclient.model.Recipe;
import org.digitalsprouts.recipesearchclient.model.RecipeSearchResponse;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static org.digitalsprouts.recipesearchclient.BuildConfig.*;

public class RecipeListActivity extends AppCompatActivity {

    private static final String TAG = "RecipeListActivity";
    private static final String STATE_MODEL = "MODEL";

    private EditText queryInputView;
    private RecyclerView recyclerView;
    private ImageLoader imageLoader;
    private View busyIndicator;
    private RecipeSearchClient searchServiceClient;
    private ArrayList<Recipe> recipeData; //using ArrayList type for Parcelable
    private RecipeListAdapter recipeListadapter;

    private OnRecipeRowClickListener clickListener = new OnRecipeRowClickListener() {
        @Override
        public void onItemClick(@NonNull Recipe item) {
            Intent showRecipeDetails = new StartRecipeDetailActivityIntent(RecipeListActivity.this, item);
            startActivity(showRecipeDetails);
        }
    };

    private View.OnKeyListener submitQueryListener = new View.OnKeyListener() {
        @Override
        public boolean onKey(View v, int keyCode, KeyEvent event) {
            if (event.getAction() == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_ENTER) {
                String queryString = queryInputView.getText().toString();
                submitSearch(queryString);
                return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_list);

        if (savedInstanceState != null) {
            // Restore saved state
            recipeData = savedInstanceState.getParcelableArrayList(STATE_MODEL);
        } else {
            recipeData = new ArrayList<>();
        }

        this.searchServiceClient = new RecipeSearchClient(F2F_API_KEY);
        this.imageLoader = new PicassoImageLoader(this);

        initViews();
    }


    private void initViews() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        busyIndicator = findViewById(R.id.busy_indicator);

        queryInputView = (EditText) findViewById(R.id.recipe_search_query);
        queryInputView.setImeActionLabel(getString(R.string.recipe_search_submit), KeyEvent.KEYCODE_ENTER);
        queryInputView.setOnKeyListener(submitQueryListener); //using keyListener to support submit by enter OR action search

        recyclerView = (RecyclerView) findViewById(R.id.recipe_recycler_view);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        this.recipeListadapter = new RecipeListAdapter(imageLoader, recipeData, clickListener);
        this.recyclerView.setAdapter(recipeListadapter);

        // Could add endless scrolling, using pagination
        // recyclerView.addOnScrollListener
        // http://stackoverflow.com/questions/26543131/how-to-implement-endless-list-with-recyclerview
    }

    private void submitSearch(final String queryString) {
        if (TextUtils.isEmpty(queryString)) {
            return;
        }
        // Could check network connectivity before searching

        busyIndicator.setVisibility(View.VISIBLE);

        searchServiceClient.searchForRecipes(queryString, new Callback<RecipeSearchResponse>() {

            @Override
            public void onResponse(Call<RecipeSearchResponse> call, Response<RecipeSearchResponse> response) {
                if (!response.isSuccessful()) {
                    reportSearchError("Server returned an error");

                    Log.e(TAG, String.format("Error response in recipe search: %1$s (%2$d)", response.message(), response.code()));
                    return;
                }

                setRecipeDataFromResponse(response.body().getRecipes());

                busyIndicator.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<RecipeSearchResponse> call, Throwable t) {
                busyIndicator.setVisibility(View.GONE);

                reportSearchError(t.getMessage());
                Log.e(TAG, "Recipe search error", t);
            }

        });
    }

    private void setRecipeDataFromResponse(final ArrayList<Recipe> recipeData) {
        this.recipeData = recipeData; //store state

        this.recipeListadapter = new RecipeListAdapter(imageLoader, recipeData, clickListener);
        this.recyclerView.setAdapter(recipeListadapter);
    }

    private void reportSearchError(final String message) {
        // TODO a production app would have more customer friendly error handling, perhaps retry requests,
        // errors could be logged remotely, Crashlytics etc
        Toast.makeText(this, "Search failure:\n" + message, Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList(STATE_MODEL, recipeData);
    }

}
