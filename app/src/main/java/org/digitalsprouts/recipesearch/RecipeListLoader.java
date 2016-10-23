package org.digitalsprouts.recipesearch;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import org.digitalsprouts.recipesearch.imageloader.PicassoImageLoader;
import org.digitalsprouts.recipesearchclient.RecipeSearchClient;
import org.digitalsprouts.recipesearchclient.model.Recipe;
import org.digitalsprouts.recipesearchclient.model.RecipeSearchResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static org.digitalsprouts.recipesearchclient.BuildConfig.F2F_API_KEY;

class RecipeListLoader {

    private final View busyIndicator;
    private final RecipeSearchClient searchServiceClient;
    private final Context context;
    private RecipeListAdapter recipeListadapter;
    private List<Recipe> recipeData;
    private static final String TAG = "RecipeListLoader";

    RecipeListLoader(@NonNull Context context, @NonNull RecyclerView recyclerView, @NonNull View busyIndicator, @NonNull OnRecipeRowClickListener clickListener, @NonNull List<Recipe> recipeData) {
        this.busyIndicator = busyIndicator;
        this.searchServiceClient = new RecipeSearchClient(F2F_API_KEY);
        this.recipeData = recipeData;
        this.context = context;

        this.recipeListadapter = new RecipeListAdapter(new PicassoImageLoader(context), this.recipeData, clickListener);

        recyclerView.setAdapter(recipeListadapter);
    }

    /*
    Loads a new recipe list from the network, based on the query string
     */
    void loadRecipeList(String queryString) {
        if (TextUtils.isEmpty(queryString)) {
            return;
        }

        busyIndicator.setVisibility(View.VISIBLE);

        searchServiceClient.searchForRecipes(queryString, new Callback<RecipeSearchResponse>() {

            @Override
            public void onResponse(Call<RecipeSearchResponse> call, Response<RecipeSearchResponse> response) {
                busyIndicator.setVisibility(View.GONE);

                if (!response.isSuccessful()) {
                    reportSearchError("Server returned an error");

                    Log.e(TAG, String.format("Error response in recipe search: %1$s (%2$d)", response.message(), response.code()));
                    return;
                }

                setRecipeDataFromResponse(response.body().getRecipes());
            }

            @Override
            public void onFailure(Call<RecipeSearchResponse> call, Throwable t) {
                busyIndicator.setVisibility(View.GONE);

                reportSearchError(t.getMessage());
                Log.e(TAG, "Recipe search error", t);
            }

        });
    }

    private void setRecipeDataFromResponse(final List<Recipe> recipeList) {
        recipeData.clear();
        recipeData.addAll(recipeList);

        recipeListadapter.notifyDataSetChanged();
    }

    private void reportSearchError(final String message) {
        // A production app would have more customer friendly error handling, perhaps retry requests,
        // errors could be logged remotely, Crashlytics etc
        Toast.makeText(context, "Search failure:\n" + message, Toast.LENGTH_LONG).show();
    }

}
