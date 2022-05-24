 package com.example.jagaweather;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
import android.os.Bundle;
import android.transition.Explode;
import android.transition.Fade;
import android.transition.Slide;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageButton;

 public class Themes extends AppCompatActivity {
    ImageButton ib1,ib2,ib3,ib4;
    Button back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().requestFeature(Window.FEATURE_ACTIVITY_TRANSITIONS);
        getWindow().setEnterTransition(new Fade());

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_themes);
        ib1 = findViewById(R.id.IB1);
        ib2 = findViewById(R.id.IB2);
        ib3 = findViewById(R.id.IB3);
        ib4 = findViewById(R.id.IB4);
        back = findViewById(R.id.Back);


        ib1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


            }
        });
        ib2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        ib3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        ib4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }

     public void write_in_file() {

     }
 }
