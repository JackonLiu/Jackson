package com.jackson.ccc.gridview;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Window;
import android.widget.ListView;

import com.jackson.ccc.test.R;
import com.jackson.ccc.util.MyAdapter;
import com.jackson.ccc.util.ViewHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by LXP on 17-3-16.
 */

public class GradeList extends Activity {

    private ListView grade_list;
    private Handler handler = new Handler();
    private static String URL = "http://www.imooc.com/api/teacher?type=4&num=30";
    MyAdapter<ThreeStrBean> mAdapter;
    private List<String> curList = new ArrayList<>();
    //Topbar topbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.myxml);
        /*topbar = (Topbar)findViewById(R.id.topbar);
        topbar = new Topbar(GradeList.this);*/

        grade_list = (ListView) findViewById(R.id.grade_list);
        grade_list.setAdapter(mAdapter = new MyAdapter<ThreeStrBean>(
                getApplicationContext(), Grade.GradeList, R.layout.item_layout) {
            @Override
            public void convert(ViewHolder helper, ThreeStrBean item) {
                Log.e("222",item.toString());
               // helper.setImageResource(R.id.img_icon, R.mipmap.news);
                helper.setText(R.id.tv_title, item.getNewsTitle());
                Log.e("222",item.getNewsTitle());
                helper.setText(R.id.tv_text, item.getNewsContent());
            }
        });

    }




    }
