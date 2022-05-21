package com.example.jagaweather;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.work.Data;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

//https://api.openweathermap.org/data/2.5/forecast?q=ufa&appid=238d9c2c4369d56d04e268ffe5b14143&units=metric&lang=ru

public class getWeather extends Worker {
    String city = getInputData().getString("city");
    String appid = "238d9c2c4369d56d04e268ffe5b14143";
    String path = "https://api.openweathermap.org/data/2.5/forecast?q=" + city
            + "&appid=" + appid + "&units=metric&lang=ru";
    Data output = new Data.Builder().build();
    String[] temps = new String[40];
    String[] feels_like = new String[40];
    String[] description = new String[40];
    String[] time = new String[40];


    public getWeather(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }

    @NonNull
    @Override
    public Result doWork() {
        HttpsURLConnection httpsURLConnection = null;
        BufferedReader bufferedReader = null;

        try {
            URL url = new URL(path);
            httpsURLConnection = (HttpsURLConnection) url.openConnection();
            httpsURLConnection.connect();
            InputStream inputStream = httpsURLConnection.getInputStream();
            bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

            StringBuffer stringBuffer = new StringBuffer();
            String string = "";

            while ((string = bufferedReader.readLine()) != null) {
                stringBuffer.append(string).append("\n");
            }

            String result = stringBuffer.toString();
            Log.d("json_result", result);

            try {
                JSONObject json = new JSONObject(result);
                Log.d("result_json", json.toString());
                JSONArray list = json.getJSONArray("list");

                for (int i = 0; i < 40; i++){
                    JSONObject weather_data = list.getJSONObject(i);
                    temps[i] = weather_data.getJSONObject("main").getDouble("temp") + "℃";
                    feels_like[i] = weather_data.getJSONObject("main").getDouble("feels_like") + "℃";
                    description[i] = weather_data.getJSONArray("weather").getJSONObject(0).getString("description");
                    time[i] = weather_data.getString("dt_txt").split(" ")[1].split(":")[0] + ":"
                            + weather_data.getString("dt_txt").split(" ")[1].split(":")[1];
                }

                Log.d("temps", temps.toString());
                Log.d("feels_like", feels_like.toString());
                Log.d("description", description.toString());
                Log.d("time", time.toString());

                output = new Data.Builder().putStringArray("temps", temps)
                        .putStringArray("feels_like", feels_like)
                        .putStringArray("description", description)
                        .putStringArray("time", time).build();

            } catch (JSONException e) {
                e.printStackTrace();
                return Result.failure();
            }

        } catch (MalformedURLException e) {
            e.printStackTrace();
            return Result.failure();
        } catch (IOException e) {
            e.printStackTrace();
            return Result.failure();
        } finally {
            if (httpsURLConnection != null) {
                httpsURLConnection.disconnect();
            }

            if (bufferedReader != null) {
                try {
                    bufferedReader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return Result.success(output);
    }
}
