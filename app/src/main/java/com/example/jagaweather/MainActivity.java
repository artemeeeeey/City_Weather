package com.example.jagaweather;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.work.Data;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkManager;

import java.util.Calendar;
import java.util.Date;

import androidx.work.WorkRequest;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.kwabenaberko.newsapilib.NewsApiClient;
import com.kwabenaberko.newsapilib.models.request.TopHeadlinesRequest;
import com.kwabenaberko.newsapilib.models.response.ArticleResponse;

//238d9c2c4369d56d04e268ffe5b14143 == open weather map api
//d1c982620c3e4ec093755b6fc3e5125a == news api

public class MainActivity extends AppCompatActivity {
    AutoCompleteTextView city_name;
    TextView write_city_name;
    TextView weather;
    ImageButton search;
    ImageView idIVIcon;
    TextView condition;
    RecyclerView rv,rv1,rv2,rv3;
    String weather_future[] = new String[8];
    String[] time = new String[8];
    int images[] = new int[8];

    String[] temps, feels_like_a, description_a, time_a;
    //weather variables

    //current weather
    String current_temp;
    String feels_like;
    String current_description;

    //1 day after
//    String od_temp;
//    String od_description;

    //2 days after
//    String twd_temp;
//    String twd_description;

    //3 days after
//    String thd_temp;
//    String thd_description;

    //4 days after
//    String frd_temp;
//    String frd_description;

    //5 days after
//    String fvd_temp;
//    String fvd_description;

    private String news_api = "d1c982620c3e4ec093755b6fc3e5125a";

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        String[] towns = { "Уфа", "Москва"};
        Date currentTime = Calendar.getInstance().getTime();
        String[] currentTime_string = currentTime.toString().split(" ");
        String hours = (currentTime.toString().split(" ")[3].split(":"))[0];
        write_city_name = findViewById(R.id.idCityName);
        city_name = findViewById(R.id.idEdtCity);
        weather = findViewById(R.id.idTVTemperture);
        rv = findViewById(R.id.idRvWeather);
        rv1 = findViewById(R.id.idRvWeather1);
        rv2 = findViewById(R.id.idRvWeather2);
        rv3 = findViewById(R.id.idRvWeather3);
        search = findViewById(R.id.idTVSearch);
        idIVIcon = findViewById(R.id.idIVIcon);
        condition = findViewById(R.id.idTVCondition);

//        weather_future = new String[]{od_temp};
//        description = new String[]{od_description };

        ArrayAdapter<String> adapter = new ArrayAdapter(this, android.R.layout.simple_dropdown_item_1line, towns);
        city_name.setAdapter(adapter);
        search.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                if (city_name.getText().toString().equals("")){
                    Toast.makeText(getApplicationContext(), "Поле не может быть пустым", Toast.LENGTH_SHORT).show();
                }
                else{
                    Data input = new Data.Builder().putString("city", city_name.getText().toString()).build();

                    WorkRequest workRequest = new OneTimeWorkRequest.Builder(getWeather.class)
                            .setInputData(input).build();
                    WorkManager workManager = WorkManager.getInstance(getApplicationContext());
                    workManager.enqueue(workRequest);
                    workManager.getWorkInfoByIdLiveData(workRequest.getId()).observe(MainActivity.this, workInfo -> {

                        Log.d("workInfo", workInfo.getOutputData().toString());
                        Log.d("state", workInfo.getState().toString());
                        if (workInfo.getState().toString().equals("FAILED")){
                            Toast.makeText(getApplicationContext(), "Попробуйте снова", Toast.LENGTH_SHORT).show();
                        }
                        else if (workInfo != null && workInfo.getState().isFinished()){

                            temps = workInfo.getOutputData().getStringArray("temps");
                            description_a = workInfo.getOutputData().getStringArray("description");
                            feels_like_a = workInfo.getOutputData().getStringArray("feels_like");
                            time_a = workInfo.getOutputData().getStringArray("time");

                            current_temp = temps[0];
                            current_description = description_a[0];
                            feels_like = feels_like_a[0];

//                            current_temp = workInfo.getOutputData().getString("current_temp");
//                            feels_like = workInfo.getOutputData().getString("feels_like");
//                            current_description = workInfo.getOutputData().getString("current_description");
//
//                            od_temp = workInfo.getOutputData().getString("1temp");
//                            od_description = workInfo.getOutputData().getString("1description");
//
//                            twd_temp = workInfo.getOutputData().getString("2temp");
//                            twd_description = workInfo.getOutputData().getString("2description");
//
//                            thd_temp = workInfo.getOutputData().getString("3temp");
//                            thd_description = workInfo.getOutputData().getString("3description");
//
//                            frd_temp = workInfo.getOutputData().getString("4temp");
//                            frd_description = workInfo.getOutputData().getString("4description");
//
//                            fvd_temp = workInfo.getOutputData().getString("5temp");
//                            fvd_description = workInfo.getOutputData().getString("5description");


//                            Log.d("results", current_temp + " " +
//                                    current_description + " " + od_temp + " " +
//                                    od_description +  " " + twd_temp + " " + twd_description + " " +
//                                    thd_temp + " " + thd_description + " " + frd_temp + " " +
//                                    frd_description + " " + fvd_temp + " " + fvd_description);

                            weather.setText(current_temp);
                            condition.setText(current_description);
                            write_city_name.setText(city_name.getText().toString());
                            if(current_description.equals("пасмурно")){ idIVIcon.setImageResource(R.drawable.rainyday);}
                            else if(current_description.equals("снег")){ idIVIcon.setImageResource(R.drawable.snow);}
                            else if(current_description.equals("дождь")){ idIVIcon.setImageResource(R.drawable.lutiy_dozhd);}
                            else if(current_description.equals("солнечно")){ idIVIcon.setImageResource(R.drawable.sun);}
                            else if(current_description.equals("переменная облачность")){ idIVIcon.setImageResource(R.drawable.cloud);}
                            else if(current_description.equals("небольшой дождь")){ idIVIcon.setImageResource(R.drawable.rain);}
                            else if(current_description.equals("облачно с прояснениями")){ idIVIcon.setImageResource(R.drawable.suncloud);}

                            int a = 0;
                            for (int i = 1; i < 9; i++){
                                weather_future[a] = temps[i];
                                time[a] = time_a[i];

                                if (description_a[i].equals("пасмурно")) images[a] = R.drawable.rainyday;
                                else if (description_a[i].equals("снег")) images[a] = R.drawable.snow;
                                else if (description_a[i].equals("дождь")) images[a] = R.drawable.lutiy_dozhd;
                                else if (description_a[i].equals("солнечно")) images[a] = R.drawable.sun;
                                else if (description_a[i].equals("переменная облачность") || description_a[i].equals("небольшая облачность")) images[a] = R.drawable.cloud;
                                else if (description_a[i].equals("небольшой дождь")) images[a] = R.drawable.rain;
                                else if (description_a[i].equals("облачно с прояснениями")) images[a] = R.drawable.suncloud;
                                a += 1;
                            }
//                            weather_future = new String[]{od_temp,thd_temp};
//                            description = new String[]{od_description ,thd_description};
//                            images = new int[]{R.drawable.snow, R.drawable.rainyday};
                            Myadapter myadapter = new Myadapter(MainActivity.this,weather_future,time, images);
                            rv.setAdapter(myadapter);
                            rv.setLayoutManager(new LinearLayoutManager(MainActivity.this,LinearLayoutManager.HORIZONTAL,false));

//                            weather_future = new String[]{twd_temp,fvd_temp};
//                            description = new String[]{twd_description ,fvd_description};
//                            images = new int[]{R.drawable.lutiy_dozhd, R.drawable.snow};

                            weather_future = new String[8];
                            time = new String[8];
                            images = new int[8];
                            a = 0;
                            for (int i = 9; i < 17; i++){
                                weather_future[a] = temps[i];
                                time[a] = time_a[i];

                                if (description_a[i].equals("пасмурно")) images[a] = R.drawable.rainyday;
                                else if (description_a[i].equals("снег")) images[a] = R.drawable.snow;
                                else if (description_a[i].equals("дождь")) images[a] = R.drawable.lutiy_dozhd;
                                else if (description_a[i].equals("солнечно")) images[a] = R.drawable.sun;
                                else if (description_a[i].equals("переменная облачность") || description_a[i].equals("небольшая облачность")) images[a] = R.drawable.cloud;
                                else if (description_a[i].equals("небольшой дождь")) images[a] = R.drawable.rain;
                                else if (description_a[i].equals("облачно с прояснениями")) images[a] = R.drawable.suncloud;
                                a += 1;
                            }
                            Myadapter myadapter1 = new Myadapter(MainActivity.this,weather_future,time,images);
                            rv1.setAdapter(myadapter1);
                            rv1.setLayoutManager(new LinearLayoutManager(MainActivity.this,LinearLayoutManager.HORIZONTAL,false));

                            weather_future = new String[8];
                            time = new String[8];
                            images = new int[8];
                            a = 0;
                            for (int i = 17; i < 25; i++){
                                weather_future[a] = temps[i];
                                time[a] = time_a[i];

                                if (description_a[i].equals("пасмурно")) images[a] = R.drawable.rainyday;
                                else if (description_a[i].equals("снег")) images[a] = R.drawable.snow;
                                else if (description_a[i].equals("дождь")) images[a] = R.drawable.lutiy_dozhd;
                                else if (description_a[i].equals("солнечно")) images[a] = R.drawable.sun;
                                else if (description_a[i].equals("переменная облачность") || description_a[i].equals("небольшая облачность")) images[a] = R.drawable.cloud;
                                else if (description_a[i].equals("небольшой дождь")) images[a] = R.drawable.rain;
                                else if (description_a[i].equals("облачно с прояснениями")) images[a] = R.drawable.suncloud;
                                a += 1;
                            }
                            Myadapter myadapter2 = new Myadapter(MainActivity.this,weather_future,time,images);
                            rv2.setAdapter(myadapter2);
                            rv2.setLayoutManager(new LinearLayoutManager(MainActivity.this,LinearLayoutManager.HORIZONTAL,false));

                            weather_future = new String[8];
                            time = new String[8];
                            images = new int[8];
                            a = 0;
                            for (int i = 25; i < 33; i++){
                                weather_future[a] = temps[i];
                                time[a] = time_a[i];

                                if (description_a[i].equals("пасмурно")) images[a] = R.drawable.rainyday;
                                else if (description_a[i].equals("снег")) images[a] = R.drawable.snow;
                                else if (description_a[i].equals("дождь")) images[a] = R.drawable.lutiy_dozhd;
                                else if (description_a[i].equals("солнечно")) images[a] = R.drawable.sun;
                                else if (description_a[i].equals("переменная облачность") || description_a[i].equals("небольшая облачность")) images[a] = R.drawable.cloud;
                                else if (description_a[i].equals("небольшой дождь")) images[a] = R.drawable.rain;
                                else if (description_a[i].equals("облачно с прояснениями")) images[a] = R.drawable.suncloud;
                                a += 1;
                            }
                            Myadapter myadapter3 = new Myadapter(MainActivity.this,weather_future,time,images);
                            rv3.setAdapter(myadapter3);
                            rv3.setLayoutManager(new LinearLayoutManager(MainActivity.this,LinearLayoutManager.HORIZONTAL,false));

                        }
                    });

                }

                NewsApiClient newsApiClient = new NewsApiClient(news_api);
                newsApiClient.getTopHeadlines(
                        new TopHeadlinesRequest.Builder()
                                .language("ru")
                                .country("ru")
                                .build(),

                        new NewsApiClient.ArticlesResponseCallback() {
                            @Override
                            public void onSuccess(ArticleResponse response) {
                                Log.d("news", response.getArticles().get(2).getTitle());
                            }

                            @Override
                            public void onFailure(Throwable throwable) {
                                System.out.println(throwable.getMessage());
                            }
                        }
                );
            }

        });
    }

//    public void setArrays(String[] temps, time, )

}