package com.jackson.ccc.test;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.jackson.ccc.viewpage.DepthPageTransformer;
import com.jackson.ccc.viewpage.Fragement2;
import com.jackson.ccc.viewpage.Fragement3;
import com.jackson.ccc.viewpage.Fragement4;
import com.jackson.ccc.viewpage.MyfragmentPageAdapter;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends FragmentActivity  {

    private ViewPager mViewPager;// 用来放置界面切换
    private List<String> titleList;
    private List<Fragment>fragList;

    //在底部的四个菜单栏线性布局
    private LinearLayout LinearLayout1, LinearLayout2, LinearLayout3, LinearLayout4;
    private ImageButton myhome, circle, messenger, personal;
    private TextView my, xiaoxi, quan, shouye;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findId();
        initViewPage();//初始化滚动页面
    }


    private void findId() {

        mViewPager=(ViewPager)findViewById(R.id.Page);
        LinearLayout1 = (LinearLayout) findViewById(R.id.LinearLayout1);
        LinearLayout1.setOnTouchListener(new LayoutClickListener());

        LinearLayout2 = (LinearLayout) findViewById(R.id.LinearLayout2);
        LinearLayout2.setOnTouchListener(new LayoutClickListener());

        LinearLayout3 = (LinearLayout) findViewById(R.id.LinearLayout3);
        LinearLayout3.setOnTouchListener(new LayoutClickListener());

        LinearLayout4 = (LinearLayout) findViewById(R.id.LinearLayout4);
        LinearLayout4.setOnTouchListener(new LayoutClickListener());

        shouye = (TextView) findViewById(R.id.shouye);
        quan = (TextView) findViewById(R.id.quan);
        my = (TextView) findViewById(R.id.my);
        xiaoxi = (TextView) findViewById(R.id.xiaoxi);
        circle = (ImageButton) findViewById(R.id.circle);
        messenger = (ImageButton) findViewById(R.id.messenger);
        personal = (ImageButton) findViewById(R.id.personal);
        myhome = (ImageButton) findViewById(R.id.myhome);

    }

    private void initViewPage() {

        //通过fragment作为数据源
        fragList=new ArrayList<Fragment>();
        fragList.add(new Fragements());
        fragList.add(new Fragement2());
        fragList.add(new Fragement3());
        fragList.add(new Fragement4());

        //为Viewpage页卡设置标题
        titleList=new ArrayList<String>();
        titleList.add("第一页");
        titleList.add("第二页");
        titleList.add("第三页");
        titleList.add("第四页");

        MyfragmentPageAdapter adapter = new
                MyfragmentPageAdapter(getSupportFragmentManager(),fragList,titleList);

        mViewPager.setAdapter(adapter);
        mViewPager.setCurrentItem(0);
        mViewPager.setPageTransformer(true, new DepthPageTransformer());

        /** 加载activity传来的数据
         String text ="love";
         Bundle bundle = new Bundle();
         bundle.putString("name",text);
         fragList.get(1).setArguments(bundle);

         * 动态加载Fragment
         * FragmentManager manager= getFragmentManager();
         FragmentTransaction transaction=manager.beginTransaction();
         transaction.add(R.id.linear_del,new Fragements());
         transaction.addToBackStack(null);
         transaction.commit();提交数据

        Fragment findId=fragmentManager.findFragmentById();
        Fragements frag=(Fragements)findFragmentById;
        frag.setArguments("fragment静态传值");*/
    }

    public void imageButtonOnClick(View v){
        switch (v.getId()) {
            case R.id.myhome:
                mViewPager.setCurrentItem(0);
                myhome.setImageResource(R.mipmap.home156);
                break;
            case R.id.circle:
                mViewPager.setCurrentItem(1);
                circle.setImageResource(R.mipmap.circle156);
                break;
            case R.id.messenger:
                mViewPager.setCurrentItem(2);
                messenger.setImageResource(R.mipmap.messenger156);
                break;
            case R.id.personal:
                mViewPager.setCurrentItem(3);
                personal.setImageResource(R.mipmap.person156);
                break;
            default:
                break;
        }

    }

    //监听屏幕的手势
/*
    public boolean onTouchEvent(MotionEvent ev){

            if(ev.getAction()== ACTION_MOVE)
               mViewPager.setCurrentItem(ACTION_MOVE-1);
                personal.setImageResource(R.mipmap.person156);
        return true;
    }
*/

    private class LayoutClickListener implements View.OnTouchListener {

        @Override
        public boolean onTouch(View v, MotionEvent event) {

            /**
             * 判断哪个要显示，及设置按钮图片
             */
            switch (v.getId()) {
                case R.id.LinearLayout1:
                    mViewPager.setCurrentItem(0);
                    myhome.setImageResource(R.mipmap.home156);
                    break;
                case R.id.LinearLayout2:
                    mViewPager.setCurrentItem(1);
                    circle.setImageResource(R.mipmap.circle156);
                    break;
                case R.id.LinearLayout3:
                    mViewPager.setCurrentItem(2);
                    messenger.setImageResource(R.mipmap.messenger156);
                    break;
                case R.id.LinearLayout4:
                    mViewPager.setCurrentItem(3);
                    personal.setImageResource(R.mipmap.person156);
                    break;
                default:
                    break;
            }
            return false;
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.home:
                // app icon in Action Bar clicked; Go home
                Intent intent = new Intent(this, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    /**
     * 把所有图片变暗

    public void resetImg() {
        myhome.setImageResource(R.mipmap.myhome);
        circle.setImageResource(R.mipmap.school);
        messenger.setImageResource(R.mipmap.messenger);
        personal.setImageResource(R.mipmap.personal);
    }*/



}
