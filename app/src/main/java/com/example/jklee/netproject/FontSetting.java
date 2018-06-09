package com.example.jklee.netproject;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Typeface;

public class FontSetting {

    public Context context;
    public Typeface typeface_Title = null;
    public Typeface typeface_Contents = null;
    public Typeface typeface_RIX = null;

    public FontSetting(Context context) {
        this.context = context;
        setFont();
    }

    public void setFont() {
        AssetManager assetManager = context.getResources().getAssets();
        typeface_Title = Typeface.createFromAsset(assetManager, "Bold.ttf");
        typeface_Contents = Typeface.createFromAsset(assetManager, "Regular.ttf");
        typeface_RIX = Typeface.createFromAsset(assetManager, "RIX.ttf");
    }

    public Typeface getTypeface_Title() {
        return typeface_Title;
    }

    public Typeface getTypeface_Contents() {
        return typeface_Contents;
    }

    public Typeface getTypeface_RIX() {
        return typeface_RIX;
    }

}