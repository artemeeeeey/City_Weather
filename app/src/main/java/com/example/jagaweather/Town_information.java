package com.example.jagaweather;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Town_information extends AppCompatActivity {
    ImageView background_towns;
    TextView city_description,local_time_tv, base_tv;
    String description,local_time,population,base;
    File file_theme;
    ImageView background;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        file_theme = new File("/data/data/com.example.jagaweather/file_theme");
        setContentView(R.layout.activity_town_information);
        city_description = findViewById(R.id.city_description);
        local_time_tv = findViewById(R.id.local_time);
        base_tv = findViewById(R.id.base);

        base = "Неизвестно";
        local_time = "Неизвестно";

        String theme = "";
        RelativeLayout lay = (RelativeLayout) findViewById(R.id.theme2);

        Intent intent = getIntent();

        description = intent.getStringExtra("description");

        if(!(intent.getStringExtra("base") == null)){
            base = intent.getStringExtra("base");
            base_tv.setText("Дата основания: " + base);
        }
        else{
            base_tv.setText("Дата основания: " + base);
        }

        if (!(intent.getStringExtra("local_time") == null)){
            local_time = intent.getStringExtra("local_time");
            local_time_tv.setText("Местное время: " + local_time);
        }
        else{
            local_time_tv.setText("Местное время: " + local_time);
        }

        city_description.setText(description);

        try {
            Scanner scanner = new Scanner(file_theme);
            if(scanner.hasNext()) {
                theme = scanner.next();
                System.out.println("hry" + theme);
                if (theme.equals("1")) {
                    lay.setBackgroundResource(R.drawable.sprin);
                } else if (theme.equals("2")) {
                    lay.setBackgroundResource(R.drawable.aut);
                }
                else if (theme.equals("3")) {
                    lay.setBackgroundResource(R.drawable.night);
                }
                else if (theme.equals("4")) {
                    lay.setBackgroundResource(R.drawable.red);
                }
                else if (theme.equals("5")) {
                    lay.setBackgroundResource(R.drawable.idk);
                }
                else if (theme.equals("6")) {
                    lay.setBackgroundResource(R.drawable.purple);
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}