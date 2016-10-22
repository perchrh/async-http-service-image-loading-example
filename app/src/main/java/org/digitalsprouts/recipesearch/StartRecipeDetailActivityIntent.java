package org.digitalsprouts.recipesearch;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;

import org.digitalsprouts.recipesearchclient.model.Recipe;

class StartRecipeDetailActivityIntent extends Intent {

    static final String EXTRA_KEY_RECIPE_ITEM = "EXTRA_KEY_RECIPE_ITEM";

    StartRecipeDetailActivityIntent(@NonNull Context context, @NonNull Recipe detailItem) {
        super(context, RecipeDetailActivity.class);
        putExtra(EXTRA_KEY_RECIPE_ITEM, detailItem);
    }

}
