package com.jackson.ccc.gridview;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.jackson.ccc.listviewfrashdemo1.ApkEntity;
import com.jackson.ccc.test.MainActivity;
import com.jackson.ccc.test.R;

import net.sf.json.JSON;

import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import java.io.IOException;
import java.net.URLDecoder;
import java.util.ArrayList;

import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


/**
 * Created by LXP on 17-4-8.
 */

@EActivity(R.layout.add)
public class AddActivities extends Activity {
    ImageButton addImage, back;
    EditText rich;
    TextView inform_tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.add);

        addImage = (ImageButton) findViewById(R.id.addImage);
        back = (ImageButton) findViewById(R.id.back_ib);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddActivities.this.finish();
            }
        });
        //listview = (ReFlashListView) findViewById(R.id.activity_list);
        addImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*TackPicture tack=new TackPicture();
                Bitmap bitmap = tack.getBitmapFromSharedPreferences();
                addImage.setImageBitmap(bitmap);*/
                Log.e("222", "2addImage.setOnClickListener2");
            }
        });
        inform_tv = (TextView) findViewById(R.id.inform_tv);
        inform_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /**
                 * @desc:post json数据提交   Header+params+json
                 */
                //sendHeadersAndJSON();
                AddActivities.this.finish();
            }
        });



    }

    public static void sendHeadersAndJSON(){

        // 表单提交 这种能满足大部分的需求
        RequestBody formBody = new FormBody.Builder()
                .add("jsonData", "{\"data\":\"121\",\"data1\":\"2232\"}")
                .add("username", "Arison+中文").add("password", "1111111")
                .build();

        String postBody = "{\"type\":\"post json提交\"}";
        String postBody2 = "{\"type2\":\"post json提交\"}";
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url("http://localhost:8080/spring-mvc-showcase/api/getHeaders")
                .header("cookie", "JSESSIONID=EB36DE5E50E342D86C55DAE0CDDD4F6D")
                .addHeader("content-type", "application/json;charset:utf-8")
                .addHeader("Home", "china")// 自定义的header
                .addHeader("user-agent", "android")
                // .post(RequestBody.create(MEDIA_TYPE_TEXT, postBody))
                .post(formBody)
                // 表单提交
                .put(RequestBody.create(
                        MediaType.parse("application/json; charset=utf-8"),
                        postBody))// post json提交
                .put(RequestBody.create(
                        MediaType.parse("application/json; charset=utf-8"),
                        postBody2))// post json提交
                .build();
        try {
            Response response = client.newCall(request).execute();
            if (response.isSuccessful()) {
                String json = response.body().string();
                Log.e("222",json);
                            /*System.out.println(json);
                            String post = parseObject(json).getString("postBody");
                            System.out.println("转义之前：" + post);
                            System.out.println("转义之后：" + URLDecoder.decode(post));*/
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
