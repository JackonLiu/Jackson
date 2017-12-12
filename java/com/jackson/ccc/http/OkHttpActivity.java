package com.jackson.ccc.http;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.jackson.ccc.test.R;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by LXP on 17-4-22.
 */

public class OkHttpActivity extends AppCompatActivity implements View.OnClickListener {
    private Button syncGet;
    private Button asyncget;
    private Button post;
    private Button fileDownload, fengzhuang;
    private TextView tvtext;
    private String result;
    String uriAPI = "http://rz.ccsu.cn/authserver/login?service=http%3A%2F%2Fentry.ccsu.cn%2F";


    private static OkHttpClient client = new OkHttpClient();


    /**
     * 在这里直接设置连接超时，静态方法内，在构造方法被调用前就已经初始话了
     */
    static {
        client.newBuilder().connectTimeout(10, TimeUnit.SECONDS);
        client.newBuilder().readTimeout(10, TimeUnit.SECONDS);
        client.newBuilder().writeTimeout(10, TimeUnit.SECONDS);
    }

    private Request request;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mtamain);
        initialize();
        initListener();

    }

    /**
     * 事件监听
     */
    private void initListener() {
        syncGet.setOnClickListener(this);
        asyncget.setOnClickListener(this);
        post.setOnClickListener(this);
        fileDownload.setOnClickListener(this);
        fengzhuang.setOnClickListener(this);
    }


    /**
     * 初始化布局控件
     */
    private void initialize() {

        syncGet = (Button) findViewById(R.id.syncGet);
        asyncget = (Button) findViewById(R.id.asyncget);
        post = (Button) findViewById(R.id.post);
        tvtext = (TextView) findViewById(R.id.tv_mt);
        fileDownload = (Button) findViewById(R.id.fileDownload);
        fengzhuang = (Button) findViewById(R.id.fengzhuang);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.syncGet:
                initSyncData();
                break;
            case R.id.asyncget:
                initAsyncGet();
                break;
            case R.id.post:
                initPost();
                break;
            case R.id.fileDownload:
                downLoadFile();
                break;
            case R.id.fengzhuang:
                break;
        }
    }


    /**
     * get请求同步方法
     */
    private void initSyncData() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {//Contants.SYNC_URL
                    request = new Request.Builder().url(uriAPI).build();
                    Response response = client.newCall(request).execute();
                    result = response.body().string();
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            tvtext.setText(result);
                            Log.e("222", "AddLost");
                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();

    }

    /**
     * 异步请求
     */
    private void initAsyncGet() {
        new Thread(new Runnable() {
            @Override
            public void run() {//Contants.ASYNC_URL
                request = new Request.Builder().url(uriAPI).build();
                client.newCall(request).enqueue(new Callback() {
                    /**
                     *
                     * A call is a request that has been prepared for execution. A call can be canceled. As this object
                     * represents a single request/response pair (stream), it cannot be executed twice.
                     * @param call   是一个接口，  是一个准备好的可以执行的request
                     *               可以取消，对位一个请求对象，只能单个请求
                     */

                    @Override
                    public void onFailure(Call call, IOException e) {
                        Log.e("222", "AddLost  请求失败");
                    }

                    /**
                     *
                     * @param call
                     * @param response   是一个响应请求
                     * @throws IOException
                     */
                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        /**
                         * 通过拿到response这个响应请求，然后通过body().string(),拿到请求到的数据
                         * 这里最好用string()  而不要用toString（）
                         * toString（）每个类都有的，是把对象转换为字符串
                         * string（）是把流转为字符串
                         */
                        result = response.body().string();
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                tvtext.setText(result);
                            }
                        });
                    }
                });
            }
        }).start();
    }

    /**
     * 表单提交
     */
    private void initPost() {

        String url = "http://112.124.22.238:8081/course_api/banner/query";


        FormBody formBody = new FormBody.Builder()
                .add("type", "1")
                .build();
        request = new Request.Builder().url(url)
                .post(formBody).build();
        new Thread(new Runnable() {
            @Override
            public void run() {
                client.newCall(request).enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {

                    }

                    @Override
                    public void onResponse(Call call, final Response response) throws IOException {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                tvtext.setText("提交成功");
                            }
                        });
                    }
                });
            }
        }).start();

    }

    /**
     * 文件下载地址
     */
    private void downLoadFile() {
        String url = "http://www.0551fangchan.com/images/keupload/20120917171535_49309.jpg";
        request = new Request.Builder().url(url).build();
        OkHttpClient client = new OkHttpClient();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

                //把请求成功的response转为字节流
                InputStream inputStream = response.body().byteStream();

                /**
                 * 在这里要加上权限   在mainfests文件中
                 * <uses-permission android:name="android.permission.INTERNET"/>
                 * <uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE"/>
                 */

                //在这里用到了文件输出流
                FileOutputStream fileOutputStream = new FileOutputStream(new File("/sdcard/logo.jpg"));
                //定义一个字节数组
                byte[] buffer = new byte[2048];
                int len = 0;
                while ((len = inputStream.read(buffer)) != -1) {
                    //写出到文件
                    fileOutputStream.write(buffer, 0, len);
                }
                //关闭输出流
                fileOutputStream.flush();
                Log.e("222", "文件下载成功...");
            }
        });
    }
}
