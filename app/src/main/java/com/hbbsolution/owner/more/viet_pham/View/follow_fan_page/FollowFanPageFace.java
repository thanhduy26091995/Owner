package com.hbbsolution.owner.more.viet_pham.View.follow_fan_page;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebView;

import com.hbbsolution.owner.R;

/**
 * Created by Administrator on 6/26/2017.
 */

public class FollowFanPageFace extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_like_page_face);
        WebView webview;
        webview = (WebView) findViewById(R.id.webview);
        webview.getSettings().setJavaScriptEnabled(true);
        webview.loadUrl("https://www.facebook.com/Ng%C6%B0%E1%BB%9Di-Gi%C3%BAp-Vi%E1%BB%87c-247-122998571630965/");
    }
}
