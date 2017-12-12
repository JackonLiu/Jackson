package com.jackson.ccc.gridview;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;

import com.jackson.ccc.listviewfrashdemo1.ApkEntity;
import com.jackson.ccc.listviewfrashdemo1.ReFlashAdapter;
import com.jackson.ccc.listviewfrashdemo1.ReFlashListView;
import com.jackson.ccc.test.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Activities extends Activity implements ReFlashListView.IReflashListener {
	ArrayList<ApkEntity> apk_list;
	ReFlashAdapter adapter;
	ReFlashListView listview;
	private ImageButton add;
	List<ThreeStrBean> threeStrBeanList;
	ThreeStrBean threeStrBean;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activities_layout);
		setData();
		showList(apk_list);
		add = (ImageButton) findViewById(R.id.actAdd);

		add.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(Activities.this,AddActivities.class);
				startActivity(intent);
			}
		});
	}


	public void showList(ArrayList<ApkEntity> apk_list) {
		if (adapter == null) {
			listview = (ReFlashListView) findViewById(R.id.activity_list);
			listview.setInterface(this);
			adapter = new ReFlashAdapter(this, apk_list);
			listview.setAdapter(adapter);
		} else {
			adapter.onDateChange(apk_list);
		}
	}

	public void setData() {
		apk_list = new ArrayList<ApkEntity>();
		for (int i = 0; i < 6; i++) {
			ApkEntity entity = new ApkEntity();
			entity.setName("学生");
			entity.setDes("这是一个神奇的应用");
			entity.setInfo("50w用户");
			apk_list.add(entity);
		}
	}

	public void setReflashData() {
		for (int i = 0; i < 2; i++) {
			ApkEntity entity = new ApkEntity();
			threeStrBeanList=getJsonData(News.URL);
			entity.setName(threeStrBeanList.get(0).getNewsTitle());
			entity.setDes(threeStrBeanList.get(0).getNewsContent());
			entity.setInfo(threeStrBeanList.get(0).getNewsIconUrl());
			apk_list.add(0,entity);
		}
	}
	@Override
	public void onReflash() {
		Handler handler = new Handler();
		handler.postDelayed(new Runnable() {

			@Override
			public void run() {
				//获取最新数据
				setReflashData();
				//通知界面显示
				showList(apk_list);
				//通知listview 刷新数据完毕；
				listview.reflashComplete();
			}
		}, 2000);

	}

	@Override
	protected void onResume() {
		super.onResume();
		Handler handler = new Handler();
		handler.postDelayed(new Runnable() {

			@Override
			public void run() {
				//获取最新数据
				setReflashData();
				//通知界面显示
				showList(apk_list);
				//通知listview 刷新数据完毕；
				listview.reflashComplete();
			}
		}, 2000);
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

}
