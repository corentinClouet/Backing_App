package com.example.android.backingapp.utils;

import android.content.Context;

import com.example.android.backingapp.entities.Ingredient;
import com.example.android.backingapp.entities.Recipe;
import com.example.android.backingapp.entities.Step;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class RecipeJsonUtils {

    /**
     * This method parses JSON from a web response and returns an array of recipes
     * describing the recipes.
     * @param recipeJsonStr JSON response from server
     *
     * @return Array of Recipe describing recipes data
     *
     * @throws JSONException If JSON data cannot be properly parsed
     */
    public static List<Recipe> parseRecipeJsonResponse(Context context, String recipeJsonStr) throws JSONException {

        /* String array to hold each recipe */
        ArrayList<Recipe> lstRecipe = new ArrayList<>();

        JSONArray recipeArray = new JSONArray(recipeJsonStr);

        for(int i=0;i<recipeArray.length();i++){
            //temporary variable to store recipe's data
            Recipe tmpRecipe = new Recipe();

            //Get the JSON object representing the current recipe
            JSONObject recipeObject = recipeArray.getJSONObject(i);

            //set the temporary objects with returned values
            tmpRecipe.setId(recipeObject.getInt("id"));
            tmpRecipe.setName(recipeObject.getString("name"));
            tmpRecipe.setServing(recipeObject.getInt("servings"));
            tmpRecipe.setImage(recipeObject.getString("image"));

            //get all the ingredients
            JSONArray ingredientArray = recipeObject.getJSONArray("ingredients");
            ArrayList<Ingredient> lstIngredient = new ArrayList<>();
            for(int y=0;y<ingredientArray.length();y++){
                Ingredient tmpIngredient = new Ingredient();
                JSONObject ingredientObject = ingredientArray.getJSONObject(y);
                tmpIngredient.setQuantity(ingredientObject.getDouble("quantity"));
                tmpIngredient.setMeasure(ingredientObject.getString("measure"));
                tmpIngredient.setName(ingredientObject.getString("ingredient"));
                lstIngredient.add(tmpIngredient);
            }
            tmpRecipe.setLstIngredient(lstIngredient);

            //get all the steps
            JSONArray stepArray = recipeObject.getJSONArray("steps");
            ArrayList<Step> lstStep = new ArrayList<>();
            for(int z=0;z<stepArray.length();z++){
                Step tmpStep = new Step();
                JSONObject stepObject = stepArray.getJSONObject(z);
                tmpStep.setId(stepObject.getInt("id"));
                tmpStep.setShortDescription(stepObject.getString("shortDescription"));
                tmpStep.setDescription(stepObject.getString("description"));
                tmpStep.setVideoUrl(stepObject.getString("videoURL"));
                tmpStep.setThumbnailUrl(stepObject.getString("thumbnailURL"));
                lstStep.add(tmpStep);
            }
            tmpRecipe.setLstStep(lstStep);

            //add the recipe object in the list
            lstRecipe.add(tmpRecipe);
        }
        return lstRecipe;
    }
}
