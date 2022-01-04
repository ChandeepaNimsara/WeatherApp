package com.example.weatherapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.DatagramSocket;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import android.os.Bundle;
import android.widget.Toast;

import java.io.BufferedReader;
import java.net.HttpURLConnection;
import java.text.DecimalFormat;
import java.util.Calendar;

import static android.icu.lang.UCharacter.GraphemeClusterBreak.L;

public class MainActivity extends AppCompatActivity {

    String forecastJsonStr;
    HttpURLConnection urlConnection;
    BufferedReader reader;

    String[] listofday = {"Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"};
    String[] Rearranged = {"", "", "", "", "", "", ""};
    int location = 0;
    int start = Calendar.getInstance().get(Calendar.DAY_OF_WEEK) - 1;

    String[] txtTemp = {"", "", "", "", "", "", ""};
    String[] imgday = {"", "", "", "", "", "", ""};
    String[] txtdes = {"", "", "", "", "", "", ""};
    String[] txthum = {"", "", "", "", "", "", ""};
   // String[] updatecity = {""};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        for (int x = start; x<listofday.length; x++) {
            Rearranged[location] = listofday[x];
            location++;
        }

        for (int y = 0; y < start; y++) {
            Rearranged[location] = listofday[y];
            location++;
        }

        for (int z = 0; z < Rearranged.length; z++) {
            System.out.println(Rearranged[z] + "\n");
        }

        WeatherData myweatherdata = new WeatherData();
        myweatherdata.execute();

        CustomListAdapter adapter = new CustomListAdapter(this, Rearranged, txtdes, txtTemp, imgday );
        ListView list = (ListView) findViewById(R.id.newlist);
        list.setAdapter(adapter);


        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {                            //for check data
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
//                String SelectedItem = myweatherdata.txtTemp[+position];
//                Toast.makeText(getApplicationContext(), SelectedItem, Toast.LENGTH_SHORT).show();

                Intent openSecondActivity = new Intent(MainActivity.this, FullActivity.class);
                openSecondActivity.putExtra("temperature",txtTemp[position]);
                openSecondActivity.putExtra("daywpic",imgday[position]);
                openSecondActivity.putExtra("descprip",txtdes[position]);
                openSecondActivity.putExtra("newhum",txthum[position]);
               // openSecondActivity.putExtra("new1city",updatecity[position]);
                startActivity(openSecondActivity);
            }
        });

    }

    public class WeatherData extends AsyncTask<String, Void, String> {

        JSONObject[] days = {null, null, null, null, null, null, null};



        @Override
        protected String doInBackground(String... strings) {
            HttpURLConnection urlConnection = null;
            BufferedReader reader = null ;
            forecastJsonStr="";
            try {
                final String BASE_URL = "https://api.openweathermap.org/data/2.5/forecast/daily?q=galle&cnt=7&appid=a18b978603316d47c572d98d52a420f6";
                URL url = new URL(BASE_URL);

                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.connect();

                InputStream inputStream = urlConnection.getInputStream();
                StringBuffer buffer = new StringBuffer();

                if (inputStream == null) {
                    return null;
                }
                reader = new BufferedReader(new InputStreamReader(inputStream));

                String line1;

                while ((line1 = reader.readLine()) != null) {
                    buffer.append(line1 + "\n");
                }
                if (buffer.length() == 0) {
                    return null;
                }
                forecastJsonStr = buffer.toString();

            } catch (IOException e) {
                Log.e("Hi", "Error", e);
                return null;
            } finally {
                if (urlConnection != null) {
                    urlConnection.disconnect();
                }
                if (reader != null) {
                    try {
                        reader.close();
                    } catch (final IOException e) {
                        Log.e("Hi", "Error closing string", e);
                    }
                }
            }
            return null;
        }

        public String[] getDescription(){
            return txtdes;
        }

        public String[] getTemperature(){
            return txtTemp;
        }

        public String[] getWeatherIcon(){
            return imgday;
        }

        public String[] getHumidity(){
            return txthum;
        }

       // public String[] getmycity(){ return updatecity;}

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(String s) {

            try {

                JSONObject J1 = new JSONObject(forecastJsonStr);
                JSONObject City = J1.getJSONObject("city");

//                String city = City.getString("name");
//                updatecity[0]=city;


                JSONObject jsonObject = new JSONObject(forecastJsonStr);
                JSONArray listofday = jsonObject.getJSONArray("list");

                for (int m = 0; m < days.length; m++) {
                    JSONObject obj = listofday.getJSONObject(m);
                    days[m] = obj;
                }

                for (int d = 0; d < days.length; d++) {
                    JSONArray WeatherArray = days[d].getJSONArray("weather");
                    JSONObject Weatheropen = WeatherArray.getJSONObject(0);
                    String onedes = Weatheropen.getString("description");
                    String iconWeather = Weatheropen.getString("icon");
                    txtdes[d] = onedes;
                    imgday[d] = iconWeather;
                }

                for (int v = 0; v < days.length; v++) {
                    JSONObject temp = days[v].getJSONObject("temp");
                    String humi1 = days[v].getString("humidity");
                    double temDouble = Double.parseDouble(temp.getString("day")) - 273.15;
                    String temforlist = new DecimalFormat("0.0").format(temDouble);
                    txtTemp[v] = temforlist;
                    txthum[v] = humi1;

                }

                for (int q = 0; q < days.length; q++) {
                    JSONArray WeatherArray = days[q].getJSONArray("weather");
                    JSONObject Weather0 = WeatherArray.getJSONObject(0);
                    String onedes = Weather0.getString("description");
                    txtdes[q] = onedes;
                }

                for (int n = 0; n < days.length; n++) {
                    Log.d("myLog", txtdes[3]);
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        @Override
        protected void onProgressUpdate(Void... Values) {

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.option, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if(id==R.id.about1){
            Intent aboutintent = new Intent(MainActivity.this, AboutActivity2.class);
            startActivity(aboutintent);
        }

        if(id==R.id.settings1){
            Intent settingintent = new Intent(MainActivity.this, SettingActivity2.class);
            startActivity(settingintent);

        }
        return super.onOptionsItemSelected(item);
    }
}