package com.example.jklee.netproject;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Weather {

    public static String data;
    private static int temperature = -99;
    private static int temperature_max;
    private static int temperature_min;
    private static String weather;
    private static int dustDensity;

    private static String weatherCode = "";
    /* 하늘상태 코드명 SKY_O01:맑음, SKY_O02구름조금, SKY_O03:구름많음,SKY_O04:구름많고 비,
    SKY_O05:구름많고 눈, SKY_O06:구름많고 비 또는 눈, SKY_O07:흐림, SKY_O08:흐리고 비, SKY_O09:흐리고 눈,
    SKY_O10:흐리고 비 또는 눈, SKY_O11 : 흐리고 낙뢰 SKY_O12:뇌우/비, SKY_O13:뇌우/눈, SKY_O14:뇌우/비 또는 눈*/

    public static void getWeatherData() {

        JSONObject weatherJson;

        if (data != null) {
            try {
                weatherJson = new JSONObject(data);
                weatherJson = new JSONObject(weatherJson.getString("weather"));
                JSONArray weatherJsonArray = new JSONArray(weatherJson.getString("hourly"));

                weatherJson = new JSONObject(String.valueOf(weatherJsonArray.getJSONObject(0)));
                weatherJson = new JSONObject(weatherJson.getString("sky"));
                weather = weatherJson.getString("name");
                weatherCode = weatherJson.getString("code");
                weatherJson = new JSONObject(weatherJsonArray.getString(0));
                weatherJson = new JSONObject(weatherJson.getString("temperature"));
                temperature = weatherJson.getInt("tc");
                temperature_max = weatherJson.getInt("tmax");
                temperature_min = weatherJson.getInt("tmin");
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    }

    public static String getWeatherCode() {
        return weatherCode;
    }

    public static void setWeatherCode(String weatherCode) {
        Weather.weatherCode = weatherCode;
    }

    public static int getTemperature() {
        return temperature;
    }

    public static int getDustDensity() {
        return dustDensity;
    }

    public static String getWeather() {
        return weather;
    }

    public static void setTemperature(int temperature) {
        Weather.temperature = temperature;
    }

    public static void setDustDensity(int dustDensity) {
        Weather.dustDensity = dustDensity;
    }

    public static void setWeather(String weather) {
        Weather.weather = weather;
    }
}
