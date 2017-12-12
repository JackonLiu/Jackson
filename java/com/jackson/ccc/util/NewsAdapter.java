package com.jackson.ccc.util;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.jackson.ccc.gridview.ThreeStrBean;
import com.jackson.ccc.test.R;
import com.jackson.ccc.upload.ImageUtil;

import java.util.List;


public class NewsAdapter extends BaseAdapter  implements AbsListView.OnScrollListener{


    private Context mContext;

    private LayoutInflater inflater;
    private ImageUtil imageUtil;
    private List<ThreeStrBean> mList;
    private  int mStart,mEnd;
    public static String[] urls;

    public NewsAdapter() {
    }

    public NewsAdapter(List<ThreeStrBean> mList, Context mContext) {
        this.mList = mList;
        inflater = LayoutInflater.from(mContext);
       // imageUtil = new ImageUtil();
        /*for (int i= 0,length=mList.size();i<length;i++){
            urls[i] = mList.get(i).newsIconUrl;
            Log.e("222",mList.get(i).newsIconUrl);
        }*/
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
        ViewHolder holder = null;
        if (null == convertView) {Log.e("222", "convertView == null");
            convertView = inflater.inflate(R.layout.item_layout, null);
            Log.e("222", "inflater =LayoutInflater.from(mContext);");
            //holder.img_icon = (ImageView) convertView.findViewById(R.id.img_icon);
            holder.tvTitle=(TextView) convertView.findViewById(R.id.tv_title);
            holder.tvContent = (TextView) convertView.findViewById(R.id.tv_text);
            convertView.setTag(holder);

        }else {
            holder = (ViewHolder) convertView.getTag();
        }
       // holder.img_icon.setImageResource(R.mipmap.activity);
        //holder.img_icon.setTag(mList.get(position).newsIconUrl);
       // imageUtil.showImageByAsynTask(holder.img_icon,mList.get(position).newsIconUrl);
        holder.tvTitle.setText(mList.get(position).newsTitle);
        holder.tvContent.setText(mList.get(position).newsContent);

        Log.e("222", mList.get(position).newsTitle + mList.get(position).newsContent );

        return convertView;
    }


    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
        if(scrollState == SCROLL_STATE_IDLE){
            imageUtil.loadImages(mStart,mEnd);
        }else {
            imageUtil.cancelAllTask();
        }

    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        mStart = firstVisibleItem;
        mEnd = firstVisibleItem + visibleItemCount;
        boolean mFirstIn=true;
        //第一次显示时调用
        if(mFirstIn && visibleItemCount>0){
            imageUtil.loadImages(mStart,mEnd);
            mFirstIn = false;
        }
    }

    class ViewHolder{
        ImageView img_icon;
        TextView tvTitle,tvContent;
    }

  /*  public void add(Data data) {
        if (mList == null) {
            Log.e("222"," public void add(Data data)");
            mList = new LinkedList<ThreeStrBean>();
        }
        mList.add(data);
        Log.e("222","mData.add(data);" +mData);

        notifyDataSetChanged();
    }

    //往特定位置，添加一个元素
    public void add(int position,Data data){
        if (mData == null) {
            mData = new LinkedList<Data>();
        }
        mData.add(position,data);
        notifyDataSetChanged();
    }

    //mAdapter.remove(mData_5);
    public void remove(Data data) {
        if(mData != null) {
            mData.remove(data);
        }
        notifyDataSetChanged();
    }

    //mAdapter.remove(2);
    public void remove(int position) {
        if(mData != null) {
            mData.remove(position);
        }
        notifyDataSetChanged();
    }

    //清除所有数据
    public void clear() {
        if(mData != null) {
            mData.clear();
        }
        notifyDataSetChanged();
    }*/
}
