package com.udacity.sandwichclub.utils;

import android.util.Log;

import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JsonUtils {

    public static Sandwich parseSandwichJson(String json) throws JSONException {
        Log.d("parseSandwichJson", json);

        /* Creates a new JSONObject with name/value mappings from the json string */
        JSONObject sandwich_Data = new JSONObject(json);

        /* Creates a new JSONObject from sandwichData for the name: name*/
        JSONObject sandwichName = sandwich_Data.getJSONObject("name");

        /* ArrayList to hold the alsoKnownAs Strings*/
        List<String> Also_KnownAs_list = new ArrayList<String>();
        JSONArray array_Also_KnownAs = sandwichName.getJSONArray("alsoKnownAs");
        for (int i=0; i<array_Also_KnownAs.length(); i++) {
            Also_KnownAs_list.add(array_Also_KnownAs.optString(i));
        }

        /* ArrayList to hold the ingredients Strings*/
        List<String> Ingredients_list = new ArrayList<String>();
        JSONArray array_Ingredients = sandwich_Data.getJSONArray("ingredients");
        for (int i=0; i<array_Ingredients.length(); i++) {
            Ingredients_list.add(array_Ingredients.optString(i));
        }

        /* Build the sandwich object */
        Sandwich sandwich = new Sandwich(
                sandwichName.optString("mainName"),
                Also_KnownAs_list,
                sandwich_Data.optString("placeOfOrigin"),
                sandwich_Data.optString("description"),
                sandwich_Data.optString("image"),
                Ingredients_list
        );

        return sandwich;
    }



    }

