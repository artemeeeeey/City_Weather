package com.example.jagaweather;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class Myadapter extends RecyclerView.Adapter<Myadapter.MyViewHolde> {
    String data1[],data2[];
    int image[];
    Context context1;
    public Myadapter(Context ct1, String[] weather_future, String[] description, int[] images){
        context1 = ct1;
        data1 = weather_future;
        data2 = description;
        image = images;


    }

    @NonNull
    @Override
    public MyViewHolde onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context1);
        View view = inflater.inflate(R.layout.forecast_new,parent,false);

        return new MyViewHolde(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolde holder, int position) {
        holder.myText1.setText(data1[position]);
        holder.myText2.setText(data2[position]);
        holder.myimnge.setImageResource(image[position]);
    }

    @Override
    public int getItemCount() {
        return image.length;
    }

    public class MyViewHolde extends RecyclerView.ViewHolder {
        TextView myText1,myText2;
        ImageView myimnge;

        public MyViewHolde(@NonNull View itemView) {
            super(itemView);
            myText1 = itemView.findViewById(R.id.temp);
            myText2 = itemView.findViewById(R.id.description);
            myimnge = itemView.findViewById(R.id.weather);

        }
    }
}
