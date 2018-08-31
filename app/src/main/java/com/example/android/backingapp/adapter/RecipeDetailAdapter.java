package com.example.android.backingapp.adapter;

import android.content.Context;
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

public class RecipeDetailAdapter extends RecyclerView.Adapter<RecipeDetailAdapter.RecipeDetailAdapterViewHolder>{

    private static final String TAG = RecipeDetailAdapter.class.getSimpleName();
    private List<String> mLstStep;
    private final RecipeDetailAdapterOnClickHandler mClickHandler;
    private Context mContext;

    /**
     * The interface that receives onClick messages.
     */
    public interface RecipeDetailAdapterOnClickHandler {
        void onClick(int position);
    }

    /**
     * Creates a RecipeDetailAdapter.
     *
     * @param lstStep The list of the steps of the selected recipe
     */
    public RecipeDetailAdapter(Context context, List<String> lstStep, RecipeDetailAdapterOnClickHandler clickHandler) {
        mContext = context;
        mLstStep = lstStep;
        mClickHandler = clickHandler;
    }

    /**
     * Cache of the children views for a recipe list item.
     */
    public class RecipeDetailAdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        @BindView(R.id.tv_step_name) TextView mStepTextView;

        RecipeDetailAdapterViewHolder(View view) {
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
            mClickHandler.onClick(adapterPosition);
        }
    }

    /**
     * This gets called when each new ViewHolder is created. This happens when the RecyclerView
     * is laid out. Enough ViewHolders will be created to fill the screen and allow for scrolling.
     *
     * @param viewGroup The ViewGroup that these ViewHolders are contained within.
     * @param viewType  If your RecyclerView has more than one type of item (which ours doesn't) you
     *                  can use this viewType integer to provide a different layout. See
     *                  {@link RecyclerView.Adapter#getItemViewType(int)}
     *                  for more details.
     * @return A new RecipeDetailAdapterViewHolder that holds the View for each list item
     */
    @Override
    public RecipeDetailAdapterViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        Context context = viewGroup.getContext();
        int layoutIdForListItem = R.layout.recipe_detail_list_item;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;

        View view = inflater.inflate(layoutIdForListItem, viewGroup, shouldAttachToParentImmediately);
        return new RecipeDetailAdapterViewHolder(view);
    }

    /**
     * OnBindViewHolder is called by the RecyclerView to display the data at the specified
     * position.
     *
     * @param recipeDetailAdapterViewHolder The ViewHolder which should be updated to represent the
     *                                  contents of the item at the given position in the data set.
     * @param position                  The position of the item within the adapter's data set.
     */
    @Override
    public void onBindViewHolder(RecipeDetailAdapterViewHolder recipeDetailAdapterViewHolder, int position) {
        recipeDetailAdapterViewHolder.mStepTextView.setText(mLstStep.get(position));
    }

    /**
     * This method simply returns the number of items to display. It is used behind the scenes
     * to help layout our Views and for animations.
     *
     * @return The number of items available in our forecast
     */
    @Override
    public int getItemCount() {
        if (null == mLstStep) return 0;
        return mLstStep.size();
    }

    /**
     * This method is used to set the recipe's step list on a RecipeDetailAdapter if we've already
     * created one. This is handy when we get new data from the web but don't want to create a
     * new RecipeAdapter to display it.
     *
     * @param lstStep The new recipe's step list to be displayed.
     */
    public void refreshData(List<String> lstStep) {
        Log.d(TAG, "Refresh data");
        this.mLstStep = lstStep;
        notifyDataSetChanged();
    }
}
