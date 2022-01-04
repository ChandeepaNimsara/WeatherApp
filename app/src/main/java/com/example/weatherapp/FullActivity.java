package com.example.weatherapp;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

public class FullActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full);

        Bundle bundle = getIntent().getExtras();
        String tem = bundle.getString("temperature");
        String p = bundle.getString("daywpic");
        String d = bundle.getString("descprip");
        String h = bundle.getString("newhum");
        //String cc = bundle.getString("city0");

       // Toast.makeText(getApplicationContext(),h, Toast.LENGTH_LONG).show();

        TextView c = (TextView) findViewById(R.id.cel);
        c.setText(tem);

       ImageView pic1 = (ImageView) findViewById(R.id.imagefull);
       Picasso.get().load("http://openweathermap.org/img/w/"+p+".png").into(pic1);


        TextView di = (TextView) findViewById(R.id.clouds);
        di.setText(d);

       TextView hi = (TextView) findViewById(R.id.newhumi);
       hi.setText(h);

//       TextView ci = (TextView) findViewById(R.id.cityupdate123);
//       ci.setText(cc);

    }
}