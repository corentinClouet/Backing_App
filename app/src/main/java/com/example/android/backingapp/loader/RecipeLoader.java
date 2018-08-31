package com.example.android.backingapp.loader;

import android.content.AsyncTaskLoader;
import android.content.Context;
import android.graphics.Movie;
import android.util.Log;

import com.example.android.backingapp.entities.Recipe;
import com.example.android.backingapp.utils.NetworkUtils;
import com.example.android.backingapp.utils.RecipeJsonUtils;

import org.json.JSONException;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class RecipeLoader extends AsyncTaskLoader<List<Recipe>> {

    private static final String LOG_TAG = RecipeLoader.class.getName(); //tag for log messages

    /**
     * Constructs a new {@link RecipeLoader}.
     *
     * @param context of the activity
     */
    public RecipeLoader(Context context) {
        super(context);
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    /**
     * This is on a background thread.
     */
    @Override
    public List<Recipe> loadInBackground() {

        Log.d(LOG_TAG, "loadInBackground");

        String resultJson = null;
        List<Recipe> result = new ArrayList<>();

        //init the query
        URL query = NetworkUtils.buildUrl();
        try {
            //get the JSON results
            resultJson = NetworkUtils.getResponseFromHttpUrl(query);
        } catch (IOException e) {
            e.printStackTrace();
        }

        //if we have a JSON result
        if (resultJson != null) {
            try {
                //parse the JSON et return an ArrayList of recipes
                result = RecipeJsonUtils.parseRecipeJsonResponse(getContext(),resultJson);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        //return an ArrayList of recipes (empty if we have an error)
        return result;
    }
}
