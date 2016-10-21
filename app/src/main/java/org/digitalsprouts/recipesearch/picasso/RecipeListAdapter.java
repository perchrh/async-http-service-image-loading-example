package org.digitalsprouts.recipesearch.picasso;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.digitalsprouts.recipesearch.R;
import org.digitalsprouts.recipesearchclient.model.Recipe;

import java.util.List;

public class RecipeListAdapter extends RecyclerView.Adapter<RecipeListAdapter.ViewHolder> {
    private final Picasso picasso;
    private final List<Recipe> recipes;

    public RecipeListAdapter(Picasso picasso, List<Recipe> recipes) {
        this.recipes = recipes;
        this.picasso = picasso;
    }

    @Override
    public RecipeListAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.recipe_list_row, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        viewHolder.recipeText.setText(recipes.get(i).getTitle());

        picasso.load(recipes.get(i).getThumbnailUrl()).
                resize(120, 60).
                placeholder(R.drawable.recipe_placeholder).
                into(viewHolder.recipeThumbnailImage);
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

    }

}