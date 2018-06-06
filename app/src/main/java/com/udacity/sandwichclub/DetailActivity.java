package com.udacity.sandwichclub;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
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


    private TextView malso_knownas;
    private TextView mingredients;

    private TextView mdescription;
    private TextView morigin;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);



        malso_knownas = (TextView) findViewById(R.id.also_known_tv);
        mingredients = (TextView) findViewById(R.id.ingredients_tv);
        mdescription = (TextView) findViewById(R.id.description_tv);
        morigin = (TextView) findViewById(R.id.origin_tv);
        ImageView ingredientsIv = findViewById(R.id.image_iv);

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
        Sandwich sandwich = null;

        try {
            sandwich = JsonUtils.parseSandwichJson(json);
        } catch (JSONException e) {
            e.printStackTrace();

        }


        if (sandwich == null) {
            // Sandwich data unavailable
            //closeOnError();
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

        mdescription.setText(sandwich.getDescription());

        /* set the Origin to N/A (not available) if the PlaceOfOrigin is empty */
        morigin.setText(sandwich.getPlaceOfOrigin().isEmpty() ? getString(R.string.detail_error_message) : sandwich.getPlaceOfOrigin());

        /*
            The sandwich.getAlsoKnownAs() returns a list of Names,
            if the list has more than one item an newline is added
            between items
         */
        if (sandwich.getAlsoKnownAs().size() > 0) {
            malso_knownas.setText(TextUtils.join("\n", sandwich.getAlsoKnownAs()));
        } else {
            // set the Also Know to N/A (not available) if there is not other knowing names
            malso_knownas.setText(getString(R.string.detail_error_message));
        }

        /*
            The sandwich.getIngredients() returns a list of Ingredients,
            if the list has more than one item an newline is added
            between items
         */
        mingredients.setText(TextUtils.join("\n", sandwich.getIngredients()));



    }
}
