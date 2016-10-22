package org.digitalsprouts.recipesearchclient;

import android.support.annotation.NonNull;

import org.digitalsprouts.recipesearchclient.model.RecipeSearchResponse;
import org.digitalsprouts.recipesearchclient.service.RecipeSearchService;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RecipeSearchClient {
    private static final String TAG = "RecipeSearchClient";
    private static final String BASE_URL = "http://food2fork.com/api/";
    private final Retrofit retroFit;
    private final String apiKey;
    private final RecipeSearchService service;

    public RecipeSearchClient(@NonNull final String apiKey) {
        this.apiKey = apiKey;
        this.retroFit = createRetrofitService();
        this.service = retroFit.create(RecipeSearchService.class);
    }

    /**
     * Search for recipes matching the queryTerm.
     *
     * @param queryTerm the query to search for
     * @param callback  the callback to execute when the results are ready or an error occurs
     * @TODO Selecting a sort mode for search is not implemented
     * @TODO Pagination is not supported
     */
    public void searchForRecipes(@NonNull String queryTerm, @NonNull Callback<RecipeSearchResponse> callback) {
        service.queryByString(apiKey, queryTerm, SortMode.RANK.sortSpec, null).enqueue(callback);
    }

    @NonNull
    private Retrofit createRetrofitService() {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BASIC);
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();

        final Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create());

        return builder.build();
    }

    public enum SortMode {
        RANK("r"),
        TREND("t");

        final String sortSpec;

        SortMode(final String sortSpec) {
            this.sortSpec = sortSpec;
        }
    }

}
