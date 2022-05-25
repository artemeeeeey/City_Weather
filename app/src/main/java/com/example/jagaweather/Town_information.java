package com.example.jagaweather;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

public class Town_information extends AppCompatActivity {
    ImageView background_towns;
    TextView city_description;
    String description;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_town_information);

        city_description = findViewById(R.id.city_description);

        Intent intent = getIntent();
        description = intent.getStringExtra("description");

        city_description.setText(description);
    }
}