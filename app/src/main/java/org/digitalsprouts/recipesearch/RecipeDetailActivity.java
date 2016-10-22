package org.digitalsprouts.recipesearch;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import org.digitalsprouts.recipesearch.imageloader.ImageLoader;
import org.digitalsprouts.recipesearch.imageloader.PicassoImageLoader;
import org.digitalsprouts.recipesearchclient.model.Recipe;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;

public class RecipeDetailActivity extends AppCompatActivity {

    private Recipe model;
    private ImageLoader imageLoader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        imageLoader = new PicassoImageLoader(this);
        model = getIntent().getParcelableExtra(StartRecipeDetailActivityIntent.EXTRA_KEY_RECIPE_ITEM);

        initViews();
    }

    private void initViews() {
        setTitle(model.getTitle());

        ImageView image = (ImageView) findViewById(R.id.image);
        image.setAdjustViewBounds(true);
        imageLoader.loadImageAsync(model.getThumbnailUrl(),
                image,
                R.drawable.recipe_placeholder);

        ListView ingredientList = (ListView) findViewById(R.id.ingredient_list);
        // dummy data because all data from server have empty ingredients list..
        List<String> ingredients = Arrays.asList(new String[]{"Thyme", "Coriander", "Chicken", "Cream"});
        ingredientList.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, ingredients));

        Button showInstructions = (Button) findViewById(R.id.showInstructionButton);
        showInstructions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent showWebPage = new Intent(Intent.ACTION_VIEW, Uri.parse(model.getRecipeUrl()));
                startActivity(showWebPage);
            }
        });

        Button showSource = (Button) findViewById(R.id.showSourceButton);
        showSource.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent showWebPage = new Intent(Intent.ACTION_VIEW, Uri.parse(model.getSourceUrl()));
                startActivity(showWebPage);
            }
        });

        TextView publisherName = (TextView) findViewById(R.id.publisher_name);
        publisherName.setText(model.getPublisher());

        TextView socialMediaRank = (TextView) findViewById(R.id.social_media_rank);
        Double socialRank = model.getSocialRank();
        if (socialRank != null) {
            socialMediaRank.setText(String.format(Locale.US,
                    getString(R.string.social_media_rank_format), socialRank));
        }


    }

}
