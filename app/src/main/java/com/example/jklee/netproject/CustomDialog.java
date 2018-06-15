package com.example.jklee.netproject;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

public class CustomDialog extends Dialog {

    public Activity a;
    public Dialog d;
    public int index;
    private Context mContext;
    TextView textView_cDialogTitle;
    TextView textView_cDialogTitleTemper;
    ImageView imageView_gender;
    ImageView imageView_cloth;
    TextView textView_webStore;
    FontSetting fontSetting;
    int temperature;
    private String url = "http://m.store.musinsa.com/";

    public CustomDialog(Activity c) {
        super(c);

        this.a = c;
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        View view = getWindow().getDecorView();
        view.setSystemUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEN);
        setContentView(R.layout.custom_dialog);
        fontSetting = new FontSetting(getContext());

        textView_cDialogTitle = findViewById(R.id.textView_cDialogTitle);
        textView_cDialogTitle.setTypeface(fontSetting.getTypeface_Main());
        textView_cDialogTitleTemper = findViewById(R.id.textView_cDialogTitleTemp);
        textView_cDialogTitleTemper.setTypeface(fontSetting.getTypeface_Sub());

        imageView_gender = findViewById(R.id.imageView_gender);
        imageView_cloth = findViewById(R.id.imageview_cloth);

        setDialog();

        textView_webStore = findViewById(R.id.textView_store);
        textView_webStore.setTypeface(fontSetting.getTypeface_Sub());
        textView_webStore.setText(Html.fromHtml("<u>" + "▶ 추천코디 보러가기 ◀" + "</u>"));
        textView_webStore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                WebStoreDialog cd = new WebStoreDialog(a, url);
                cd.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                cd.setCancelable(true);
                cd.show();
            }
        });

    }

    public void setDialog() {
        temperature = Weather.getTemperature();
        String gender = MainActivity.Gender;

        switch (gender) {
            case "male":
                imageView_gender.setImageResource(R.drawable.icon_male);
                break;
            case "female":
                imageView_gender.setImageResource(R.drawable.icon_female);
                break;
        }

        if (temperature < 5) {
            textView_cDialogTitleTemper.setText("4℃~");
            imageView_cloth.setImageResource(R.drawable.g_cloth_4);
            switch (gender) {
                case "male":
                                 url ="http://m.store.musinsa.com/app/staff/views/26935?sex=&year_date=2017&month_date=&brand=&selshop=&display_cnt=60&page=1";
                    break;
                case "female":
                    url = "http://m.wusinsa.musinsa.com/app/styles/views/3210?sex=28&brand=&model=&max_rt=2018&min_rt=2015&year_date=2017&month_date=&display_cnt=60&list_kind=small&sort=rt&page=2";
                    break;
            }
        } else if (temperature < 9) {
            textView_cDialogTitleTemper.setText("5℃~8℃");
            imageView_cloth.setImageResource(R.drawable.g_cloth_5_8);
            switch (gender) {
                case "male":
                    url = "http://m.store.musinsa.com/app/staff/views/26969?sex=&year_date=2018&month_date=&brand=&selshop=&display_cnt=60&page=77";
                    break;
                case "female":
                    url = "http://m.wusinsa.musinsa.com/app/styles/views/3500?sex=28&brand=&model=&max_rt=2018&min_rt=2015&year_date=2018&month_date=&display_cnt=60&list_kind=small&sort=rt&page=16";
                    break;
            }
        } else if (temperature < 12) {
            textView_cDialogTitleTemper.setText("9℃~11℃");
            imageView_cloth.setImageResource(R.drawable.g_cloth_9_11);
            switch (gender) {
                case "male":
                    url ="http://m.store.musinsa.com/app/staff/views/27340?sex=&year_date=2018&month_date=&brand=&selshop=&display_cnt=60&page=72";
                    break;
                case "female":
                    url = "http://m.wusinsa.musinsa.com/app/styles/views/3526?sex=28&brand=&model=&max_rt=2018&min_rt=2015&year_date=2018&month_date=&display_cnt=60&list_kind=small&sort=rt&page=15";
                    break;
            }
        } else if (temperature < 17) {
            textView_cDialogTitleTemper.setText("12℃~16℃");
            imageView_cloth.setImageResource(R.drawable.g_cloth_12_16);
            switch (gender) {
                case "male":
                    url ="http://m.store.musinsa.com/app/staff/views/27442?sex=&year_date=2018&month_date=&brand=&selshop=&display_cnt=60&page=70";
                    break;
                case "female":
                    url = "http://m.wusinsa.musinsa.com/app/styles/views/3614?sex=28&brand=&model=&max_rt=2018&min_rt=2015&year_date=2018&month_date=&display_cnt=60&list_kind=small&sort=rt&page=15";
                    break;
            }
        } else if (temperature < 20) {
            textView_cDialogTitleTemper.setText("17℃~19℃");
            imageView_cloth.setImageResource(R.drawable.g_cloth_17_19);
            switch (gender) {
                case "male":
                    url ="http://m.store.musinsa.com/app/staff/views/27425?sex=&year_date=2018&month_date=&brand=&selshop=&display_cnt=60&page=70";
                    break;
                case "female":
                    url = "http://m.wusinsa.musinsa.com/app/styles/views/4193?sex=28&brand=&model=&max_rt=2018&min_rt=2015&year_date=2018&month_date=&display_cnt=60&list_kind=small&sort=rt&page=10";
                    break;
            }
        } else if (temperature < 23) {
            textView_cDialogTitleTemper.setText("20℃~22℃");
            imageView_cloth.setImageResource(R.drawable.g_cloth_20_22);
            switch (gender) {
                case "male":
                    url ="http://m.store.musinsa.com/app/staff/views/27825?sex=&year_date=2018&month_date=&brand=&selshop=&display_cnt=60&page=65";
                    break;
                case "female":
                    url = "http://m.wusinsa.musinsa.com/app/styles/views/4611?sex=28&brand=&model=&max_rt=2018&min_rt=2015&year_date=2018&month_date=&display_cnt=60&list_kind=small&sort=rt&page=7";
                    break;
            }
        } else if (temperature < 28)

        {
            textView_cDialogTitleTemper.setText("23℃~27℃");
            switch (gender) {
                case "male":
                    imageView_cloth.setImageResource(R.drawable.m_cloth_23_27);
                    url = "http://m.store.musinsa.com/app/staff/views/31982?sex=&year_date=2018&month_date=&brand=&selshop=&display_cnt=60&page=1";
                    break;
                case "female":
                    imageView_cloth.setImageResource(R.drawable.w_cloth_23_27);
                    url = "http://m.wusinsa.musinsa.com/app/staff/views/31857";
                    break;
            }
        } else
        {
            textView_cDialogTitleTemper.setText("28℃~");
            switch (gender) {
                case "male":
                    imageView_cloth.setImageResource(R.drawable.m_cloth_28);
                    url = "http://m.store.musinsa.com/app/staff/views/32012?sex=&year_date=2018&month_date=&brand=&selshop=&display_cnt=60&page=1";
                    break;
                case "female":
                    imageView_cloth.setImageResource(R.drawable.m_cloth_28);
                    url = "http://m.wusinsa.musinsa.com/app/staff/views/31942";
                    break;
            }
        }
    }

    public void setStoreUrl(String gender, String url)
    {
        switch (gender) {
            case "male":
                this.url = url;
                break;
            case "female":
                this.url = url;
                break;
        }
    }

}