package com.jackson.ccc.gridview;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.jackson.ccc.test.MainActivity;
import com.jackson.ccc.test.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Cookie;
import okhttp3.FormBody;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by LXP on 17-3-4.
 */

public class Grade extends Activity {

    private ListView listView;
    private EditText id,password;
    private Handler handler = new Handler();
    public static List<ThreeStrBean> GradeList;
    OkHttpClient client = new OkHttpClient();
    String uriAPI = "http://218.196.42.2/weiccsu/GradeServlet",
    login="http://218.196.42.2/weiccsu/LoginServlet";
    String cookie="";
    private Button select;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.grade_layout);
        select=(Button)findViewById(R.id.find);
        id = (EditText)findViewById(R.id.editNumber);
        password=(EditText)findViewById(R.id.password);
        /*WebView webView =(WebView)findViewById(R.id.gradeWeb);
        webView.getSettings().setJavaScriptEnabled(true);
        //处理javascript对话框
        webView.setWebChromeClient(new WebChromeClient());
        //处理各种请求和通知事件，不使用就会用内置浏览器访问
        webView.setWebViewClient(new WebViewClient());
        webView.getSettings().setSupportZoom(true);
        webView.getSettings().setBuiltInZoomControls(true);
        new HttpThread("http://218.196.42.2/weiccsu/grade.html",webView,handler).start();*/
        select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                search(v);
            }
        });

    }

    private String getCookie() {

        FormBody account = new FormBody.Builder()
                .add("username", "B20150303126")
                .add("password", "222219")
                .build();
        Request request = new Request.Builder().post(account).url(login).build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.e("222",e.getMessage()+ "onFailure");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String jsonString = null;
                try {
                    jsonString = response.body().string();
                    Log.e("222", jsonString);
                    JSONObject object = new JSONObject(jsonString);
                    cookie=object.getString("cookie");

                    Log.e("222", cookie);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });

        return cookie;
    }

    public void search(View view){

        Cookie cookie = Cookie.parse(HttpUrl.parse(login),getCookie());

        FormBody formBody = new FormBody.Builder()
                .add("cookie",cookie.value())
                .add("term","2016-2017-1")
                .add("xsrf", "zhcj")
                .build();
        Request request1 = new Request.Builder().post(formBody).url(uriAPI).build();
        //final Request requestcookie = new Request.Builder().url(login).build();
        client.newCall(request1).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.e("222",e.getMessage()+ "onFailure");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Log.e("222", "onResponse: " + response.body().string());
                GradeList = new ArrayList<>();

                String jsonString = null;
                try {
                    jsonString = response.body().string();
                    Log.e("222", jsonString);
                    JSONObject object;
                    ThreeStrBean threeStrBean;
                    object = new JSONObject(jsonString);
                    JSONArray jsonArray = object.getJSONArray("grades");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        object = jsonArray.getJSONObject(i);
                        threeStrBean = new ThreeStrBean();
                        threeStrBean.setNewsIconUrl(object.getString("score"));
                        threeStrBean.setNewsTitle(object.getString("courseName"));
                        threeStrBean.setNewsContent(object.getString("property"));
                        Log.e("222", threeStrBean.getNewsTitle());
                        Log.e("222", threeStrBean.getNewsContent());
                        Log.e("222", threeStrBean.newsIconUrl);
                        GradeList.add(threeStrBean);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                if(GradeList.get(0)!=null) {
                    Intent toDetail = new Intent(Grade.this, GradeList.class);
                    startActivity(toDetail);
                }
            }
        });
    }

    public void back(View view){
        Grade.this.finish();
    }

}
