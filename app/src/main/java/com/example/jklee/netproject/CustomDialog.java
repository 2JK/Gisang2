package com.example.jklee.netproject;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Window;

public class CustomDialog extends Dialog {

    public Activity a;
    public Dialog d;
    public int index;
    private Context mContext;


    public CustomDialog(Activity c) {
        super(c);

        this.a = c;
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.custom_dialog);

    }
}