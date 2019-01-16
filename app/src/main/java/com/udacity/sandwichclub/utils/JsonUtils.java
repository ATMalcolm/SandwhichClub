package com.udacity.sandwichclub.utils;

import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class JsonUtils {
    private static final String TAG = JsonUtils.class.getSimpleName();
    private static final String JSON_NAME_KEY = "name";
    private static final String JSON_MAIN_NAME_KEY = "mainName";
    private static final String JSON_ALSO_KNOWN_AS_KEY = "alsoKnownAs";
    private static final String JSON_PLACE_OF_ORIGIN_KEY = "placeOfOrigin";
    private static final String JSON_DESCRIPTION_KEY = "description";
    private static final String JSON_IMAGE_KEY = "image";
    private static final String JSON_INGREDIENTS_KEY = "ingredients";

    public static Sandwich parseSandwichJson(String json) {
        ArrayList<String> alsoKnownAs = new ArrayList<>();
        ArrayList<String> ingredients = new ArrayList<>();
        String mainName, placeOfOrigin, description, image;

        try {
            JSONObject jsonSandwich = new JSONObject(json);

            JSONObject name = jsonSandwich.getJSONObject(JSON_NAME_KEY);
            mainName = name.getString(JSON_MAIN_NAME_KEY);
            JSONArray jsonAlsoKnownAs = name.getJSONArray(JSON_ALSO_KNOWN_AS_KEY);
            for (int i = 0; i < jsonAlsoKnownAs.length(); i++) {
                alsoKnownAs.add(jsonAlsoKnownAs.get(i).toString());
            }
            placeOfOrigin = jsonSandwich.getString(JSON_PLACE_OF_ORIGIN_KEY);
            description = jsonSandwich.getString(JSON_DESCRIPTION_KEY);
            image = jsonSandwich.getString(JSON_IMAGE_KEY);
            JSONArray jsonIngredients = jsonSandwich.getJSONArray(JSON_INGREDIENTS_KEY);
            for (int i = 0; i < jsonIngredients.length(); i++) {
                ingredients.add(jsonIngredients.get(i).toString());
            }
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }

        Sandwich sandwich = new Sandwich(mainName, alsoKnownAs, placeOfOrigin, description, image, ingredients);
        return sandwich;
    }
}
