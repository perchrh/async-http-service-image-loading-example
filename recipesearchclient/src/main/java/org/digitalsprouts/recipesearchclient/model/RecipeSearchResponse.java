package org.digitalsprouts.recipesearchclient.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class RecipeSearchResponse {

    @SerializedName("count")
    private Long count; // Number of recipes in result (Max 30)

    @SerializedName("recipes")
    private List<Recipe> recipes; // List of Recipes

    public long getCount() {
        return count;
    }

    public List<Recipe> getRecipes() {
        return recipes;
    }
}
