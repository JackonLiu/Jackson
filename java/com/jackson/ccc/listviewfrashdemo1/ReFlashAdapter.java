package com.jackson.ccc.listviewfrashdemo1;

import java.util.ArrayList;


import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.jackson.ccc.test.R;

public class ReFlashAdapter extends BaseAdapter {
	ArrayList<ApkEntity> apk_list;
	LayoutInflater inflater;

	public ReFlashAdapter(Context context, ArrayList<ApkEntity> apk_list) {
		this.apk_list = apk_list;
		this.inflater = LayoutInflater.from(context);
	}

	public void onDateChange(ArrayList<ApkEntity> apk_list) {
		this.apk_list = apk_list;
		Log.e("222","onDateChange");
		this.notifyDataSetChanged();
	}

	@Override
	public int getCount() {
		return apk_list.size();
	}

	@Override
	public Object getItem(int position) {
		return apk_list.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ApkEntity entity = apk_list.get(position);
		ViewHolder holder;
		if (convertView == null) {
			holder = new ViewHolder();
			convertView = inflater.inflate(R.layout.item_frash, null);
			holder.name_tv = (TextView) convertView
					.findViewById(R.id.item3_apkname);
			holder.des_tv = (TextView) convertView
					.findViewById(R.id.item3_apkdes);
			holder.info_tv = (TextView) convertView
					.findViewById(R.id.item3_apkinfo);
			convertView.setTag(holder);
		}else{
			holder = (ViewHolder) convertView.getTag();
		}
		holder.name_tv.setText(entity.getName());
		holder.des_tv.setText(entity.getDes());
		holder.info_tv.setText(entity.getInfo());
		return convertView;
	}

	class ViewHolder {
		TextView name_tv;
		TextView des_tv;
		TextView info_tv;
	}
}
