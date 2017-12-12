package com.jackson.ccc.util;


import android.net.http.HttpResponseCache;
import android.os.Handler;
import android.webkit.WebView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpCookie;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

/**
 * Created by LXP on 17-3-16.
 */

public class HttpThread extends Thread {

    private String url;
    private WebView webView;
    private Handler handler;

    public HttpThread(String url, WebView webView, Handler handler) {
        this.url = url;
        this.webView = webView;
        this.handler = handler;
    }

    @Override
    public void run() {
        try {
            URL httpUrl = new URL(url);
            HttpURLConnection connection = (HttpURLConnection) httpUrl.openConnection();
            connection.setReadTimeout(5000);
            connection.setRequestMethod("GET");

            final StringBuffer stringBuffer = new StringBuffer();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String str;
            while ((str = bufferedReader.readLine()) != null) {
                stringBuffer.append(str);
            }

            handler.post(new Runnable() {
                @Override
                public void run() {
                    webView.loadDataWithBaseURL(null,stringBuffer.toString(),"text/html","utf-8",null);
                }
            });
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
