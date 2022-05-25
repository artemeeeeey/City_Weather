 package com.example.jagaweather;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.transition.Explode;
import android.transition.Fade;
import android.transition.Slide;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

 public class Themes extends AppCompatActivity {
    ImageButton ib1,ib2,ib3,ib4,ib,ib5;
    Button back;
    File file_theme;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().requestFeature(Window.FEATURE_ACTIVITY_TRANSITIONS);
        getWindow().setExitTransition(new Fade());
        file_theme = new File("/data/data/com.example.jagaweather/files/file_theme");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_themes);
        ib1 = findViewById(R.id.IB1);
        ib2 = findViewById(R.id.IB2);
        ib3 = findViewById(R.id.IB3);
        ib4 = findViewById(R.id.IB4);
        ib5 = findViewById(R.id.IB5);
        ib = findViewById(R.id.IB);
        back = findViewById(R.id.Back);


        ib1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                file_theme.delete();
                file_theme = new File("/data/data/com.example.jagaweather/files/file_theme");
                write_in_file(1,file_theme);
                Toast.makeText(getApplicationContext(), "Фон установлен", Toast.LENGTH_SHORT).show();
            }
        });
        ib2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                file_theme.delete();
                file_theme = new File("/data/data/com.example.jagaweather/files/file_theme");
                write_in_file(2,file_theme);
                Toast.makeText(getApplicationContext(), "Фон установлен", Toast.LENGTH_SHORT).show();
            }
        });
        ib3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                file_theme.delete();
                file_theme = new File("/data/data/com.example.jagaweather/files/file_theme");
                write_in_file(3,file_theme);
                Toast.makeText(getApplicationContext(), "Фон установлен", Toast.LENGTH_SHORT).show();
            }
        });
        ib4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                file_theme.delete();
                file_theme = new File("/data/data/com.example.jagaweather/files/file_theme");
                write_in_file(4,file_theme);
                Toast.makeText(getApplicationContext(), "Фон установлен", Toast.LENGTH_SHORT).show();
            }
        });
        ib.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                file_theme.delete();
                file_theme = new File("/data/data/com.example.jagaweather/files/file_theme");
                write_in_file(5,file_theme);
                Toast.makeText(getApplicationContext(), "Фон установлен", Toast.LENGTH_SHORT).show();
            }
        });
        ib5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                file_theme.delete();
                file_theme = new File("/data/data/com.example.jagaweather/files/file_theme");
                write_in_file(6,file_theme);
                Toast.makeText(getApplicationContext(), "Фон установлен", Toast.LENGTH_SHORT).show();
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Themes.this, MainActivity.class);
                startActivity(i, ActivityOptions.makeSceneTransitionAnimation(Themes.this).toBundle());
            }
        });
    }

     public void write_in_file(int theme,File file_themes) {
         PrintWriter writer = null;
         try {
             writer = new PrintWriter(new FileWriter(file_themes));
             writer.println(theme);
             writer.close();
         } catch (IOException e) {
             e.printStackTrace();
         }

     }
 }
