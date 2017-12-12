package com.jackson.ccc.test;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.SimpleAdapter;
import android.widget.ViewFlipper;

import com.jackson.ccc.gridview.Agora;
import com.jackson.ccc.gridview.Agora_;
import com.jackson.ccc.gridview.ClassList;
import com.jackson.ccc.gridview.ClassList_;
import com.jackson.ccc.gridview.Lost;
import com.jackson.ccc.gridview.Activities;
import com.jackson.ccc.gridview.Grade;
import com.jackson.ccc.gridview.Lost_;
import com.jackson.ccc.gridview.News;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by LXP on 17-3-12.
 */

public class Fragements extends Fragment implements AdapterView.OnItemClickListener {

    private SimpleAdapter adapter;
    private GridView gridView;
    private Intent intent;
    private List<Map<String,Object>> dataList;
    private int[]icon={R.mipmap.activity, R.mipmap.news, R.mipmap.grade,
            R.mipmap.classlist,R.mipmap. agora, R.mipmap.lost};
    private String[]iconName={"活动","资讯","成绩","课表","集市","失物招领"};

    private ViewFlipper viewFlipper;
    private int[]resId = {R.mipmap.hanxu,R.mipmap.gym,R.mipmap.hu};

    //Mylistener mylistener;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragments, container, false);

        this.gridView=(GridView) view.findViewById(R.id.grid);
        this.viewFlipper = (ViewFlipper) view.findViewById(R.id.flipper);

        atuo(viewFlipper);//自动播放图片页面
        initGridView();
        return view;
        //String text = format("%s", getArguments().get("name"));
        //TextView tv =(TextView)view.findViewById(R.id.aboutusText);
        //tv.setText(text);
        //mylistener.thank(code);
    }

    private void initGridView(){
        dataList=new ArrayList<Map<String,Object>>();
        adapter=new SimpleAdapter(getActivity(),getData(),R.layout.gridview_item,
                new String[]{"image","text"},new int[]{R.id.gridImage,R.id.gridText});
        gridView.setAdapter(adapter);
        gridView.setOnItemClickListener(this);
    }

    private  List<Map<String,Object>> getData(){
        for(int i=0,length=icon.length;i<length;i++){
            //键值对
            Map<String,Object>map=new HashMap<String,Object>();
            map.put("image",icon[i]);
            map.put("text",iconName[i]);


            dataList.add(map);
        }
        return dataList;
    }

    //gridView的事件监听器
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        switch (position) {
            case 0:
                intent = new Intent(getActivity(), Activities.class);
                startActivity(intent);
                break;
            case 1:
                intent = new Intent(getActivity(), News.class);
                startActivity(intent);
                break;
            case 2:
                intent = new Intent(getActivity(), Grade.class);
                startActivity(intent);
                break;
            case 3:
                intent = new Intent(getActivity(), ClassList_.class);
                startActivity(intent);
                break;
            case 4:
                intent = new Intent(getActivity(), Agora.class);
                startActivity(intent);
                break;
            case 5:
                intent = new Intent(getActivity(), Lost.class);
                startActivity(intent);
                break;
            default:
                break;
        }
    }


    //自动播放图片页面
    private void atuo(View source) {
        //动态导入的方法为ViewFlipper加入子View
        for(int i=0;i<resId.length;i++){
            viewFlipper.addView(getImageView(resId[i]));
        }
        viewFlipper.setInAnimation(getActivity(), android.R.anim.slide_in_left);
        viewFlipper.setOutAnimation(getActivity(), android.R.anim.slide_out_right);
        // 开始自动播放
        viewFlipper.setFlipInterval(8000);
        viewFlipper.startFlipping();
    }

    private ImageView getImageView(int resId){
        ImageView imageView = new ImageView(getActivity());
        imageView.setBackgroundResource(resId);
        return imageView;
    }


   /* public interface Mylistener{
        public void thank(String code);
    }

    @Override
    public void onAttach(Activity activity) {
        mylistener =new Mylistener() {
            @Override
            public void thank(String code) {

            }
        };
        super.onAttach(activity);
    }*/
}
