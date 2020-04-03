package com.udacity.sandwichclub;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.udacity.sandwichclub.model.Sandwich;
import com.udacity.sandwichclub.utils.JsonUtils;

import org.json.JSONException;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;

    TextView akaNameTV;
    TextView ingredTV;
    TextView descTV;
    TextView pOOTV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        ImageView ingredientsIv = findViewById(R.id.image_iv);


        akaNameTV = findViewById(R.id.also_known_tv);
        ingredTV = findViewById(R.id.ingredients_tv);
        descTV = findViewById(R.id.description_tv);
        pOOTV = findViewById(R.id.origin_tv);

        Intent intent = getIntent();
        if (intent == null) {
            closeOnError();
        }

        int position = intent.getIntExtra(EXTRA_POSITION, DEFAULT_POSITION);
        if (position == DEFAULT_POSITION) {
            // EXTRA_POSITION not found in intent
            closeOnError();
            return;
        }

        String[] sandwiches = getResources().getStringArray(R.array.sandwich_details);
        String json = sandwiches[position];
        Log.d("json", "json " + json);
        Sandwich sandwich = null;
        try {
            sandwich = JsonUtils.parseSandwichJson(json);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        if (sandwich == null) {
            // Sandwich data unavailable
            closeOnError();
            return;
        }

        populateUI(sandwich);
        Picasso.with(this)
                .load(sandwich.getImage())
                .into(ingredientsIv);

        setTitle(sandwich.getMainName());
    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    private void populateUI(Sandwich sandwich) {


        if (!sandwich.getAlsoKnownAs().isEmpty() || !sandwich.getAlsoKnownAs().equals("")) {
            String s = null;
            StringBuilder str
                    = new StringBuilder();
            for (int i = 0; i < sandwich.getAlsoKnownAs().size(); i++) {

                str.append(sandwich.getAlsoKnownAs().get(i) + "\n");
                s = str.toString();
            }
            akaNameTV.setText(s);
        }
        else {
            akaNameTV.setText("");

        }


        if (!sandwich.getIngredients().isEmpty() || !sandwich.getIngredients().equals("")) {
            String q = null;
            StringBuilder str
                    = new StringBuilder();
            for (int i = 0; i < sandwich.getIngredients().size(); i++) {
                str.append(sandwich.getIngredients().get(i) + "\n");
                q = str.toString();
            }
            ingredTV.setText(q);
        }
        else {
            ingredTV.setText("");
        }

        descTV.setText(sandwich.getDescription());
        pOOTV.setText(sandwich.getPlaceOfOrigin());

    }
}
