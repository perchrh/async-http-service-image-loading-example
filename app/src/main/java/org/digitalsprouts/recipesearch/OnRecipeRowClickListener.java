package org.digitalsprouts.recipesearch;

import org.digitalsprouts.recipesearchclient.model.Recipe;

interface OnRecipeRowClickListener {
    void onItemClick(Recipe item);
}
