package com.badrul.qnitibox;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class WebViewPayment extends AppCompatActivity {


    private WebView wv1;

    String details;
    String amount;
    String name;
    String email;
    String phone;
    String url;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view_payment);

        sharedPreferences = getSharedPreferences(Config.SHARED_PREF_NAME, Context.MODE_PRIVATE);
        name = sharedPreferences.getString(Config.NAME_ID2, "Not Available");
        phone = sharedPreferences.getString(Config.PHONE_ID2, "Not Available");
        email = sharedPreferences.getString(Config.EMAIL_ID2, "Not Available");
        details = sharedPreferences.getString(Config.MENU_TYPE, "Not Available");
        amount = sharedPreferences.getString(Config.FOOD_PRICE_ALL, "99999.99");
        wv1 = findViewById(R.id.webview);
        wv1.setWebViewClient(new MyBrowser());
        wv1.getSettings().setLoadsImagesAutomatically(true);
        wv1.getSettings().setJavaScriptEnabled(true);
        wv1.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
        url = "https://myqr.qniti.com/paymentdemo/paymentregister.php?detail=" + details + "&amount=" + amount + "&name=" + name + "&email=" + email + "&phone=" + phone;
        wv1.loadUrl(url);
/*
        wv1.setWebChromeClient(new WebChromeClient() {
            @Override
            public boolean onJsAlert(WebView view, String url, String message, JsResult result) {

                Toast.makeText(WebViewPayment.this, message, Toast.LENGTH_LONG).show();

                return super.onJsAlert(view, url, message, result);
            }
        });*/
    }


    private class MyBrowser extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }
    }

    public void onBackPressed() {
        Intent i = new Intent(WebViewPayment.this, NewIndvOrder.class);
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(i);
        finish();
    }
}