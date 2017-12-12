package com.jackson.ccc.gridview;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.Window;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageButton;

import com.jackson.ccc.test.R;
import com.jackson.ccc.util.HttpThread;

/**
 * Created by LXP on 17-3-14.
 */

public class SunService extends Activity{

    private Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.sun_service);

        WebView webView = (WebView)findViewById(R.id.sun_service);
        webView.getSettings().setJavaScriptEnabled(true);
        //处理javascript对话框
        webView.setWebChromeClient(new WebChromeClient());
        //处理各种请求和通知事件，不使用就会用内置浏览器访问
        webView.setWebViewClient(new WebViewClient());
        webView.getSettings().setSupportZoom(true);
        webView.getSettings().setBuiltInZoomControls(true);
        new HttpThread("http://218.196.40.231/web/wxindex.jsp",webView,handler).start();
    }

}
