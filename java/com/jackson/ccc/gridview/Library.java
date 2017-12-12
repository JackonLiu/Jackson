package com.jackson.ccc.gridview;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.jackson.ccc.test.R;
import com.jackson.ccc.util.HttpThread;


/**
 * Created by LXP on 17-3-25.
 */

public class Library extends Activity {
    private Handler handler = new Handler();


    public Library() {
        super();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.library);
        WebView webView = (WebView)findViewById(R.id.libWeb);
        webView.getSettings().setJavaScriptEnabled(true);
        //处理javascript对话框
        webView.setWebChromeClient(new WebChromeClient());
        //处理各种请求和通知事件，不使用就会用内置浏览器访问
        webView.setWebViewClient(new WebViewClient());
        webView.getSettings().setSupportZoom(true);
        webView.getSettings().setBuiltInZoomControls(true);
        new HttpThread("http://218.196.42.2/weiccsu/library.html",webView,handler).start();

    }
}
