package com.example.jklee.netproject;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;

public class WebStoreDialog extends Dialog {

    public Activity a;
    public Dialog d;
    public int index;
    private Context mContext;

    public WebStoreDialog(Activity c) {
        super(c);

        this.a = c;
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        View view = getWindow().getDecorView();
        view.setSystemUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEN);
        setContentView(R.layout.webstore_dialog);

        WebView webView = (WebView) findViewById(R.id.webView);
        webView.setWebViewClient(new myWebViewClient());
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setBuiltInZoomControls(true);
        Location location = new Location();
        //String url = "https://www.google.co.kr/maps/@" + String.valueOf(location.getLatitude()) + "," + String.valueOf(location.getLongitude()) + ",16z";
        String url = "http://m.store.musinsa.com/app/product/detail/721489/0";
        webView.loadUrl(url);

    }

    class myWebViewClient extends WebViewClient {

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }
    }
}