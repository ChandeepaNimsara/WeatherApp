package com.example.weatherapp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class SettingActivity2 extends AppCompatActivity {
private TextView textViewcityname;
private TextView textViewtempvalu;
AlertDialog.Builder builder;
int checkedItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting2);

        textViewcityname = (TextView) findViewById(R.id.city11);
        textViewtempvalu = (TextView) findViewById(R.id.temp22);

        builder = new AlertDialog.Builder(this);
        textViewcityname.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                citydialog();
            }
        });

        textViewtempvalu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tempdialog();
            }
        });
    }
    protected void citydialog(){
        LayoutInflater layoutInflater = LayoutInflater.from(SettingActivity2.this);
        View  cityview = layoutInflater.inflate(R.layout.changecity,null);
        AlertDialog.Builder newbuilder = new AlertDialog.Builder(SettingActivity2.this);
        newbuilder.setTitle("City");
        newbuilder.setView(cityview);

        EditText ce = (EditText) cityview.findViewById(R.id.cityedit);
        newbuilder.setCancelable(false).setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                Intent wentacc= new Intent(SettingActivity2.this,MainActivity.class);
                wentacc.putExtra("city",ce.getText().toString());
                startActivity(wentacc);
            }
        }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                dialog.cancel();
            }
        });
        AlertDialog al = newbuilder.create();
        al.show();
    }

    protected void tempdialog(){
        AlertDialog.Builder layoutInflater = new AlertDialog.Builder(SettingActivity2.this);
        layoutInflater.setTitle("Temperature Unit");
        String[] units = {"Celsius","Fahrenheit"};

        layoutInflater.setSingleChoiceItems(units, checkedItem, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {

                String sent_temperature;
                String temperature_unit;
                String getunitshort;
                switch (which){
                    case 0:
                        checkedItem=0;
                        sent_temperature="metric";
                        temperature_unit="Cel";
                        getunitshort="C";
                        Intent nextActivity = new Intent(SettingActivity2.this, MainActivity.class);
                        nextActivity.putExtra("temp12", sent_temperature);
                        nextActivity.putExtra("temp_unit", temperature_unit);
                        nextActivity.putExtra("unit_short", getunitshort);
                        startActivity(nextActivity);

                        break;
                    case 1:
                        checkedItem=1;
                        sent_temperature="imperial";
                        temperature_unit="Fah";
                        getunitshort="F";
                        Intent secondActivity = new Intent(SettingActivity2.this, MainActivity.class);
                        secondActivity.putExtra("temp12", sent_temperature);
                        secondActivity.putExtra("temp_unit", temperature_unit);
                        secondActivity.putExtra("temp_unit_short", getunitshort);
                        startActivity(secondActivity);
                        break;
                }
            }
        }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                dialog.cancel();
            }
        });
        AlertDialog alert=layoutInflater.create();
        alert.setCanceledOnTouchOutside(true);
        alert.show();
    }
}