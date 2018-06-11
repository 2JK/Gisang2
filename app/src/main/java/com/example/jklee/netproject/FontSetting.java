package com.example.jklee.netproject;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Typeface;

public class FontSetting {

    public Context context;
    public Typeface typeface_Sub = null;
    public Typeface typeface_Main = null;

    public FontSetting(Context context) {
        this.context = context;
        setFont();
    }

    public void setFont() {
        AssetManager assetManager = context.getResources().getAssets();
        typeface_Sub = Typeface.createFromAsset(assetManager, "Sub.ttf");
        typeface_Main = Typeface.createFromAsset(assetManager, "Main.ttf");
    }

    public Typeface getTypeface_Sub() {
        return typeface_Sub;
    }

    public Typeface getTypeface_Main() {
        return typeface_Main;
    }

}