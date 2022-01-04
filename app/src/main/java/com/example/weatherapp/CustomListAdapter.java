package com.example.weatherapp;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import com.squareup.picasso.Picasso;

public class CustomListAdapter extends ArrayAdapter<String> {

    private final Activity context;
    private final String[] dayname;
    private final String[] descriptionname;
    private final String[] temvalue;
    private final String[] wpic;
    public CustomListAdapter(Activity context, String[] dayname, String[] descriptionname, String[] temvalue, String[] wpic) {

        super(context,R.layout.thelist,temvalue);
        this.context = context;
        this.dayname = dayname;
        this.descriptionname = descriptionname;
        this.temvalue = temvalue;
        this.wpic = wpic;
    }
    @NonNull
    @Override
    public View getView(int position, @NonNull View view, @NonNull ViewGroup parent){
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView  =inflater.inflate(R.layout.thelist,null,true);


        TextView txtday = (TextView) rowView.findViewById(R.id.day);
        TextView txtdes = (TextView) rowView.findViewById(R.id.description);
        TextView txttem = (TextView) rowView.findViewById(R.id.temperature);
        ImageView imgday= (ImageView) rowView.findViewById(R.id.weatherpic);


        txtday.setText(dayname[position]);
        txtdes.setText((descriptionname[position]));
        txttem.setText(temvalue[position]);
        Picasso.get().load("http://openweathermap.org/img/w/"+wpic[position]+".png").into(imgday);

        return rowView;
    }
}
