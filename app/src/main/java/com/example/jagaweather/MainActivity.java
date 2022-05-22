package com.example.jagaweather;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.work.Data;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkManager;

import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import androidx.work.WorkRequest;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

//238d9c2c4369d56d04e268ffe5b14143 == open weather map api
//d1c982620c3e4ec093755b6fc3e5125a == news api

public class MainActivity extends AppCompatActivity {
    AutoCompleteTextView city_name;
    String city;
    TextView write_city_name, weather;
    TextView day1, day2, day3, day4;
    ImageButton search;
    ImageView idIVIcon;
    TextView condition;
    RecyclerView rv,rv1,rv2,rv3;
    String weather_future[] = new String[8];
    String[] time = new String[8];
    int images[] = new int[8];
    String[] temps, feels_like_a, description_a, time_a;
    double lon, lat;
    String day_today;

    //current weather
    String current_temp;
    String feels_like;
    String current_description;

    //location stuff
    Geocoder geocoder;
    List<Address> addresses;

    String cities;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED){
                requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
            }
            else{
                startService();
            }
        }
        else{
            startService();
        }

        cities = "Абакан Азов Александров Алексин Альметьевск Анапа Ангарск Анжеро-Судженск Апатиты Арзамас Армавир Арсеньев Артем Архангельск Асбест Астрахань Ачинск Балаково Балахна Балашиха Балашов Барнаул Батайск Белгород Белебей Белово Белогорск Белорецк Белореченск Бердск Березники Березовский Бийск Биробиджан Благовещенск Бор Борисоглебск Боровичи Братск Брянск Бугульма Буденновск Бузулук Буйнакск Великие Луки Великий Новгород Верхняя Пышма Видное Владивосток Владикавказ Владимир Волгоград Волгодонск Волжск Волжский Вологда Вольск Воркута Воронеж Воскресенск Воткинск Всеволожск Выборг Выкса Вязьма Гатчина Геленджик Георгиевск Глазов Горно-Алтайск Грозный Губкин Гудермес Гуково Гусь-Хрустальный Дербент Дзержинск Димитровград Дмитров Долгопрудный Домодедово Донской Дубна Евпатория Егорьевск Ейск Екатеринбург Елабуга Елец Ессентуки Железногорск Железногорск Жигулевск Жуковский Заречный Зеленогорск Зеленодольск Златоуст Иваново Ивантеевка Ижевск Избербаш Иркутск Искитим Ишим Ишимбай Йошкар-Ола Казань Калининград Калуга Каменск-Уральский Каменск-Шахтинский Камышин Канск Каспийск Кемерово Керчь Кинешма Кириши Киров Кирово-Чепецк Киселевск Кисловодск Клин Клинцы Ковров Когалым Коломна Комсомольск-на-Амуре Копейск Королев Кострома Котлас Красногорск Краснодар Краснокаменск Краснокамск Краснотурьинск Красноярск Кропоткин Крымск Кстово Кузнецк Кумертау Кунгур Курган Курск Кызыл Лабинск Лениногорск Ленинск-Кузнецкий Лесосибирск Липецк Лиски Лобня Лысьва Лыткарино Люберцы Магадан Магнитогорск Майкоп Махачкала Междуреченск Мелеуз Миасс Минеральные Воды Минусинск Михайловка Михайловск Мичуринск Москва Мурманск Муром Мытищи Набережные Челны Назарово Назрань Нальчик Наро-Фоминск Находка Невинномысск Нерюнгри Нефтекамск Нефтеюганск Нижневартовск Нижнекамск Нижний Новгород Нижний Тагил Новоалтайск Новокузнецк Новокуйбышевск Новомосковск Новороссийск Новосибирск Новотроицк Новоуральск Новочебоксарск Новочеркасск Новошахтинск Новый Уренгой Ногинск Норильск Ноябрьск Нягань Обнинск Одинцово Озерск Октябрьский Омск Орел Оренбург Орехово-Зуево Орск Павлово Павловский Посад Пенза Первоуральск Пермь Петрозаводск Петропавловск-Камчатский Подольск Полевской Прокопьевск Прохладный Псков Пушкино Пятигорск Раменское Ревда Реутов Ржев Рославль Россошь Ростов-на-Дону Рубцовск Рыбинск Рязань Салават Сальск Самара Санкт-Петербург Саранск Сарапул Саратов Саров Свободный Севастополь Северодвинск Северск Сергиев Посад Серов Серпухов Сертолово Сибай Симферополь Славянск-на-Кубани Смоленск Соликамск Солнечногорск Сосновый Бор Сочи Ставрополь Старый Оскол Стерлитамак Ступино Сургут Сызрань Сыктывкар Таганрог Тамбов Тверь Тимашевск Тихвин Тихорецк Тобольск Тольятти Томск Троицк Туапсе Туймазы Тула Тюмень Узловая Улан-Удэ Ульяновск Урус-Мартан Усолье-Сибирское Уссурийск Усть-Илимск Уфа Ухта Феодосия Фрязино Хабаровск Ханты-Мансийск Хасавюрт Химки Чайковский Чапаевск Чебоксары Челябинск Черемхово Череповец Черкесск Черногорск Чехов Чистополь Чита Шадринск Шали Шахты Шуя Щекино Щелково Электросталь Элиста Энгельс Южно-Сахалинск Юрга Якутск Ялта Ярославль";
        String[] towns = cities.split(" ");

        Date currentTime = Calendar.getInstance().getTime();
        Log.d("time", currentTime.toString());
        String[] currentTime_string = currentTime.toString().split(" ");
        String day_today = currentTime_string[0];
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
        day1 = findViewById(R.id.day1);
        day2 = findViewById(R.id.day2);
        day3 = findViewById(R.id.day3);
        day4 = findViewById(R.id.day4);

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
                            Toast.makeText(getApplicationContext(), "Попробуйте ещё", Toast.LENGTH_SHORT).show();
                        }
                        else if (workInfo != null && workInfo.getState().isFinished()){

                            temps = workInfo.getOutputData().getStringArray("temps");
                            description_a = workInfo.getOutputData().getStringArray("description");
                            feels_like_a = workInfo.getOutputData().getStringArray("feels_like");
                            time_a = workInfo.getOutputData().getStringArray("time");

                            current_temp = temps[0];
                            current_description = description_a[0];
                            feels_like = feels_like_a[0];

                            weather.setText(current_temp);
                            condition.setText(current_description.substring(0, 1).toUpperCase(Locale.ROOT)
                                    + current_description.substring(1));
                            write_city_name.setText(city_name.getText().toString().substring(0, 1).
                                    toUpperCase(Locale.ROOT) + city_name.getText().toString().substring(1));

                            if(current_description.equals("пасмурно")){ idIVIcon.setImageResource(R.drawable.rainyday);}
                            else if(current_description.equals("снег") || current_description.equals("небольшой снег")){ idIVIcon.setImageResource(R.drawable.snow);}
                            else if(current_description.equals("дождь") || current_description.equals("сильный дождь")){ idIVIcon.setImageResource(R.drawable.lutiy_dozhd);}
                            else if(current_description.equals("солнечно") || current_description.equals("ясно")){ idIVIcon.setImageResource(R.drawable.sun);}
                            else if(current_description.equals("переменная облачность") || current_description.equals("небольшая облачность")){ idIVIcon.setImageResource(R.drawable.cloud);}
                            else if(current_description.equals("небольшой дождь")){ idIVIcon.setImageResource(R.drawable.rain);}
                            else if(current_description.equals("облачно с прояснениями")){ idIVIcon.setImageResource(R.drawable.suncloud);}
                            setDay(day_today);

                            setArrays(weather_future, time, images, 1);
                            Myadapter myadapter = new Myadapter(MainActivity.this,weather_future,time, images);
                            rv.setAdapter(myadapter);
                            rv.setLayoutManager(new LinearLayoutManager(MainActivity.this,LinearLayoutManager.HORIZONTAL,false));
                            clearArray();

                            setArrays(weather_future, time, images, 9);
                            Myadapter myadapter1 = new Myadapter(MainActivity.this,weather_future,time,images);
                            rv1.setAdapter(myadapter1);
                            rv1.setLayoutManager(new LinearLayoutManager(MainActivity.this,LinearLayoutManager.HORIZONTAL,false));
                            clearArray();

                            setArrays(weather_future, time, images, 17);
                            Myadapter myadapter2 = new Myadapter(MainActivity.this,weather_future,time,images);
                            rv2.setAdapter(myadapter2);
                            rv2.setLayoutManager(new LinearLayoutManager(MainActivity.this,LinearLayoutManager.HORIZONTAL,false));
                            clearArray();

                            setArrays(weather_future, time, images, 25);
                            Myadapter myadapter3 = new Myadapter(MainActivity.this,weather_future,time,images);
                            rv3.setAdapter(myadapter3);
                            rv3.setLayoutManager(new LinearLayoutManager(MainActivity.this,LinearLayoutManager.HORIZONTAL,false));
                            clearArray();
                        }
                    });
                }
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    public void setArrays(String[] temp, String[] time, int[] images, int position){
        int a = 0;

        for (int i = position; i < position + 8; i++){
            weather_future[a] = temps[i];
            time[a] = time_a[i];

            if (description_a[i].equals("пасмурно")) images[a] = R.drawable.rainyday;
            else if (description_a[i].equals("снег") || description_a[i].equals("небольшой снег")) images[a] = R.drawable.snow;
            else if (description_a[i].equals("дождь") || description_a[i].equals("сильный дождь")) images[a] = R.drawable.lutiy_dozhd;
            else if (description_a[i].equals("солнечно") || description_a[i].equals("ясно")) images[a] = R.drawable.sun;
            else if (description_a[i].equals("переменная облачность") || description_a[i].equals("небольшая облачность")) images[a] = R.drawable.cloud;
            else if (description_a[i].equals("небольшой дождь")) images[a] = R.drawable.rain;
            else if (description_a[i].equals("облачно с прояснениями")) images[a] = R.drawable.suncloud;
            a += 1;
        }
    }

    public void clearArray(){
        weather_future = new String[8];
        time = new String[8];
        images = new int[8];
    }

    public void setDay(String day_today){
        if(day_today.equals("Sun")){
            day1.setText("Понедельник");
            day2.setText("Вторник");
            day3.setText("Среда");
            day4.setText("Четверг");
        }
        else if(day_today.equals("Sat")){
            day1.setText("Воскресенье");
            day2.setText("Понедельник");
            day3.setText("Вторник");
            day4.setText("Среда");
        }
        else if(day_today.equals("Fri")){
            day1.setText("Суббота");
            day2.setText("Воскресенье");
            day3.setText("Понедальник");
            day4.setText("Вторник");
        }
        else if(day_today.equals("Thu")){
            day1.setText("Пятница");
            day2.setText("Суббота");
            day3.setText("Воскресенье");
            day4.setText("Понедельник");
        }
        else if(day_today.equals("Wed")){
            day1.setText("Четверг");
            day2.setText("Пятница");
            day3.setText("Суббота");
            day4.setText("Воскресенье");
        }
        else if(day_today.equals("Tue")){
            day1.setText("Среда");
            day2.setText("Четверг");
            day3.setText("Пятица");
            day4.setText("Суббота");
        }
        else if(day_today.equals("Mon")){
            day1.setText("Вторник");
            day2.setText("Среда");
            day3.setText("Четверг");
            day4.setText("Пятница");
        }
    }

    public void startService(){
        LocationBroadcastReceiver locationBroadcastReceiver = new LocationBroadcastReceiver();
        IntentFilter intentFilter = new IntentFilter("ACT_LOC");
        registerReceiver(locationBroadcastReceiver, intentFilter);
        Intent intent = new Intent(MainActivity.this, locationService.class);
        startService(intent);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode){
            case 1:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    startService();
                }
                else{
                    Toast.makeText(this, "Предоставьте доступ к местоположению", Toast.LENGTH_SHORT).show();
                }
        }
    }

    public class LocationBroadcastReceiver extends BroadcastReceiver{

        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals("ACT_LOC")){
                lat = intent.getDoubleExtra("lat", 0f);
                lon = intent.getDoubleExtra("lon", 0f);
            }
        }
    }
}