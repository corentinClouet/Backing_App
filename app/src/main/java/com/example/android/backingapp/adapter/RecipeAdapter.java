package com.example.android.backingapp.adapter;

import android.content.Context;
import android.graphics.Movie;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.android.backingapp.R;
import com.example.android.backingapp.entities.Recipe;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RecipeAdapter extends RecyclerView.Adapter<RecipeAdapter.RecipeAdapterViewHolder>{

    private static final String TAG = RecipeAdapter.class.getSimpleName();
    private List<Recipe> mLstRecipe;
    private final RecipeAdapterOnClickHandler mClickHandler;
    private Context mContext;

    /**
     * The interface that receives onClick messages.
     */
    public interface RecipeAdapterOnClickHandler {
        void onClick(Recipe recipe);
    }

    /**
     * Creates a RecipeAdapter.
     *
     * @param clickHandler The on-click handler for this adapter. This single handler is called
     *                     when an item is clicked.
     */
    public RecipeAdapter(RecipeAdapterOnClickHandler clickHandler, Context context) {
        mLstRecipe = new ArrayList<>(); //create empty list by default
        mClickHandler = clickHandler;
        mContext = context;
    }

    /**
     * Cache of the children views for a recipe list item.
     */
    public class RecipeAdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @BindView(R.id.iv_recipe_image) ImageView mRecipeImageView;
        @BindView(R.id.tv_recipe_name) TextView mRecipeTextView;

        RecipeAdapterViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
            view.setOnClickListener(this);
        }

        /**
         * This gets called by the child views during a click.
         *
         * @param v The View that was clicked
         */
        @Override
        public void onClick(View v) {
            int adapterPosition = getAdapterPosition();
            Recipe recipe = mLstRecipe.get(adapterPosition);
            mClickHandler.onClick(recipe);
        }
    }

    /**
     * This gets called when each new ViewHolder is created. This happens when the RecyclerView
     * is laid out. Enough ViewHolders will be created to fill the screen and allow for scrolling.
     *
     * @param viewGroup The ViewGroup that these ViewHolders are contained within.
     * @param viewType  If your RecyclerView has more than one type of item (which ours doesn't) you
     *                  can use this viewType integer to provide a different layout. See
     *                  {@link android.support.v7.widget.RecyclerView.Adapter#getItemViewType(int)}
     *                  for more details.
     * @return A new RecipeAdapterViewHolder that holds the View for each list item
     */
    @Override
    public RecipeAdapterViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        Context context = viewGroup.getContext();
        int layoutIdForListItem = R.layout.recipe_list_item;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;

        View view = inflater.inflate(layoutIdForListItem, viewGroup, shouldAttachToParentImmediately);
        return new RecipeAdapterViewHolder(view);
    }

    /**
     * OnBindViewHolder is called by the RecyclerView to display the data at the specified
     * position.
     *
     * @param recipeAdapterViewHolder The ViewHolder which should be updated to represent the
     *                                  contents of the item at the given position in the data set.
     * @param position                  The position of the item within the adapter's data set.
     */
    @Override
    public void onBindViewHolder(RecipeAdapterViewHolder recipeAdapterViewHolder, int position) {
        Recipe recipe = mLstRecipe.get(position);
        if (recipe.getImage().equals("")){
            recipeAdapterViewHolder.mRecipeImageView.setImageResource(R.drawable.no_recipe_image);
        }else{
            Picasso.with(mContext)
                    .load(recipe.getImage())
                    .error(R.drawable.no_recipe_image)
                    .into(recipeAdapterViewHolder.mRecipeImageView);
        }

        recipeAdapterViewHolder.mRecipeTextView.setText(recipe.getName());
    }

    /**
     * This method simply returns the number of items to display. It is used behind the scenes
     * to help layout our Views and for animations.
     *
     * @return The number of items available in our forecast
     */
    @Override
    public int getItemCount() {
        if (null == mLstRecipe) return 0;
        return mLstRecipe.size();
    }

    /**
     * This method is used to set the recipe list on a RecipeAdapter if we've already
     * created one. This is handy when we get new data from the web but don't want to create a
     * new RecipeAdapter to display it.
     *
     * @param lstRecipe The new recipe list to be displayed.
     */
    public void refreshData(List<Recipe> lstRecipe) {
        Log.d(TAG, "Refresh data");
        this.mLstRecipe = lstRecipe;
        notifyDataSetChanged();
    }
}
