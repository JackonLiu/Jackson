package com.jackson.ccc.gridview;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;

import com.jackson.ccc.http.OkHttpActivity;
import com.jackson.ccc.test.R;
import com.jackson.ccc.util.MyAdapter;
import com.jackson.ccc.util.MyOpenHelper;
import com.jackson.ccc.util.ViewHolder;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import static com.jackson.ccc.test.R.*;

/**
 * Created by LXP on 17-3-4.
 */

public class News extends Activity {
    private ListView newsList;
    public static String URL = "http://www.imooc.com/api/teacher?type=4&num=30";
    private News mContext;
    private ImageView image;
    private MyOpenHelper myOpenHelper;
    SQLiteDatabase sqLiteDatabase1;
    private ImageButton newsIB;
    MyAdapter<ThreeStrBean> mAdapter;
    List<ThreeStrBean> threeStrBeanList;
    ThreeStrBean threeStrBean;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.news_layout);
        newsList = (ListView) findViewById(R.id.news_list);

        newsIB = (ImageButton) findViewById(R.id.newsIB);
        newsIB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(News.this, AddNews.class);
                startActivity(intent);
            }
        });

        new NewsAsyncTask().execute(URL);

        // myOpenHelper = new MyOpenHelper(mContext);

        //sqLiteDatabase1 = myOpenHelper.getReadableDatabase();

       // Log.e("222", "myOpenHelper.getReadableDatabase();");
        //sqLiteDatabase1.execSQL("insert into school(_id,category,info) values('2','校园通知','清明节放假啦')")

    }

    //将URl对应的json格式数据转化成我们需要的ThreeStrBean对象
    public List<ThreeStrBean> getJsonData(String url) {
        threeStrBeanList = new ArrayList<>();
        Log.e("222", "threeStrBeanList = new ArrayList<>();");
        String jsonString = null;
        try {
            jsonString = readStream(new URL(url).openStream());
            JSONObject object;

            object = new JSONObject(jsonString);
            JSONArray jsonArray = object.getJSONArray("data");
            for (int i = 0; i < jsonArray.length(); i++) {
                object = jsonArray.getJSONObject(i);
                threeStrBean = new ThreeStrBean();
                threeStrBean.setNewsIconUrl(object.getString("picSmall"));
                threeStrBean.setNewsTitle(object.getString("name"));
                threeStrBean.setNewsContent(object.getString("description"));
                Log.e("222", threeStrBean.getNewsTitle());
                Log.e("222", threeStrBean.getNewsContent());
                Log.e("222", threeStrBean.newsIconUrl);
                threeStrBeanList.add(threeStrBean);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return threeStrBeanList;
    }

    //通过input stream解析网络返回的数据
    private String readStream(InputStream is) {
        InputStreamReader isr;
        String result = "";
        try {
            String line = "";
            isr = new InputStreamReader(is, "utf-8");
            Log.e("222", "isr = new InputStreamReader(is, \"utf-8\");");

            BufferedReader br = new BufferedReader(isr);
            while ((line = br.readLine()) != null) {
                result += line;
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return result;
    }

    //实现网络的异步访问
    class NewsAsyncTask extends AsyncTask<String, Void, List<ThreeStrBean>> {

        @Override
        protected List<ThreeStrBean> doInBackground(String... params) {
            return getJsonData(params[0]);
        }

        @Override
        protected void onPostExecute(List<ThreeStrBean> threeStrBeen) {
            super.onPostExecute(threeStrBeen);
            newsList.setAdapter(mAdapter = new MyAdapter<ThreeStrBean>(
                    mContext, threeStrBeanList, R.layout.item_layout) {
                @Override
                public void convert(ViewHolder helper, ThreeStrBean threeStrBeen) {
                    Log.e("222","common on");
                    helper.setImageResource(R.id.img_icon, mipmap.news);
                    helper.setText(R.id.tv_title, threeStrBean.getNewsTitle());
                    Log.e("222",threeStrBean.getNewsTitle()+"222");
                    helper.setText(R.id.tv_text, threeStrBean.getNewsContent());
                    Log.e("222",threeStrBean.getNewsContent());
                }
            });

        }
    }

    /**
     //调用增加第一条资讯
     public void Add(SQLiteDatabase db) {
     //获取数据库对象
     //db.execSQL("insert into school(category,info) value(?,?)",new Object[]{"校园","放假通知"});


     //ContentValues 内部封装了一个 map key：对应的类别 value对应的值

     ContentValues values = new ContentValues();
     values.put("_id", "4");
     values.put("category", "校园");
     values.put("info", "明天放假");

     //返回值代表插入新行的id,底层自动生成sql语句
     long insert = db.insert("school", null, values);
     Log.e("222", "values.put(info,明天放假);");

     db.close();
     if (insert > 0) {
     Toast.makeText(News.this, "插入新行的id为" + insert, Toast.LENGTH_SHORT).show();
     } else {
     Toast.makeText(News.this, "添加失败", Toast.LENGTH_SHORT).show();
     }
     }

     //删除
     public void delete(View v) {
     SQLiteDatabase db = myOpenHelper.getReadableDatabase();
     db.execSQL("delete from school where category=?", new Object[]{"校园"});

     // int delete = db.delete("school","category=?",new String[]{"校园"});
     db.close();
     Toast.makeText(getApplicationContext(), "删除成功", Toast.LENGTH_LONG).show();

     }

     //更新
     public void update(View v) {
     SQLiteDatabase db = myOpenHelper.getReadableDatabase();
     // db.execSQL("update school set category=? where info=?",new Object[]{"校园"});

     ContentValues values = new ContentValues();
     values.put("category", "社团");
     int update = db.update("school", values, "category=?", new String[]{"校园"});
     db.close();
     Toast.makeText(getApplicationContext(), "修改成功", Toast.LENGTH_LONG).show();

     }

     //查找
     public void query(SQLiteDatabase db) {
     //columns  代表你要查询的列
     //selection 根据什么查询
     Cursor cursor = db.rawQuery("select * from school", null);
     // Cursor cursor = db.query("school", new String[]{"category"}, "category=?", new String[]{"校园"}, null, null, null);

     if (cursor != null && cursor.getCount() > 0) {
     while (cursor.moveToNext()) {
     //columnIndex代表列的索引
     String category = cursor.getString(0);
     Log.e("222", "查询成功" + category);
     }
     }
     }


     * Cursor cursor = db.rawQuery("select * from school",null);
     * if(cursor!=null&&cursor.getCount()>0){
     * while(cursor.moveToNext()){
     * String info = cursor.getString(1);
     * String category = cursor.getString(2);
     * }
     * }
     */

}
