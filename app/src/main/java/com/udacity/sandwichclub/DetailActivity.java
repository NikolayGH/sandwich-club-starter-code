package com.udacity.sandwichclub;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.udacity.sandwichclub.model.Sandwich;
import com.udacity.sandwichclub.utils.JsonUtils;

import java.util.List;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;

    TextView alsoKnownTextView;
    TextView ingredientsTextView;
    TextView originTextView;
    TextView descriptionTextView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        ImageView ingredientsIv = findViewById(R.id.image_iv);

        alsoKnownTextView = findViewById(R.id.also_known_tv);
        ingredientsTextView = findViewById(R.id.ingredients_tv);
        originTextView = findViewById(R.id.origin_tv);
        descriptionTextView = findViewById(R.id.description_tv);

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
        Sandwich sandwich = JsonUtils.parseSandwichJson(json);
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

        descriptionTextView.setText(sandwich.getDescription());

        if (!sandwich.getPlaceOfOrigin().equals("")) {
            originTextView.setText(sandwich.getPlaceOfOrigin());
        } else {
            originTextView.setText(R.string.unknown_origin);

        }


        if(sandwich.getAlsoKnownAs().size() > 0) {
            alsoKnownTextView.setText(joinStrings(", ",sandwich.getAlsoKnownAs()));
        } else {
            alsoKnownTextView.setText(R.string.no_names);

        }

        if(sandwich.getIngredients().size() > 0) {
            ingredientsTextView.setText(joinStrings(", ",sandwich.getIngredients()));
        }

    }
    private String joinStrings(String devider, List<String> strings) {
        StringBuilder stringBuilder = new StringBuilder();
        for(int i=0; i < strings.size(); i++) {
            if (i>0) {
                stringBuilder.append(devider);
            }
            stringBuilder.append(strings.get(i));
        }
        return stringBuilder.toString();


    }
}
