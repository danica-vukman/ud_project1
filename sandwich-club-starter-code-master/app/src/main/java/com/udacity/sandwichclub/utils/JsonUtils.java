package com.udacity.sandwichclub.utils;

import android.util.Log;

import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class JsonUtils {

    public static Sandwich parseSandwichJson(String json) throws JSONException {


        JSONObject jsonObject = new JSONObject(json);
        JSONObject sandwichName = jsonObject.getJSONObject("name");
        JSONArray sandwichAKA= sandwichName.getJSONArray("alsoKnownAs");
        JSONArray sandwichIng = jsonObject.getJSONArray("ingredients");

        String name = sandwichName.getString("mainName");

        List<String> aka = new ArrayList<>();
        for (int i = 0; i < sandwichAKA.length(); i++) {
        aka.add(sandwichAKA.getString(i));
        }


        List<String> ing = new ArrayList<>();
        for (int i = 0; i < sandwichIng.length(); i++) {
            ing.add(sandwichIng.getString(i));
        }

        String pOO = jsonObject.getString("placeOfOrigin");
        String desc = jsonObject.getString("description");
        String img = jsonObject.getString("image");


        return new Sandwich(name, aka, pOO, desc, img, ing);

    }
}
