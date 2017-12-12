package com.jackson.ccc.gridview;

import android.app.FragmentManager;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;

import com.jackson.ccc.test.R;
import com.jackson.ccc.util.MyAdapter;
import com.jackson.ccc.util.ViewHolder;
import com.jackson.ccc.viewpage.DepthPageTransformer;

import org.androidannotations.annotations.EActivity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by LXP on 17-3-4.
 */

@EActivity(R.layout.agora_layout)
public class Agora extends FragmentActivity {
    ViewpagerIndicator mIndicator;
    ViewPager mViewPager;// 用来放置界面切换
    //fragment的数据集
    private List<VpSimpleFragment> mContents = new ArrayList<VpSimpleFragment>();
    private FragmentPagerAdapter mPagerAdapter;// 初始化View适配器
    private BaseAdapter adapter;
    private Agora mContext;
    private ListView agoraList;
    private List<String> curList = new ArrayList<>();
    private List<String> mTitles = Arrays.asList("全部", "课本", "电子", "服饰", "单车", "生活用品");
    private int lastPress = 0;
    private boolean delState = false;  //是否删除的标志
    private Button allProducts, book, technology, photo, life, bicycle;
    private ImageView image;
    private ImageButton agoraAdd;

    @RequiresApi(api = Build.VERSION_CODES.GINGERBREAD)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.agora_layout);
        mViewPager = (ViewPager) findViewById(R.id.viewPager);
        //init();
        initDatas();
        agoraAdd = (ImageButton) findViewById(R.id.agoraAdd);

        mIndicator = new ViewpagerIndicator(Agora.this);
        mIndicator = (ViewpagerIndicator) findViewById(R.id.indicator);

        mIndicator.setVisibleTabCount(4);
        mIndicator.setTabItemTitles(mTitles);
        mViewPager.setAdapter(mPagerAdapter);
        mViewPager.setPageTransformer(true, new DepthPageTransformer());
        Log.e("222", mTitles.get(1));
        mIndicator.setViewPager(mViewPager, 0);
    }

    public void goAdd(View view) {
        Intent intent = new Intent(Agora.this, AddAgora.class);
        startActivity(intent);

    }

    private void initDatas() {
        for (String title : mTitles) {
            VpSimpleFragment fragment = VpSimpleFragment.newInstance(title);
            mContents.add(fragment);
        }
/*        FragmentManager manager = Fragment.getChildFragmentManager();
        mPagerAdapter = new MyFragPagerAdapter(getCallingActivity().getChildFragmentManager(), mContents);
        mPagerAdapter = new ViewPager_Adapter(childFragmentManager, mContents);*/
        mPagerAdapter = new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return mContents.get(position);
            }

            @Override
            public int getCount() {
                return mContents.size();
            }
        };


    }

/*    private class MyFragPagerAdapter extends Fragment {
        public MyFragPagerAdapter(FragmentManager manager, Object p1) {
            super();
        }
    }*/
}

    //初始化Listview，然后设置长按监听器
    /*private void init() {

        mContext = this;
        String[] strings = {"全部","课本","电子","服饰","单车","生活用品"};
        for (String s : strings) {
            curList.add(s);
        }
        MyAdapter<ThreeStrBean> adapter;
        ThreeStrBean threeStrBean = new ThreeStrBean();
        List<ThreeStrBean> threeStrBeanList = new ArrayList<>();
        threeStrBean.setNewsIconUrl("picSmall");
        threeStrBean.setNewsTitle("name");
        threeStrBean.setNewsContent("description");
        threeStrBeanList.add(threeStrBean);
        agoraList = (ListView) findViewById(R.id.agora_list);
        agoraList.setAdapter(adapter = new MyAdapter<ThreeStrBean>(
                getApplicationContext(), threeStrBeanList, R.layout.item_layout) {
            @Override
            public void convert(ViewHolder helper, ThreeStrBean item) {
                Log.e("222",item.toString());
                helper.setImageResource(R.id.img_icon, R.mipmap.news);
                helper.setText(R.id.tv_title, item.getNewsTitle());
                Log.e("222",item.getNewsTitle());
                helper.setText(R.id.tv_text, item.getNewsContent());
            }
        });*/
       //agoraList.addHeaderView(getCurrentFocus());

        //adapter.add(1,new Data(R.mipmap.agora,"这是第 1  个listView"));

       //agoraList.setAdapter(adapter);  //将适配器与ListView关联
       // agoraList.addFooterView(getCurrentFocus());

       /* agoraList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (delState) {
                    if (lastPress < parent.getCount()) {
                        View delview = parent.getChildAt(lastPress).findViewById(R.id.linear_del);
                        if (null != delview) {
                            delview.setVisibility(View.GONE);
                        }
                    }
                    delState = false;
                    return;
                } else {
                    Log.d("click:", position + "");
                }
                 lastPress = position;
            }
        });


        agoraList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            private View delview;
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                if (lastPress < parent.getCount()) {
                    delview = parent.getChildAt(lastPress).findViewById(R.id.linear_del);
                    if (null != delview) {
                        delview.setVisibility(View.GONE);
                    }
                }

                delview = view.findViewById(R.id.linear_del);
                delview.setVisibility(View.VISIBLE);

                delview.findViewById(R.id.tv_del).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        delview.setVisibility(View.GONE);
                        curList.remove(position);
                        adapter.notifyDataSetChanged();
                    }
                });
                delview.findViewById(R.id.tv_cancel).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        delview.setVisibility(View.GONE);
                    }
                });
                lastPress = position;
                delState = true;
                return true;
            }
        });*/




    /**
     *
     *  // 适配器初始化并设置
     mPagerAdapter = new PagerAdapter() {

    @Override
    public void destroyItem(ViewGroup container, int position,
    Object object) {
    container.removeView(mViews.get(position));

    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
    //实例化一个页卡
    View view = mViews.get(position);
    container.addView(view);
    return view;
    }

    @Override
    public boolean isViewFromObject(View arg0, Object arg1) {
    //view是否在紫玉对象
    return arg0 == arg1;
    }

    @Override
    public int getCount() {
    //返回页卡数量
    return mViews.size();
    }
    };
     mViewPager.setAdapter(mPagerAdapter);
     */


