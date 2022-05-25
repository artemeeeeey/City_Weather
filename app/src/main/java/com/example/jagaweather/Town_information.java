package com.example.jagaweather;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.widget.ImageView;
import android.widget.TextView;

public class Town_information extends AppCompatActivity implements GestureDetector.OnGestureListener {
    ImageView background_towns;
    TextView city_description;
    String description;
    private float y1, y2;
    private static int MIN_DISTANCE = 200;
    private GestureDetector gestureDetector;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_town_information);
        this.gestureDetector = new GestureDetector(Town_information.this, this);
        city_description = findViewById(R.id.city_description);

        Intent intent = getIntent();
        description = intent.getStringExtra("description");

        city_description.setText(description);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        gestureDetector.onTouchEvent(event);
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                y1 = event.getY();

                break;
            case MotionEvent.ACTION_UP:
                y2 = event.getY();

                float valueX = y2 - y1;


                if (Math.abs(valueX) > MIN_DISTANCE) {
                    if (y2 > y1) {
                        Intent back1 = new Intent(Town_information.this, MainActivity.class);
                        startActivity(back1, ActivityOptions.makeSceneTransitionAnimation(Town_information.this).toBundle());

                    }
                }
        }

        return super.onTouchEvent(event);
    }

    @Override
    public boolean onDown(MotionEvent motionEvent) {
        return false;
    }

    @Override
    public void onShowPress(MotionEvent motionEvent) {

    }

    @Override
    public boolean onSingleTapUp(MotionEvent motionEvent) {
        return false;
    }

    @Override
    public boolean onScroll(MotionEvent motionEvent, MotionEvent motionEvent1, float v, float v1) {
        return false;
    }

    @Override
    public void onLongPress(MotionEvent motionEvent) {

    }

    @Override
    public boolean onFling(MotionEvent motionEvent, MotionEvent motionEvent1, float v, float v1) {
        return false;
    }
}