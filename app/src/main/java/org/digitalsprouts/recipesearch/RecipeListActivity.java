package org.digitalsprouts.recipesearch;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;

import org.digitalsprouts.recipesearchclient.model.Recipe;

import java.util.ArrayList;

public class RecipeListActivity extends AppCompatActivity {

    private static final String TAG = "RecipeListActivity";
    private static final String STATE_MODEL = "MODEL";

    private EditText queryInputView;
    private RecyclerView recyclerView;
    private View busyIndicator;
    private ArrayList<Recipe> recipeData; //using ArrayList type for Parcelable

    private OnRecipeRowClickListener clickListener = new OnRecipeRowClickListener() {
        @Override
        public void onItemClick(@NonNull Recipe item) {
            Intent showRecipeDetails = new StartRecipeDetailActivityIntent(RecipeListActivity.this, item);
            startActivity(showRecipeDetails);
        }
    };

    private View.OnKeyListener submitQueryListener = new View.OnKeyListener() {
        @Override
        public boolean onKey(View v, int keyCode, KeyEvent event) {
            if (event.getAction() == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_ENTER) {
                String queryString = queryInputView.getText().toString();
                submitSearch(queryString);
                return true;
            }
            return false;
        }
    };
    private RecipeListLoader recipeListLoader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_list);

        if (savedInstanceState != null) {
            // Restore saved state
            recipeData = savedInstanceState.getParcelableArrayList(STATE_MODEL);
        } else {
            recipeData = new ArrayList<>();
        }

        initViews();
    }


    private void initViews() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        busyIndicator = findViewById(R.id.busy_indicator);

        queryInputView = (EditText) findViewById(R.id.recipe_search_query);
        queryInputView.setImeActionLabel(getString(R.string.recipe_search_submit), KeyEvent.KEYCODE_ENTER);
        queryInputView.setOnKeyListener(submitQueryListener); //using keyListener to support submit by enter OR action search

        recyclerView = (RecyclerView) findViewById(R.id.recipe_recycler_view);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        recipeListLoader = new RecipeListLoader(this, recyclerView, busyIndicator, clickListener, recipeData);

        // Could add endless scrolling, using pagination
        // recyclerView.addOnScrollListener
        // http://stackoverflow.com/questions/26543131/how-to-implement-endless-list-with-recyclerview
    }

    private void submitSearch(final String queryString) {
        recipeListLoader.loadRecipeList(queryString); // updates 'recipeData'
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList(STATE_MODEL, recipeData);
    }

}
