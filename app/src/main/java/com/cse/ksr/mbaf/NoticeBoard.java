package com.cse.ksr.mbaf;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebView;
import android.webkit.WebViewClient;

/**
 * Created by G.KARUN on 4/7/2017.
 */
public class NoticeBoard extends AppCompatActivity {
    WebView noticeBoard;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.noticeboard);
        noticeBoard=(WebView)findViewById(R.id.noticeboard);
        noticeBoard.getSettings().setLoadWithOverviewMode(true);
        Bundle bundle=getIntent().getExtras();
        String url=bundle.getString("url");
        //noticeBoard.getSettings().setUserAgentString(DESKTOP_USERAGENT);
        noticeBoard.getSettings().setUseWideViewPort(true);
        noticeBoard.getSettings().setSupportZoom(true);
        noticeBoard.getSettings().setDisplayZoomControls(false);
        noticeBoard.getSettings().setBuiltInZoomControls(true);
        noticeBoard.setWebViewClient(new WebViewClient());
        noticeBoard.getSettings().setJavaScriptEnabled(true);
        noticeBoard.loadUrl(url);

    }
}
