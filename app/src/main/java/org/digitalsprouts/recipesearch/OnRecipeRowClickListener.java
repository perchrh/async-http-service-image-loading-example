package org.digitalsprouts.recipesearch;

import android.support.annotation.NonNull;

import org.digitalsprouts.recipesearchclient.model.Recipe;

interface OnRecipeRowClickListener {
    void onItemClick(@NonNull Recipe item);
}
