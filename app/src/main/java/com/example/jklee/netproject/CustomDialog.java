package com.example.jklee.netproject;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Window;
import android.widget.TextView;

public class CustomDialog extends Dialog {

    public Activity a;
    public Dialog d;
    public int index;
    private Context mContext;
    TextView textView_cDialogTitle;
    TextView textView_cDialogTitleTemper;
    FontSetting fontSetting;
    int temperature;

    public CustomDialog(Activity c) {
        super(c);

        this.a = c;
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.custom_dialog);
        fontSetting = new FontSetting(getContext());

       textView_cDialogTitle = findViewById(R.id.textView_cDialogTitle);
        textView_cDialogTitle.setTypeface(fontSetting.getTypeface_RIX());
        textView_cDialogTitleTemper = findViewById(R.id.textView_cDialogTitleTemp);
        textView_cDialogTitleTemper.setTypeface(fontSetting.getTypeface_RIX());

        temperature = Weather.getTemperature();
        if (temperature < 5)
            textView_cDialogTitleTemper.setText("4℃~");
        else if (temperature < 9)
            textView_cDialogTitleTemper.setText("5℃~8℃");
        else if (temperature < 12)
            textView_cDialogTitleTemper.setText("9℃~11℃");
        else if (temperature < 17)
            textView_cDialogTitleTemper.setText("12℃~16℃");
        else if (temperature < 20)
            textView_cDialogTitleTemper.setText("17℃~19℃");
        else if (temperature < 23)
            textView_cDialogTitleTemper.setText("20℃~22℃");
        else if (temperature < 28)
            textView_cDialogTitleTemper.setText("23℃~27℃");
        else
            textView_cDialogTitleTemper.setText("28℃~");



    }


}