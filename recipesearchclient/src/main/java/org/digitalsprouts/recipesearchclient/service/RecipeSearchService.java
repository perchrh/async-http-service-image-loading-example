package org.digitalsprouts.recipesearchclient.service;

import org.digitalsprouts.recipesearchclient.model.RecipeSearchResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface RecipeSearchService {

    @GET("search")
    Call<RecipeSearchResponse> queryByString(@Query("key") String apiKey, @Query("q") String query, @Query("sort") String sort, @Query("page") Integer page);

}

