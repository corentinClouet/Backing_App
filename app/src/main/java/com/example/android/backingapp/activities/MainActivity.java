package com.example.android.backingapp.activities;

import android.app.LoaderManager;
import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.android.backingapp.R;
import com.example.android.backingapp.adapter.RecipeAdapter;
import com.example.android.backingapp.entities.Recipe;
import com.example.android.backingapp.loader.RecipeLoader;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements RecipeAdapter.RecipeAdapterOnClickHandler, LoaderManager.LoaderCallbacks<List<Recipe>> {

    @BindView(R.id.rv_recipe) RecyclerView mRecyclerView;
    @BindView(R.id.tv_no_data) TextView mErrorMessageDisplay;
    @BindView(R.id.pb_loading) ProgressBar mLoadingIndicator;
    private RecipeAdapter mRecipeAdapter;

    private static final String TAG = MainActivity.class.getSimpleName();
    private static final int RECIPE_LOADER_ID = 1;
    private static final String EXTRA_RECIPE = "recipe";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        //set the layoutManager attached to our recyclerView
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        mRecyclerView.setLayoutManager(linearLayoutManager);

        /*
         * The RecipeAdapter is responsible for linking our recipes data with the Views that
         * will end up displaying our recipes data.
         */
        mRecipeAdapter = new RecipeAdapter(this, getApplicationContext());

        /* Setting the adapter attaches it to the RecyclerView in our layout. */
        mRecyclerView.setAdapter(mRecipeAdapter);

        if (isConnected()){
            // Get a reference to the LoaderManager, in order to interact with loaders.
            LoaderManager loaderManager = getLoaderManager();
            // Initialize the loader. Pass in the int ID constant defined above and pass in null for the bundle.
            loaderManager.initLoader(RECIPE_LOADER_ID, null, this);
        }
        else
        {
            // Hide loading indicator because there is not internet connection
            mLoadingIndicator.setVisibility(View.GONE);
            // Set empty state view to display "No internet connection."
            mErrorMessageDisplay.setVisibility(View.VISIBLE);
            mErrorMessageDisplay.setText(R.string.no_internet_connection);
        }
    }

    //verify internet connection
    public boolean isConnected(){
        ConnectivityManager cm = (ConnectivityManager)this.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        return activeNetwork != null && activeNetwork.isConnectedOrConnecting();
    }

    //Open the detail activity of the recipe when a user clicked on an item
    @Override
    public void onClick(Recipe recipe) {
        Intent intent = new Intent(this, RecipeDetail.class);
        intent.putExtra(EXTRA_RECIPE, recipe);
        startActivity(intent);
    }

    @Override
    public Loader<List<Recipe>> onCreateLoader(int i, Bundle bundle) {
        Log.d(TAG, "onCreateLoader");
        return new RecipeLoader(this);
    }

    @Override
    public void onLoadFinished(Loader<List<Recipe>> loader, List<Recipe> data) {
        Log.d(TAG, "onLoadFinished");

        // Hide loading indicator because the data has been loaded
        mLoadingIndicator.setVisibility(View.GONE);

        // Set empty state view to display "No data found." in case of we have no data
        mErrorMessageDisplay.setText(R.string.no_data);

        // If there is a valid list of {@link Recipe}s, then add them to the adapter's
        // data set. This will trigger the RecyclerView to update.
        if (data != null && !data.isEmpty()) {
            mRecipeAdapter.refreshData(data);
        }
    }

    @Override
    public void onLoaderReset(Loader<List<Recipe>> loader) {
        Log.d(TAG, "onLoaderReset");
        mRecipeAdapter.refreshData(null);
    }
}
