package com.f126219.recordarcheryscoring;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.webkit.WebView;

public class userGuide extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_guide);

        WebView userGuideView = (WebView) findViewById(R.id.webview);
        userGuideView.clearCache(true);
        userGuideView.loadUrl("file:///android_asset/RecordArcheryUserGuide.html");
    }
}