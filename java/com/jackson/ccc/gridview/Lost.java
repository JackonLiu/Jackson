package com.jackson.ccc.gridview;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;

import com.jackson.ccc.listviewfrashdemo1.ApkEntity;
import com.jackson.ccc.listviewfrashdemo1.ReFlashListView;
import com.jackson.ccc.test.R;
import com.jackson.ccc.listviewfrashdemo1.ReFlashAdapter;

import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.res.StringRes;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by LXP on 17-3-4.
 */


@EActivity(R.layout.lost_layout)
public class Lost extends Activity implements ReFlashListView.IReflashListener{
    ArrayList<ApkEntity> apk_list;
    ReFlashAdapter adapter;
    ReFlashListView listview;
    private ListView lostList;
    private Lost mContext;
    private List<String> curList = new ArrayList<>();
    private Handler handler = new Handler();
    ImageButton add;
    private ImageView image;
    private String inputName;
    private Context context;
    protected Data mData;
    private List<View> mViews = new ArrayList<View>();// 用来存放Tab01-04
    private BaseAdapter mAdapter;
    private List<Data> Bean = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lost_layout);

        setData();
        showList(apk_list);
        add = (ImageButton) findViewById(R.id.add);

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Lost.this,AddLost.class);
                startActivity(intent);
            }
        });

        // 四个Tab，每个Tab包含一个按钮，每个Tab需要对应一个布局文件
       /* LayoutInflater mLayoutInflater = LayoutInflater.from(this);
        View tab01 = mLayoutInflater.inflate(R.layout.activity_main, null);
        View tab02 = mLayoutInflater.inflate(R.layout.schoolcircle, null);
        View tab03 = mLayoutInflater.inflate(R.layout.messengers, null);
        View tab04 = mLayoutInflater.inflate(R.layout.person, null);

        mViews.add(tab01);
        mViews.add(tab02);
        mViews.add(tab03);
        mViews.add(tab04);*/

    /*    WebView webView = (WebView) findViewById(R.id.lostWeb);
        webView.getSettings().setJavaScriptEnabled(true);
        //处理javascript对话框
        webView.setWebChromeClient(new WebChromeClient());
        //处理各种请求和通知事件，不使用就会用内置浏览器访问
        webView.setWebViewClient(new WebViewClient());
        webView.getSettings().setSupportZoom(true);
        webView.getSettings().setBuiltInZoomControls(true);
        new HttpThread("http://dl.wei7s.com/mp4/20170429/18.mp4", webView, handler).start();*/

    }

    private void showList(ArrayList<ApkEntity> apk_list) {
        if (adapter == null) {
            listview = (ReFlashListView) findViewById(R.id.lost_list);
            listview.setInterface(this);
            adapter = new ReFlashAdapter(this, apk_list);
            listview.setAdapter(adapter);
        } else {
            adapter.onDateChange(apk_list);
        }
    }

    private void setData() {
        apk_list = new ArrayList<ApkEntity>();
        for (int i = 0; i < 5; i++) {
            ApkEntity entity = new ApkEntity();
            entity.setName("默认数据");
            entity.setDes("这是一个神奇的应用");
            entity.setInfo("50w用户");
            apk_list.add(entity);
        }
    }

    private void setReflashData() {
        for (int i = 0; i < 2; i++) {
            ApkEntity entity = new ApkEntity();
            entity.setName("刷新数据");
            entity.setDes("这是一个神奇的应用");
            entity.setInfo("50w用户");
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

/*    public void add(ThreeStrBean bean) {
        if (mData == null) {
            Log.e("222", " public void add(Data data)");
            mData = new ArrayList();
        }
        mData.add(bean);
        Log.e("222", "mData.add(data);" + mData);

        notifyDataSetChanged();
    }*/


/*    private String inputTitleDialog() {

        final EditText inputServer = new EditText(Lost.this);
        inputServer.setFocusable(true);

        AlertDialog.Builder builder = new AlertDialog.Builder(Lost.this);
        builder.setTitle(getString(R.string.lost)).setIcon(
                R.mipmap.lost).setView(inputServer).setNegativeButton(
                hello, null);
        builder.setPositiveButton(getString(R.string.ok),
                new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int which) {
                        inputName = inputServer.getText().toString();
                        String[] strings = {inputName};
                        for (String s : strings) {
                            curList.add(s);
                        }
                        initAdapter();

                    }
                });
        builder.show();
        return inputName;
    }*/

 /*   private void onClickStory() {

        Tencent mTencent = *//*new Tencent("9984",context)*//*null;
        if (mTencent.isSessionValid() && mTencent.getOpenId() != null) {   Bundle params = new Bundle();   params.putString(SocialConstants.PARAM_TITLE,     "AndroidSdk_1_3:UiStory title");   params.putString(SocialConstants.PARAM_COMMENT,     "AndroidSdk_1_3: UiStory comment");   params.putString(SocialConstants.PARAM_IMAGE,     "http://imgcache.qq.com/qzone/space_item/pre/0/66768.gif");   params.putString(SocialConstants.PARAM_SUMMARY,     "AndroidSdk_1_3: UiStory summary");   params.putString(     SocialConstants.PARAM_PLAY_URL,     "http://player.youku.com/player.php/Type/Folder/"
        + "Fid/15442464/Ob/1/Pt/0/sid/XMzA0NDM2NTUy/v.swf");

            *//*IUiListener listener = new BaseUiListener() {
                @Override
                protected void doComplete(JSONObject values) {
                    updateLoginButton();
                }
            };
            mTencent.story(Lost.this, params, new BaseUiListener());*//*  } }*/




}

