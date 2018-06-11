package com.example.jklee.netproject;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Handler;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.format.Time;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.Random;


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

    TextView textView_cloth;

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
    TextView textView_pm10_unit;
    TextView textView_pm25;
    TextView textView_pm25_num;
    TextView textView_pm25_unit;

    //임시 강제 파라미터
    int dust_density = 259; // 미세먼지 농도
    public static String weather_api_key = "496073f3-0770-4307-be88-86970654ea17";

    FontSetting fontSetting;

    private final int PERMISSIONS_ACCESS_FINE_LOCATION = 1000;
    private final int PERMISSIONS_ACCESS_COARSE_LOCATION = 1001;
    private boolean isAccessFineLocation = false;
    private boolean isAccessCoarseLocation = false;
    private boolean isPermission = false;

    public static String Gender;
    SharedPreferences pref;

    @Override
    protected void onPostResume() {
        super.onPostResume();
        View view = getWindow().getDecorView();
        view.setSystemUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEN);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        View view = getWindow().getDecorView();
        view.setSystemUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEN);

        firstExecute();
        normalSetting();


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

        setLocation();

        setWeather();

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

        Weather.setPm10(30);
        setMainBgColor(Weather.getPm10());

    }

    private void firstExecute() {
        pref = getSharedPreferences("gisang", MODE_PRIVATE);
        boolean isAlreadyExecuted = pref.getBoolean("isAlreadyExecuted", false);
        if (isAlreadyExecuted == false) {
            SharedPreferences.Editor editor = pref.edit();
            editor.putBoolean("isAlreadyExecuted", true);
            editor.commit();

            Intent intent = new Intent(getApplicationContext(), IntroActivity.class);
            startActivity(intent);
        } else {
            if (pref.getBoolean("isMale", false))
                Gender = "male";
            else
                Gender = "female";
        }
    }

    //뷰들의 ID와 폰트 설정
    public void normalSetting() {
        /* ID등록 */
        linearLayout_location = findViewById(R.id.linearLayout_location);
        linearLayout_cloth = findViewById(R.id.linearLayout_cloth);

        //의류추천 레이아웃
        linearLayout_cloth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CustomDialog cd = new CustomDialog(MainActivity.this);
                cd.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                cd.setCancelable(true);
                cd.show();
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
                Random random = new Random();
                setMainBgColor(random.nextInt(280));

            }
        });

        imageView_weather = findViewById(R.id.imageView_weather);
        imageView_mask = findViewById(R.id.imageView_mask);
        imageView_sunglasses = findViewById(R.id.imageView_sunglasses);
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
        textView_pm10_unit = findViewById(R.id.textView_pm10_unit);
        textView_pm25_unit = findViewById(R.id.textView_pm25_unit);
        textView_cloth = findViewById(R.id.textView_cloth);


        /* 폰트 셋팅 */
        fontSetting = new FontSetting(getApplicationContext());
        //위치
        textView_location.setTypeface(fontSetting.getTypeface_Main());
        //온도
        textView_temperature.setTypeface(fontSetting.getTypeface_Main());
        //시간
        textView_Date.setTypeface(fontSetting.getTypeface_Main());
        textView_AmPm.setTypeface(fontSetting.getTypeface_Main());
        textView_Time.setTypeface(fontSetting.getTypeface_Main());
        //미세먼지
        textView_dust_densityinfo.setTypeface(fontSetting.getTypeface_Main());
        textView_pm10.setTypeface(fontSetting.getTypeface_Main());
        textView_pm10_num.setTypeface(fontSetting.getTypeface_Main());
        textView_pm10_unit.setTypeface(fontSetting.getTypeface_Sub());
        textView_pm25.setTypeface(fontSetting.getTypeface_Main());
        textView_pm25_num.setTypeface(fontSetting.getTypeface_Main());
        textView_pm25_unit.setTypeface(fontSetting.getTypeface_Sub());

        textView_cloth.setTypeface(fontSetting.getTypeface_Main());

    }


    //미세먼지 농도에 따라 배경화면 색과 미세먼지 상태 표시
    public void setMainBgColor(int density) {

        textView_pm10_num.setText(density + "㎍/㎥");
        if (density < 31) // 좋음
        {
            main_constraintLayout.setBackgroundColor(Color.parseColor("#4d90eb"));
            textView_dust_densityinfo.setText("미세먼지 : 좋음");
            maskOff();
        } else if (density < 81) // 보통
        {
            main_constraintLayout.setBackgroundColor(Color.parseColor("#52ac52"));
            textView_dust_densityinfo.setText("미세먼지 : 보통");
            maskOff();
        } else if (density < 121) // 약간나쁨
        {
            main_constraintLayout.setBackgroundColor(Color.parseColor("#d8c03d"));
            textView_dust_densityinfo.setText("미세먼지 : 약간나쁨");
            maskOn();
        } else if (density < 201) // 나쁨
        {
            main_constraintLayout.setBackgroundColor(Color.parseColor("#d87f3d"));
            textView_dust_densityinfo.setText("미세먼지 : 나쁨");
            maskOn();
        } else // 매우나쁨
        {
            main_constraintLayout.setBackgroundColor(Color.parseColor("#d83d57"));
            textView_dust_densityinfo.setText("미세먼지 : 매우나쁨");
            maskOn();
        }
    }

    public void setLocation() {
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

        String cur_ampm = "오전";
        if (hours == 0) {
            hours = 12;
        }
        if (hours > 12) {
            hours = hours - 12;
            cur_ampm = "오후";
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


    public static void maskOn() {
        imageView_mask.setImageResource(R.drawable.icon_mask);
    }

    public static void maskOff() {
        imageView_mask.setImageResource(R.drawable.icon_mask_off);
    }

    public static void umbrellaOn() {
        imageView_umbrella.setImageResource(R.drawable.icon_umbrella);
    }

    public static void umbrellaOff() {
        imageView_umbrella.setImageResource(R.drawable.icon_umbrella_off);
    }

    public static void sunglassesOn() {
        imageView_sunglasses.setImageResource(R.drawable.icon_sunglasses);
    }

    public static void sunglassesOff() {
        imageView_sunglasses.setImageResource(R.drawable.icon_sunglasses_off);
    }

}
