package com.example.jklee.netproject;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

public class IntroActivity extends AppCompatActivity {

    private Button buttonOk, buttonReset;
    private RadioButton radioButtonAge10, radioButtonAge20, radioButtonAge30;
    private RadioButton radioButtonGenderMale, radioButtonGenderFemale;

    private TextView textView_gisang2;
    private TextView textView_gisang2_2;
    private TextView textView_gisang2_3;
    private TextView textView_gisang2_gender;
    private TextView textView_gisang2_age;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);

        buttonOk = (Button) findViewById(R.id.btn_ok);
        buttonReset = (Button) findViewById(R.id.btn_reset);
        radioButtonAge10 = (RadioButton) findViewById(R.id.rbtn_10);
        radioButtonAge20 = (RadioButton) findViewById(R.id.rbtn_20);
        radioButtonAge30 = (RadioButton) findViewById(R.id.rbtn_30);
        radioButtonGenderMale = (RadioButton) findViewById(R.id.rbtn_male);


        radioButtonGenderFemale = (RadioButton) findViewById(R.id.rbtn_female);

        FontSetting fontSetting = new FontSetting(getApplicationContext());

        radioButtonGenderMale.setTypeface(fontSetting.getTypeface_Main());
        radioButtonGenderFemale.setTypeface(fontSetting.getTypeface_Main());
        radioButtonAge10.setTypeface(fontSetting.getTypeface_Main());
        radioButtonAge20.setTypeface(fontSetting.getTypeface_Main());
        radioButtonAge30.setTypeface(fontSetting.getTypeface_Main());
        buttonOk.setTypeface(fontSetting.getTypeface_Sub());
        buttonReset.setTypeface(fontSetting.getTypeface_Sub());

        textView_gisang2 = findViewById(R.id.textView_gisang2);
        textView_gisang2.setTypeface(fontSetting.getTypeface_Main());
        textView_gisang2_2 = findViewById(R.id.textView_gisang2_2);
        textView_gisang2_2.setTypeface(fontSetting.getTypeface_Main());
        textView_gisang2_3 = findViewById(R.id.textView_gisang2_3);
        textView_gisang2_3.setTypeface(fontSetting.getTypeface_Main());
        textView_gisang2_gender = findViewById(R.id.textView_gisang2_gender);
        textView_gisang2_gender.setTypeface(fontSetting.getTypeface_Main());
        textView_gisang2_age = findViewById(R.id.textView_gisang2_age);
        textView_gisang2_age.setTypeface(fontSetting.getTypeface_Main());

        buttonOk.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                if ((radioButtonAge10.isChecked() || radioButtonAge20.isChecked() || radioButtonAge30.isChecked())
                        && (radioButtonGenderMale.isChecked() || radioButtonGenderFemale.isChecked())) {
                    SharedPreferences pref = getSharedPreferences("gisang", Activity.MODE_PRIVATE);
                    SharedPreferences.Editor editor = pref.edit();

                    if (radioButtonGenderMale.isChecked()) {
                        editor.putBoolean("isMale", true);
                        editor.commit();
                    } else {
                        editor.putBoolean("isMale", false);
                        editor.commit();
                    }
                    Toast.makeText(getApplicationContext(), "환영합니다!", Toast.LENGTH_LONG).show();
                    if (pref.getBoolean("isMale", false))
                        MainActivity.Gender = "male";
                    else
                        MainActivity.Gender = "female";
                    editor.putBoolean("isSelected", true);
                    finish();
                } else {
                    Toast.makeText(getApplicationContext(), "나이 및 성별 정보를 선택해주세요.", Toast.LENGTH_LONG).show();
                }
            }
        });

        buttonReset.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                radioButtonAge10.setChecked(false);
                radioButtonAge20.setChecked(false);
                radioButtonAge30.setChecked(false);
                radioButtonGenderMale.setChecked(false);
                radioButtonGenderFemale.setChecked(false);
            }
        });

    }
}