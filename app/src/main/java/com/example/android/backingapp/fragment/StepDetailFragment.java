package com.example.android.backingapp.fragment;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.android.backingapp.R;
import com.example.android.backingapp.entities.Ingredient;
import com.example.android.backingapp.entities.Recipe;
import com.example.android.backingapp.entities.Step;
import com.google.android.exoplayer2.SimpleExoPlayer;

import butterknife.BindView;
import butterknife.ButterKnife;

public class StepDetailFragment extends android.support.v4.app.Fragment {

    private static final String LOG = StepDetailFragment.class.getSimpleName();
    private static final String SAVED_INSTANCE_RECIPE = "recipe";
    private static final String SAVED_INSTANCE_POSITION = "position";
    private Recipe mRecipe;
    private int mPosition;

    private SimpleExoPlayer mExoPlayer;
    @BindView(R.id.fl_exoplayer_container) FrameLayout mExoPlayerContainer;
    @BindView(R.id.tv_step_name) TextView mStepName;
    @BindView(R.id.tv_step_description) TextView mStepDescription;
    @BindView(R.id.iv_previous) ImageView mPrevious;
    @BindView(R.id.iv_next) ImageView mNext;

    public StepDetailFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.d(LOG, "onCreateView");

        // Load the saved state (the selected recipe and position) if there is one
        if(savedInstanceState != null) {
            mRecipe = savedInstanceState.getParcelable(SAVED_INSTANCE_RECIPE);
            mPosition = savedInstanceState.getInt(SAVED_INSTANCE_POSITION);
        }

        //initialize the final View and bind the inner views
        final View rootView = inflater.inflate(R.layout.fragment_step_detail, container, false);
        ButterKnife.bind(this, rootView);
        initButtonState();
        fillViews();

        //set listener on previous and next buttons
        mPrevious.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPosition--;
                initButtonState();
                fillViews();
            }
        });

        mNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPosition++;
                initButtonState();
                fillViews();
            }
        });

        // Return the root view
        return rootView;
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable(SAVED_INSTANCE_RECIPE, mRecipe);
        outState.putInt(SAVED_INSTANCE_POSITION, mPosition);
    }

    //Initialize the visibilities of the buttons corresponding to the position
    private void initButtonState(){
        int maxPosition = mRecipe.getLstStep().size();
        if (mPosition == 0){
            mPrevious.setVisibility(View.GONE);
            mNext.setVisibility(View.VISIBLE);
        }else if (mPosition == maxPosition){
            mPrevious.setVisibility(View.VISIBLE);
            mNext.setVisibility(View.GONE);
        }else{
            mPrevious.setVisibility(View.VISIBLE);
            mNext.setVisibility(View.VISIBLE);
        }
    }

    //Fill all the views depending of the value of the position and the selected recipe
    private void fillViews(){
        if (mPosition == 0) { //if we show ingredients
            mExoPlayerContainer.setVisibility(View.GONE); //we don't show the exoPlayer if we want to show the ingredients
            mStepName.setText(R.string.ingredient_step_name); //we set the title manually
            mStepDescription.setText(getListIngredient()); //we show the list of ingredients
        } else { //else we show the detail of the step
            if (getActivity().getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
                // Portrait Mode
                mExoPlayerContainer.setVisibility(View.VISIBLE); //we show the exoPlayer if we want to show the steps
                int realPosition = mPosition - 1; //we retire 1 at the position because position 0 = ingredients
                Step step = mRecipe.getLstStep().get(realPosition);
                mStepName.setText(step.getShortDescription()); //title of the step
                mStepDescription.setText(step.getDescription()); //description of the step
            } else {
                // Landscape Mode
                mExoPlayerContainer.setVisibility(View.VISIBLE); //we show the exoPlayer if we want to show the steps
                mExoPlayerContainer.setLayoutParams(new ConstraintLayout.LayoutParams(ConstraintLayout.LayoutParams.MATCH_PARENT, ConstraintLayout.LayoutParams.MATCH_PARENT));
                mStepName.setVisibility(View.GONE);
                mStepDescription.setVisibility(View.GONE);
                mPrevious.setVisibility(View.GONE);
                mNext.setVisibility(View.GONE);
            }
        }
    }

    //Return a String who contains the list of ingredients
    private String getListIngredient(){
        StringBuilder sb = new StringBuilder();
        for (int i=0;i<mRecipe.getLstIngredient().size();i++){
            Ingredient ingredient = mRecipe.getLstIngredient().get(i);
            sb.append(ingredient.getName());
            sb.append(" : ");
            sb.append(ingredient.getQuantity());
            sb.append(" ");
            sb.append(ingredient.getMeasure());
            sb.append("\n");
        }
        return sb.toString();
    }

    public void setmRecipe(Recipe mRecipe) {
        this.mRecipe = mRecipe;
    }

    public void setmPosition(int mPosition) {
        this.mPosition = mPosition;
    }
}
