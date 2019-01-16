package com.udacity.sandwichclub;

import com.squareup.picasso.Picasso;
import com.udacity.sandwichclub.databinding.ActivityDetailBinding;
import com.udacity.sandwichclub.model.Sandwich;
import com.udacity.sandwichclub.utils.JsonUtils;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

public class DetailActivity extends AppCompatActivity {
    public static final String TAG = DetailActivity.class.getSimpleName();
    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;
    private ActivityDetailBinding mBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_detail);

        ImageView ingredientsIv = mBinding.imageIv;

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
        String aliases = TextUtils.join(", ", sandwich.getAlsoKnownAs());
        // if the sandwich doesn't have any "also known as" names, don't display that section
        if (aliases.isEmpty()) {
            mBinding.alsoKnownLabelTv.setVisibility(View.GONE);
            mBinding.alsoKnownTv.setVisibility(View.GONE);
        } else {
            mBinding.alsoKnownTv.setText(aliases);
        }

        String origin = sandwich.getPlaceOfOrigin();
        if (origin.isEmpty()) {
            origin = getString(R.string.detail_place_of_origin_unknown);
        }
        mBinding.originTv.setText(origin);

        mBinding.descriptionTv.setText(sandwich.getDescription());

        mBinding.ingredientsTv.setText(TextUtils.join(", ", sandwich.getIngredients()));
    }
}
