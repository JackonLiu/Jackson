package com.jackson.ccc.util;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jackson.ccc.test.R;

import java.util.List;

/**
 * Created by LXP on 17-4-22.
 */

public class MyAdapter<T> extends CommonAdapter<T> {


    public MyAdapter(Context context, List<T> mDatas, int itemLayoutId) {
        super(context, mDatas, itemLayoutId);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        ViewHolder viewHolder = ViewHolder.get(mContext, convertView, parent,
                R.layout.item_layout, position);
        TextView mTitle = viewHolder.getView(R.id.tv_title);
        mTitle.setText(String.valueOf(mDatas.get(position)));
        return viewHolder.getmConvertView();
    }

    @Override
    public void convert(ViewHolder helper, T item) {

    }
}
