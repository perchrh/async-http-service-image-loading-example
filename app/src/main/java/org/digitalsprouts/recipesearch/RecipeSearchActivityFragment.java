package org.digitalsprouts.recipesearch;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.jakewharton.picasso.OkHttp3Downloader;
import com.squareup.picasso.Picasso;

import org.digitalsprouts.recipesearch.picasso.RecipeListAdapter;
import org.digitalsprouts.recipesearchclient.model.Recipe;

import java.util.Arrays;
import java.util.List;

import okhttp3.OkHttpClient;

public class RecipeSearchActivityFragment extends Fragment {

    private EditText queryInputView;
    private RecyclerView recyclerView;

    private View.OnKeyListener submitQueryListener = new View.OnKeyListener() {
        @Override
        public boolean onKey(View v, int keyCode, KeyEvent event) {
            // If the event is a key-down event on the "enter" button
            if ((event.getAction() == KeyEvent.ACTION_DOWN) &&
                    (keyCode == KeyEvent.KEYCODE_ENTER)) {
                String queryString = queryInputView.getText().toString();
                submitSearch(queryString);
                return true;
            }
            return false;
        }
    };

    public RecipeSearchActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_recipe_search, container, false);

        initViews(rootView);

        return rootView;
    }

    private Picasso createImageLoader(Context context) {
        // TODO when activity is disconnect, set to null
        // TODO when activity is reconnected, create it again (new context)
        OkHttpClient client = new OkHttpClient.Builder()
                .build();
        OkHttp3Downloader downloader = new OkHttp3Downloader(client);
        return new Picasso.Builder(context).downloader(downloader).build();
    }

    private void initViews(final View rootView) {
        queryInputView = (EditText) rootView.findViewById(R.id.recipe_search_query);
        queryInputView.setImeActionLabel(getString(R.string.recipe_search_submit), KeyEvent.KEYCODE_ENTER);
        queryInputView.setOnKeyListener(submitQueryListener);

        recyclerView = (RecyclerView) rootView.findViewById(R.id.recipe_recycler_view);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);

        List<Recipe> recipeData = getDummyData(); //TODO get from network

        RecipeListAdapter adapter = new RecipeListAdapter(createImageLoader(getContext()), recipeData);
        recyclerView.setAdapter(adapter);
    }

    private void submitSearch(String queryString) {
        // TODO Call retrofit here..
        Toast.makeText(getActivity(), "I'm searching for : " + queryString, Toast.LENGTH_LONG).show();
    }




}
