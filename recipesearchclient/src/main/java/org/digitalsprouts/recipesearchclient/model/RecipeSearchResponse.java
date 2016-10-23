package org.digitalsprouts.recipesearchclient.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class RecipeSearchResponse {

    @SerializedName("count")
    private Long count; // Number of recipes in result (Max 30)

    @SerializedName("recipes")
    private ArrayList<Recipe> recipes; // List of Recipes

    public long getCount() {
        return count;
    }

    // Using ArrayList to be compatible with Android's parcelable APIs
    public ArrayList<Recipe> getRecipes() {
        return recipes;
    }
}
