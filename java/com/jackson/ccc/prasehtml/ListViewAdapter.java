package com.jackson.ccc.prasehtml;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.jackson.ccc.gridview.Data;
import com.jackson.ccc.test.R;

import java.util.LinkedList;
import java.util.List;

public class ListViewAdapter extends BaseAdapter {
    private Context mContext;
    private LayoutInflater mInflater;
    private List<TitleBean> mList;


    public ListViewAdapter(Context context, List<TitleBean> list) {
        this.mContext = context;
        mInflater = LayoutInflater.from(context);
        this.mList = list;
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Object getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.activity_listviw_item, null);
            holder = new ViewHolder();
            holder.img_icon = (ImageView) convertView.findViewById(R.id.img_icon);
            holder.txt_content = (TextView) convertView.findViewById(R.id.txt_content);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.img_icon.setImageResource(R.mipmap.activity);
        //holder.txt_content.setText("222");
        TitleBean bean = mList.get(position);
        holder.txt_content.setText(bean.getTitle());
        Log.e("222", "txt_content.setText");
        holder.txt_content.setOnClickListener(new ItemClickListener(bean.getUrl()));
        return convertView;
    }

    private class ItemClickListener implements View.OnClickListener {
        private String url;

        public ItemClickListener(String url) {
            this.url = url;
        }

        @Override
        public void onClick(View v) {
            Intent toDetail = new Intent(mContext, DetailActivity.class);
            toDetail.putExtra("detailUrl", url);
            mContext.startActivity(toDetail);
        }
    }

    public static class ViewHolder {
        ImageView img_icon;
        public TextView txt_content;
    }

   /* public void add(Data data) {
        if (mData == null) {
            Log.e("222", " public void add(Data data)");
            mData = new LinkedList<Data>();
        }
        mData.add(data);
        Log.e("222", "mData.add(data);" + mData);

        notifyDataSetChanged();
    }

    //往特定位置，添加一个元素
    public void add(int position, Data data) {
        if (mData == null) {
            mData = new LinkedList<Data>();
        }
        mData.add(position, data);
        notifyDataSetChanged();
    }

    //mAdapter.remove(mData_5);
    public void remove(Data data) {
        if (mData != null) {
            mData.remove(data);
        }
        notifyDataSetChanged();
    }

    //mAdapter.remove(2);
    public void remove(int position) {
        if (mData != null) {
            mData.remove(position);
        }
        notifyDataSetChanged();
    }

    //清除所有数据
    public void clear() {
        if (mData != null) {
            mData.clear();
        }
        notifyDataSetChanged();
    }*/
}
