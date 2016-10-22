package org.digitalsprouts.recipesearch;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.digitalsprouts.recipesearch.imageloader.ImageLoader;
import org.digitalsprouts.recipesearchclient.model.Recipe;

import java.util.List;

public class RecipeListAdapter extends RecyclerView.Adapter<RecipeListAdapter.ViewHolder> {
    private final List<Recipe> recipes;
    private final ImageLoader imageLoader;
    private final OnRecipeRowClickListener clickListener;

    public RecipeListAdapter(ImageLoader imageLoader, List<Recipe> recipes, OnRecipeRowClickListener clickListener) {
        this.recipes = recipes;
        this.imageLoader = imageLoader;
        this.clickListener = clickListener;
    }

    @Override
    public RecipeListAdapter.ViewHolder onCreateViewHolder(final ViewGroup viewGroup, final int i) {
        final View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.recipe_list_row, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {
        Recipe recipe = recipes.get(position);

        String title = recipe.getTitle();
        viewHolder.recipeText.setText(title);

        String thumbnailUrl = recipes.get(position).getThumbnailUrl();
        imageLoader.loadImageAsync(thumbnailUrl,
                viewHolder.recipeThumbnailImage,
                R.drawable.recipe_placeholder);

        viewHolder.bind(recipe, clickListener);
    }

    @Override
    public int getItemCount() {
        return recipes.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView recipeText;
        ImageView recipeThumbnailImage;

        ViewHolder(View view) {
            super(view);

            recipeText = (TextView) view.findViewById(R.id.recipe_row_text);
            recipeThumbnailImage = (ImageView) view.findViewById(R.id.recipe_row_image);

        }

        public void bind(final Recipe item, final OnRecipeRowClickListener listener) {
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override public void onClick(View v) {
                    listener.onItemClick(item);
                }
            });

        }
    }

}