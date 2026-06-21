package com.ucsm.paisesapp.ui;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.ucsm.paisesapp.R;

public class CountryDetailActivity extends AppCompatActivity {

    TextView tvTitle, tvCities;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_country_detail);

        tvTitle = findViewById(R.id.tvTitle);
        tvCities = findViewById(R.id.tvCities);

        String country = getIntent().getStringExtra("country");
        String cities = getIntent().getStringExtra("cities");

        tvTitle.setText(country);
        tvCities.setText(cities);
    }
}