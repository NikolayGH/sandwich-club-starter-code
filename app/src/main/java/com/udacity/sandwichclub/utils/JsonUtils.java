package com.udacity.sandwichclub.utils;

import android.util.Log;

import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class JsonUtils {

    public static Sandwich parseSandwichJson(String json) {

        try {
            //Create new  Sandwich object to return it in the end this method
            Sandwich sandwich = new Sandwich();
            //getting first JSONObject. It includes all info about sandwich
            JSONObject sandwichJSONObject = new JSONObject(json);

            // parsing sandwichJSONObject to get second object. It contents name info
            JSONObject name = sandwichJSONObject.getJSONObject("name");

            //setting name info to the field MainName
            sandwich.setMainName(name.getString("mainName"));

            //here we need use Array because here can be two or more objects
            JSONArray alsoKnownAsJSONArray = name.getJSONArray("alsoKnownAs");

            //create new String array for storing data from JSON array
            ArrayList<String> alsoKnownAs = new ArrayList<>();
            for (int j = 0; j < alsoKnownAsJSONArray.length(); j++) {
                alsoKnownAs.add(alsoKnownAsJSONArray.getString(j));
            }

            //setting data to appropriate field in Sandwich object
            sandwich.setAlsoKnownAs(alsoKnownAs);
            // there is similar
            JSONArray ingredientsJSONArray = sandwichJSONObject.getJSONArray("ingredients");
            ArrayList<String> ingredients = new ArrayList<>();
            for (int j = 0; j < ingredientsJSONArray.length(); j++) {
                ingredients.add(ingredientsJSONArray.getString(j));
            }
            sandwich.setIngredients(ingredients);
            //and setting other info
            sandwich.setDescription(sandwichJSONObject.getString("description"));
            sandwich.setImage(sandwichJSONObject.getString("image"));
            sandwich.setPlaceOfOrigin(sandwichJSONObject.getString("placeOfOrigin"));
            return sandwich;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }
}
