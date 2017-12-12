package com.jackson.ccc.gridview;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.jackson.ccc.listviewfrashdemo1.ApkEntity;
import com.jackson.ccc.listviewfrashdemo1.ReFlashAdapter;
import com.jackson.ccc.listviewfrashdemo1.ReFlashListView;
import com.jackson.ccc.test.R;

import java.util.ArrayList;


/**
 * Created by LXP on 17-3-25.
 */

public class VpSimpleFragment extends Fragment implements ReFlashListView.IReflashListener{
    private String mTitle;
    public static final String BUNDLE_TITLE = "title";
    ArrayList<ApkEntity> apk_list;
    ReFlashAdapter adapter;
    ReFlashListView listview;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.agora_fragment, container, false);
        Bundle bundle = new Bundle();
        if(bundle!=null){
            mTitle=bundle.getString(BUNDLE_TITLE);
        }

        TextView tv;
        tv=(TextView)view.findViewById(R.id.agora_title);
        tv.setText(mTitle);
        tv.setGravity(Gravity.CENTER);

        listview = (ReFlashListView)view.findViewById(R.id.agora_list);
        setData();
        showList(apk_list);
        return view;
    }

    public static VpSimpleFragment newInstance(String title) {
        Bundle bundle = new Bundle();
        bundle.putString(BUNDLE_TITLE,title);
        VpSimpleFragment fragment=new VpSimpleFragment();
        fragment.setArguments(bundle);
        return fragment;

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

    public void setData() {
        apk_list = new ArrayList<ApkEntity>();
        for (int i = 0; i < 10; i++) {
            ApkEntity entity = new ApkEntity();
            entity.setName("默认数据");
            entity.setDes("这是一个神奇的应用");
            entity.setInfo("50w用户");
            apk_list.add(entity);
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


}
