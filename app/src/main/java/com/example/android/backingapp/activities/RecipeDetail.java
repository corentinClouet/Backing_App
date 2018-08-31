package com.example.android.backingapp.activities;

import android.content.Intent;
import android.os.PersistableBundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import com.example.android.backingapp.R;
import com.example.android.backingapp.entities.Recipe;
import com.example.android.backingapp.fragment.RecipeDetailFragment;
import com.example.android.backingapp.fragment.StepDetailFragment;

import java.util.ArrayList;

public class RecipeDetail extends AppCompatActivity implements RecipeDetailFragment.OnStepClickListener{

    private static final String LOG = RecipeDetail.class.getSimpleName();
    private static final String EXTRA_RECIPE = "recipe";
    private static final String SAVED_INSTANCE_RECIPE = "recipe";
    private static final String BACKSTACK_TAG = "recipeDetail";
    private Recipe mRecipe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_detail);
        Log.d(LOG, "onCreate");

        //get the selected recipe if it exists
        if (savedInstanceState != null){
           mRecipe = savedInstanceState.getParcelable(SAVED_INSTANCE_RECIPE);
        }
        else if (getIntent().hasExtra(EXTRA_RECIPE)) {
            Log.d(LOG, "getIntent");
            mRecipe = getIntent().getParcelableExtra(EXTRA_RECIPE);
        }else {return;}

        //set the title of the window
        setTitle(mRecipe.getName());

        if (savedInstanceState == null) {
            // Create a new RecipeDetailFragment
            RecipeDetailFragment recipeDetailFragment = new RecipeDetailFragment();

            // Set the Recipe object for the recipeDetailFragment to initialize the RecyclerView with ingredients and steps
            recipeDetailFragment.setmRecipe(mRecipe);

            // Add the fragment to its container using a FragmentManager and a Transaction
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction()
                    .add(R.id.recipe_detail_container, recipeDetailFragment)
                    .commit();
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable(SAVED_INSTANCE_RECIPE, mRecipe);
    }

    @Override
    public void onStepClick(int position) {
        // Create a new StepDetailFragment
        StepDetailFragment stepDetailFragment = new StepDetailFragment();

        // Set the Recipe object and the position for the stepDetailFragment to initialize the RecyclerView with corresponding step
        stepDetailFragment.setmRecipe(mRecipe);
        stepDetailFragment.setmPosition(position);

        // Add the fragment to its container using a FragmentManager and a Transaction
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.recipe_detail_container, stepDetailFragment)
                .addToBackStack(BACKSTACK_TAG)
                .commit();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onBackPressed() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        if (fragmentManager.getBackStackEntryCount() > 0){
            fragmentManager.popBackStack();
        }else{
            super.onBackPressed();
        }
    }
}
