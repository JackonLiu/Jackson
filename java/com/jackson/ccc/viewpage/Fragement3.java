package com.jackson.ccc.viewpage;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.jackson.ccc.listviewfrashdemo1.ApkEntity;
import com.jackson.ccc.listviewfrashdemo1.ReFlashAdapter;
import com.jackson.ccc.listviewfrashdemo1.ReFlashListView;
import com.jackson.ccc.test.R;

import java.util.ArrayList;

/**
 * 消息碎片，viewpage中的fragment
 */

public class Fragement3 extends Fragment implements ReFlashListView.IReflashListener{
    ArrayList<ApkEntity> apk_list;
    ReFlashAdapter adapter;
    ReFlashListView listview;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.messengers, container, false);

        listview = (ReFlashListView)view.findViewById(R.id.msg_list);
        setData();
        showList(apk_list);
        return view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
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


    public void showList(ArrayList<ApkEntity> apk_list) {
        if (adapter == null) {
            listview.setInterface(this);
            adapter = new ReFlashAdapter(getActivity(), apk_list);
            listview.setAdapter(adapter);
        } else {
            adapter.onDateChange(apk_list);
        }
    }

    public void setReflashData() {
        for (int i = 0; i < 2; i++) {
            ApkEntity entity = new ApkEntity();
            entity.setName("刷新数据");
            entity.setDes("这是一个神奇的应用");
            entity.setInfo("50w用户");
            apk_list.add(0,entity);
        }
    }

    public void setData() {
        apk_list = new ArrayList<ApkEntity>();
        for (int i = 0; i < 6; i++) {
            ApkEntity entity = new ApkEntity();
            entity.setName("默认数据");
            entity.setDes("这是一个神奇的应用");
            entity.setInfo("50w用户");
            apk_list.add(entity);
        }
    }
}
