package com.example.jklee.netproject;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.location.Address;
import android.location.Geocoder;
import android.os.Build;
import android.os.Handler;
import android.support.constraint.ConstraintLayout;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.format.Time;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.io.IOException;
import java.util.List;


public class MainActivity extends AppCompatActivity {

    Button button_refresh;

    public static Time mTime;
    Runnable run_Time;
    Handler timeHandler;

    Runnable run_Weather;
    Handler weatherHandler;
    static int WEATHER_RESET_TIME = 1000;

    TextView textView_Date;
    TextView textView_Time;
    TextView textView_AmPm;

    TextView textView_location;

     LinearLayout linearLayout_location;
     LinearLayout linearLayout_cloth;
    ConstraintLayout main_constraintLayout;

      public static ImageView imageView_weather;
    public static ImageView imageView_mask;
    public static ImageView imageView_umbrella;
    public static ImageView imageView_sunglasses;

    TextView textView_temperature;
    TextView textView_dust_densityinfo;
    TextView textView_pm10;
    TextView textView_pm10_num;
    TextView textView_pm25;
    TextView textView_pm25_num;
    TextView textView_cDialogTitle;
    //임시 강제 파라미터
    int dust_density = 259; // 미세먼지 농도
    public static String weather_api_key = "496073f3-0770-4307-be88-86970654ea17";

    FontSetting fontSetting;

    private final int PERMISSIONS_ACCESS_FINE_LOCATION = 1000;
    private final int PERMISSIONS_ACCESS_COARSE_LOCATION = 1001;
    private boolean isAccessFineLocation = false;
    private boolean isAccessCoarseLocation = false;
    private boolean isPermission = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        View view = getWindow().getDecorView();
        view.setSystemUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEN);

        normalSetting();

        setLocation();


        mTime = new Time();
        run_Time = new Runnable() {
            @Override
            public void run() {
                mTime.setToNow();
                showTime(mTime);
                timeHandler.postDelayed(run_Time, 1000);
            }
        };
        timeHandler = new Handler();
        timeHandler.postDelayed(run_Time, 1000);

        run_Weather = new Runnable() {
            @Override
            public void run() {
                Location.getLocation(getApplicationContext());
                setLocation();
                Weather.sendRequest4Weather(getApplicationContext());
                Weather.getWeatherData();
                setWeather();
                weatherHandler.postDelayed(run_Weather, WEATHER_RESET_TIME);
            }
        };
        weatherHandler = new Handler();
        weatherHandler.postDelayed(run_Weather, WEATHER_RESET_TIME);

        setMainBgColor(dust_density);

    }

    //뷰들의 ID와 폰트 설정
    public void normalSetting() {
        /* ID등록 */
        linearLayout_location  = findViewById(R.id.linearLayout_location);
        linearLayout_cloth = findViewById(R.id.linearLayout_cloth);

        //의류추천 레이아웃
        linearLayout_cloth.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                CustomDialog cd = new CustomDialog(MainActivity.this);
                cd.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                cd.setCancelable(true);
                cd.show();
                return false;
            }
        });
        main_constraintLayout = findViewById(R.id.main_constraintLayout);

        //새로고침 버튼
        button_refresh = findViewById(R.id.button_refresh);
        button_refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Location.getLocation(getApplicationContext());
                setLocation();
                Weather.sendRequest4Weather(getApplicationContext());
                Weather.getWeatherData();
                setWeather();
            }
        });

        imageView_weather = findViewById(R.id.imageView_weather);
        imageView_mask = findViewById(R.id.imageView_mask);
        imageView_mask.setVisibility(View.INVISIBLE);
        imageView_sunglasses = findViewById(R.id.imageView_sunglasses);
        imageView_sunglasses.setVisibility(View.INVISIBLE);
        imageView_umbrella = findViewById(R.id.imageView_umbrella);

        //위치
        textView_location = findViewById(R.id.textView_location);
        //시간
        textView_Date = (TextView) findViewById(R.id.textView_Date);
        textView_Time = (TextView) findViewById(R.id.textView_Time);
        textView_AmPm = (TextView) findViewById(R.id.textView_AmPm);
        //온도
        textView_temperature = findViewById(R.id.textView_temperature);
        //미세먼지
        textView_dust_densityinfo = findViewById(R.id.textView_dust_densityinfo);
        textView_pm10 = findViewById(R.id.textView_pm10);
        textView_pm10_num = findViewById(R.id.textView_pm10_num);
        textView_pm25 = findViewById(R.id.textView_pm25);
        textView_pm25_num = findViewById(R.id.textView_pm25_num);

        textView_cDialogTitle = findViewById(R.id.textView_cDialogTitle);

        /* 폰트 셋팅 */
        fontSetting = new FontSetting(getApplicationContext());
        //위치
        textView_location.setTypeface(fontSetting.getTypeface_Contents());
        //온도
        textView_temperature.setTypeface(fontSetting.getTypeface_Title());
        //시간
        textView_Date.setTypeface(fontSetting.getTypeface_Contents());
        textView_AmPm.setTypeface(fontSetting.getTypeface_Contents());
        textView_Time.setTypeface(fontSetting.getTypeface_Contents());
        //미세먼지
        textView_dust_densityinfo.setTypeface(fontSetting.getTypeface_Title());
        textView_pm10.setTypeface(fontSetting.getTypeface_Contents());
        textView_pm10_num.setTypeface(fontSetting.getTypeface_Title());
        textView_pm25.setTypeface(fontSetting.getTypeface_Contents());
        textView_pm25_num.setTypeface(fontSetting.getTypeface_Title());

        //얘는 왜 안되냥
        //textView_cDialogTitle.setTypeface(fontSetting.getTypeface_Title());

    }


    //미세먼지 농도에 따라 배경화면 색과 미세먼지 상태 표시
    public void setMainBgColor(int density) {

        textView_pm10_num.setText(density + "㎍/㎥");
        if (density < 31) // 좋음
        {
            main_constraintLayout.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.bg_blue));
            textView_dust_densityinfo.setText("미세먼지 : 좋음");
        } else if (density < 81) // 보통
        {
            main_constraintLayout.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.bg_green));
            textView_dust_densityinfo.setText("미세먼지 : 보통");
        } else if (density < 121) // 약간나쁨
        {
            main_constraintLayout.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.bg_yellow));
            textView_dust_densityinfo.setText("미세먼지 : 약간나쁨");
        } else if (density < 201) // 나쁨
        {
            main_constraintLayout.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.bg_orange));
            textView_dust_densityinfo.setText("미세먼지 : 나쁨");
        } else // 매우나쁨
        {
            main_constraintLayout.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.bg_red));
            textView_dust_densityinfo.setText("미세먼지 : 매우나쁨");
        }
    }

    public void setLocation()
    {
        //화면에 주소 출력
        textView_location.setText(Location.getAddress());
    }



    public void setWeather() {

        if (Weather.getTemperature() == -99)
            return;

        textView_temperature.setText(Weather.getTemperature() + "℃");

        System.out.println("Temperature : ==================" + Weather.getTemperature());
        System.out.println("Temperature : ==================" + Weather.getWeatherCode());

        WEATHER_RESET_TIME = 600000;

        boolean isAm = true;
        if (mTime.hour > 20 || mTime.hour < 7)
            isAm = false;

        switch (Weather.getWeatherCode()) {

            case "SKY_O01":
                if (isAm)
                    imageView_weather.setImageResource(R.drawable.a01_am);
                else
                    imageView_weather.setImageResource(R.drawable.a01_pm);
                break;
            case "SKY_O02":
                if (isAm)
                    imageView_weather.setImageResource(R.drawable.a02_am);
                else
                    imageView_weather.setImageResource(R.drawable.a02_pm);
                break;
            case "SKY_O03":
                if (isAm)
                    imageView_weather.setImageResource(R.drawable.a03_am);
                else
                    imageView_weather.setImageResource(R.drawable.a03_pm);
                break;
            case "SKY_O04":
                if (isAm)
                    imageView_weather.setImageResource(R.drawable.a04_am);
                else
                    imageView_weather.setImageResource(R.drawable.a04_pm);
                break;
            case "SKY_O05":
                if (isAm)
                    imageView_weather.setImageResource(R.drawable.a05_am);
                else
                    imageView_weather.setImageResource(R.drawable.a05_pm);
                break;
            case "SKY_O06":
                if (isAm)
                    imageView_weather.setImageResource(R.drawable.a06_am);
                else
                    imageView_weather.setImageResource(R.drawable.a06_pm);
                break;
            case "SKY_O07":
                imageView_weather.setImageResource(R.drawable.a07);
                break;
            case "SKY_O08":
                imageView_weather.setImageResource(R.drawable.a08);
                break;
            case "SKY_O09":
                imageView_weather.setImageResource(R.drawable.a09);
                break;
            case "SKY_O10":
                imageView_weather.setImageResource(R.drawable.a10);
                break;
            case "SKY_O11":
                imageView_weather.setImageResource(R.drawable.a11);
                break;
            case "SKY_O12":
                imageView_weather.setImageResource(R.drawable.a12);
                break;
            case "SKY_O13":
                imageView_weather.setImageResource(R.drawable.a13);
                break;
            case "SKY_O14":
                imageView_weather.setImageResource(R.drawable.a14);
                break;
            default:
                imageView_weather.setImageResource(R.drawable.a00);
                break;
        }
    }

    void showTime(Time mTime) {
        int hours, minutes, seconds, weekday, date, month;
        hours = mTime.hour;
        minutes = mTime.minute;
        seconds = mTime.second;
        weekday = mTime.weekDay;
        date = mTime.monthDay;
        month = mTime.month + 1;

        String cur_ampm = "AM";
        if (hours == 0) {
            hours = 12;
        }
        if (hours > 12) {
            hours = hours - 12;
            cur_ampm = "PM";
        }

        String text_time = String.format("%02d:%02d:%02d", hours, minutes, seconds);
        String day_of_week = "";
        switch (weekday) {
            case 0:
                day_of_week = "일요일";
                break;
            case 1:
                day_of_week = "월요일";
                break;
            case 2:
                day_of_week = "화요일";
                break;
            case 3:
                day_of_week = "수요일";
                break;
            case 4:
                day_of_week = "목요일";
                break;
            case 5:
                day_of_week = "금요일";
                break;
            case 6:
                day_of_week = "토요일";
                break;
        }
        String text_date = String.format("%d월 %d일 %s", month, date, day_of_week);
        textView_Time.setText(text_time);
        textView_Date.setText(text_date);
        textView_AmPm.setText(cur_ampm);

    }



    public static void maskOn()
    {
        imageView_mask.setVisibility(View.VISIBLE);
    }

    public static void maskOff()
    {
        imageView_mask.setVisibility(View.INVISIBLE);
    }

    public static void umbrellaOn()
    {
        imageView_umbrella.setVisibility(View.VISIBLE);
    }

    public static void umbrellaOff()
    {
        imageView_umbrella.setVisibility(View.INVISIBLE);
    }

    public static void sunglassesOn()
    {
        imageView_sunglasses.setVisibility(View.VISIBLE);
    }

    public static void sunglassesOff()
    {
        imageView_sunglasses.setVisibility(View.INVISIBLE);
    }

}
