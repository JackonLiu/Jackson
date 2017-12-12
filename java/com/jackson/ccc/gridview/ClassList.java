package com.jackson.ccc.gridview;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.http.HttpResponseCache;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.jackson.ccc.http.ResourceInAssetHandler;
import com.jackson.ccc.http.SimpleHttpServer;
import com.jackson.ccc.http.UploadImaHandler;
import com.jackson.ccc.http.WebConfigration;
import com.jackson.ccc.prasehtml.PraseHtmlActivity;
import com.jackson.ccc.prasehtml.TitleBean;
import com.jackson.ccc.test.MainActivity;
import com.jackson.ccc.test.R;
import com.jackson.ccc.util.HttpThread;

import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.Extra;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.rest.Get;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpCookie;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by LXP on 17-3-4.
 */

@EActivity(R.layout.grade_layout)
public class ClassList extends Activity {
    @Extra
    String mTitle;
    private ClassList context;
    private Handler handler = new Handler();
    private EditText urlText;  //声明作为地址栏的EditText对象
    Button select,forward,back;
    WebView webView;
    private List<HttpCookie> cookies;
    private HttpResponseCache cache;
    String week;
    private EditText id,password;
    Handler mHandler = new Handler();
    OkHttpClient okHttpClient = new OkHttpClient();
    String uriAPI = "218.196.42.2/weiccsu/schedule/oqv6VtxBgqrqtuhINRvZq7KHQKUM";
    private SimpleHttpServer shs;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.grade_layout);
        select = (Button)findViewById(R.id.find);
        id = (EditText)findViewById(R.id.editNumber);
        password=(EditText)findViewById(R.id.password);

        select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                select();
                Log.e("222",v.toString());
            }
        });

        /*WebConfigration wc = new WebConfigration();
        wc.setPort(8088);
        wc.setMaxParallets(50);
        shs = new SimpleHttpServer(wc);
        shs.registerSourceHandler(new ResourceInAssetHandler(context));
        //shs.registerSourceHandler(new UploadImaHandler(){});
        shs.startAsync();*/
    }


    /*urlText = (EditText) findViewById(R.id.urlText);

        forward = (Button) findViewById(R.id.forward);
        back = (Button) findViewById(R.id.back);*/


       /* webView = (WebView) findViewById(R.id.classWeb);
        webView.getSettings().setJavaScriptEnabled(true);
        //处理javascript对话框
        //webView.setWebChromeClient(new WebChromeClient());
        //处理各种请求和通知事件，不使用就会用内置浏览器访问
       // webView.setWebViewClient(new WebViewClient());
        //让其具有放大和缩小网页的功能
        webView.getSettings().setSupportZoom(true);
        webView.getSettings().setBuiltInZoomControls(true);
        new HttpThread("http://218.196.42.2/weiccsu/schedule/oqv6VtxBgqrqtuhINRvZq7KHQKUM", webView, handler).start();*/

/*
    @Click
    void search_go_btn(){
        Intent toDetail = new Intent(ClassList.this, PraseHtmlActivity.class);
        startActivity(toDetail);
    }*/
    public void select(){

        final Request request = new Request.Builder().url(uriAPI).get().build();

        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.e("222","onFailure" );
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String resp = response.body().string();
                Log.e("222",resp);
                Document parse = Jsoup.parse(resp);
                Log.e("222",parse.toString());
                Elements select = parse.select("tr");
                Log.e("222",select.toString());
                Element element = select.get(1);
                week = element.attr("td");
                Log.e("222",week);
            }
        });


       /* FormBody formBody = new FormBody.Builder()
                .add("captcha_type", "cn")
                .add("_xsrf", xsrf)
                .add("password", "liuxinpeng222")
                .add("remember_me", "true")
                .add("email", "13574246766")
                .build();
        Request request1 = new Request.Builder().post(formBody).url("https://www.zhihu.com/#signin").build();
        okHttpClient.newCall(request1).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                    Log.e("222",call.toString()+ "onFailure");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Log.e("222", "google_lenve_fb  onResponse: " + response.body().string());
            }
        });*/
    }


   /* public String getCookie(String username, String password, String loginAction) throws Exception {
        //登录
        URL url = new URL(loginAction);
        String param = "username=" + username + "&password=" + password;
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setDoInput(true);
        conn.setDoOutput(true);
        conn.setRequestMethod("POST");
        OutputStream out = conn.getOutputStream();
        out.write(param.getBytes());
        out.flush();
        out.close();
        String sessionId = "";
        String cookieVal = "";
        String key = null;
        //取cookie
        for (int i = 1; (key = conn.getHeaderFieldKey(i)) != null; i++) {
            if (key.equalsIgnoreCase("set-cookie")) {
                cookieVal = conn.getHeaderField(i);
                cookieVal = cookieVal.substring(0, cookieVal.indexOf(";"));
                sessionId = sessionId + cookieVal + ";";
            }
        }
        return sessionId;
    }
*/


    public void back(View view){
        //ClassList.this.finish();
        select();
    }

    public void doGet(View view) throws IOException {

           /* @UiThread
            void run() {
                webView.getSettings().setLoadWithOverviewMode(true);
                webView.getSettings().setUseWideViewPort(true);
                webView.loadUrl(uriAPI);
                Log.e("222", "在此可更新主线程");
            }
        });*/

    }}



