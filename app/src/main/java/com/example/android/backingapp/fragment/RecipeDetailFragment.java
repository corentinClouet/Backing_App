package com.example.android.backingapp.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.android.backingapp.R;
import com.example.android.backingapp.adapter.RecipeDetailAdapter;
import com.example.android.backingapp.entities.Recipe;

import java.util.ArrayList;
import java.util.List;

public class RecipeDetailFragment extends android.support.v4.app.Fragment implements RecipeDetailAdapter.RecipeDetailAdapterOnClickHandler {

    private static final String SAVED_INSTANCE_RECIPE = "recipe";
    private Recipe mRecipe;
    private OnStepClickListener mCallback;

    public interface OnStepClickListener{
        void onStepClick(int position);
    }

    public RecipeDetailFragment() {
    }

    @Override
    public View onCreateView( LayoutInflater inflater,  ViewGroup container,  Bundle savedInstanceState) {
        // Load the saved state (the selected recipe) if there is one
        if(savedInstanceState != null) {
            mRecipe = savedInstanceState.getParcelable(SAVED_INSTANCE_RECIPE);
        }

        final View rootView = inflater.inflate(R.layout.fragment_recipe_detail, container, false);

        // Get a reference to the GridView in the fragment_master_list xml layout file
        RecyclerView recyclerView = rootView.findViewById(R.id.rv_recipe_detail);
        //set the layoutManager attached to our recyclerView
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(linearLayoutManager);

        // Create the adapter
        // This adapter takes in the context and an ArrayList of ALL the image resources to display
        RecipeDetailAdapter mAdapter = new RecipeDetailAdapter(getContext(), initListSteps(), this);

        // Set the adapter on the GridView
        recyclerView.setAdapter(mAdapter);

        // Return the root view
        return rootView;
    }

    // Override onAttach to make sure that the container activity has implemented the callback
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        // This makes sure that the host activity has implemented the callback interface
        // If not, it throws an exception
        try {
            mCallback = (OnStepClickListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement OnStepClickListener");
        }
    }

    //create a List<String> that contains all the details steps of he recipe (including ingredients)
    private List<String> initListSteps(){
        List<String> lstSteps = new ArrayList<>();
        lstSteps.add("Ingredients"); //the first item of the details steps is always "INGREDIENT"
        for(int i=0;i<mRecipe.getLstStep().size();i++){
            lstSteps.add(mRecipe.getLstStep().get(i).getShortDescription()); //get the short description of the step to add in the list
        }
        return lstSteps;
    }

    //Click on a step of the recipe
    @Override
    public void onClick(int position) {
        //call the callback in the source activity
        mCallback.onStepClick(position);
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable(SAVED_INSTANCE_RECIPE, mRecipe);
    }

    public void setmRecipe(Recipe mRecipe) {
        this.mRecipe = mRecipe;
    }
}
