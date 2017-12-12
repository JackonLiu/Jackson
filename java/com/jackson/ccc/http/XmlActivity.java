package com.jackson.ccc.http;

import android.app.Activity;
import android.os.Handler;
import android.os.Bundle;
import android.widget.TextView;

import com.jackson.ccc.test.R;

public class XmlActivity extends Activity {

    private TextView textView;
    Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add);
        //MutiDownload mutiDownload = new MutiDownload();
        //mutiDownload.main(new String[]{"22","221"});
        String url = "localhost:8080/web/girl.xml";
        new XmlThread(url,textView,handler).start();

    }


}
