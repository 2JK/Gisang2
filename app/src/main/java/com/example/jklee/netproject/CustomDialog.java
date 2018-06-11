package com.example.jklee.netproject;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
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
    Button button_webStore;
    FontSetting fontSetting;
    int temperature;

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

        button_webStore = findViewById(R.id.button_store);
        button_webStore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                WebStoreDialog cd = new WebStoreDialog(a);
                cd.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                cd.setCancelable(true);
                cd.show();
            }
        });

    }

    public void setDialog() {
        temperature = Weather.getTemperature();
        String gender = MainActivity.Gender;
        Log.d("dialog_gender", gender);
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
        } else if (temperature < 9) {
            textView_cDialogTitleTemper.setText("5℃~8℃");
            imageView_cloth.setImageResource(R.drawable.g_cloth_5_8);
        } else if (temperature < 12) {
            textView_cDialogTitleTemper.setText("9℃~11℃");
            imageView_cloth.setImageResource(R.drawable.g_cloth_9_11);
        } else if (temperature < 17) {
            textView_cDialogTitleTemper.setText("12℃~16℃");
            imageView_cloth.setImageResource(R.drawable.g_cloth_12_16);
        } else if (temperature < 20) {
            textView_cDialogTitleTemper.setText("17℃~19℃");
            imageView_cloth.setImageResource(R.drawable.g_cloth_17_19);
        } else if (temperature < 23) {
            textView_cDialogTitleTemper.setText("20℃~22℃");
            imageView_cloth.setImageResource(R.drawable.g_cloth_20_22);
        } else if (temperature < 28) {
            textView_cDialogTitleTemper.setText("23℃~27℃");
            switch (gender) {
                case "male":
                    imageView_cloth.setImageResource(R.drawable.m_cloth_23_27);
                    break;
                case "female":
                    imageView_cloth.setImageResource(R.drawable.w_cloth_23_27);
                    break;
            }
        } else {
            textView_cDialogTitleTemper.setText("28℃~");
            switch (gender) {
                case "male":
                    imageView_cloth.setImageResource(R.drawable.m_cloth_28);
                    break;
                case "female":
                    imageView_cloth.setImageResource(R.drawable.m_cloth_28);
                    break;
            }
        }
    }
}