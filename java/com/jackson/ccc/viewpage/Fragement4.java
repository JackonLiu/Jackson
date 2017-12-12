package com.jackson.ccc.viewpage;

import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.jackson.ccc.gridview.Data;
import com.jackson.ccc.test.R;
import com.tencent.mm.opensdk.modelmsg.SendMessageToWX;
import com.tencent.mm.opensdk.modelmsg.WXAppExtendObject;
import com.tencent.mm.opensdk.modelmsg.WXImageObject;
import com.tencent.mm.opensdk.modelmsg.WXMediaMessage;
import com.tencent.mm.opensdk.modelmsg.WXTextObject;
import com.tencent.mm.opensdk.modelmsg.WXWebpageObject;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.tencent.mm.sdk.platformtools.Util;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by LXP on 17-3-12.
 */

public class Fragement4 extends Fragment{

    private static final int THUMB_SIZE = 150;

    private static final String SDCARD_ROOT = Environment.getExternalStorageDirectory().getAbsolutePath();
    private static final String APP_ID="wx0cca2248b04c9364";
    private IWXAPI api;
    private static final int MMAlertSelect1  =  0;
    private static final int MMAlertSelect2  =  1;
    private static final int MMAlertSelect3  =  2;

    private int mTargetScene = SendMessageToWX.Req.WXSceneSession;
    private int friendCircle = SendMessageToWX.Req.WXSceneTimeline;
    private int favorate = SendMessageToWX.Req.WXSceneFavorite;

    private LinearLayout join,sendToWX,mypopulerity,myfavorites;
    private ImageView image;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.person, container, false);
        initView(view);

        return view;
    }
    //将应用的appid注册到微信
    private void regToWx(){
        api = WXAPIFactory.createWXAPI(getActivity(),APP_ID,true);
        api.registerApp(APP_ID);
    }

    private  void initView(View view) {
        regToWx();
        myfavorites=(LinearLayout)view.findViewById(R.id.myfavorites);
        myfavorites.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                WXWebpageObject webpage = new WXWebpageObject();
                webpage.webpageUrl = "http://www.jianshu.com/u/600a13241fd6";
                WXMediaMessage msg = new WXMediaMessage(webpage);
                msg.title = "我的简书";
                msg.description = "我写的文章";
                Bitmap bmp = BitmapFactory.decodeResource(getResources(), R.mipmap.send_img);
                Bitmap thumbBmp = Bitmap.createScaledBitmap(bmp, THUMB_SIZE, THUMB_SIZE, true);
                bmp.recycle();
                msg.thumbData = Util.bmpToByteArray(thumbBmp, true);

                SendMessageToWX.Req req = new SendMessageToWX.Req();
                req.transaction = buildTransaction("webpage");
                req.message = msg;
                req.scene = mTargetScene;
                api.sendReq(req);
            }
        });

        mypopulerity=(LinearLayout)view.findViewById(R.id.mypopulerity);
        mypopulerity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bitmap bmp = BitmapFactory.decodeResource(getResources(), R.mipmap.send_img);
                WXImageObject imgObj = new WXImageObject(bmp);

                WXMediaMessage msg = new WXMediaMessage();
                msg.mediaObject = imgObj;

                Bitmap thumbBmp = Bitmap.createScaledBitmap(bmp, THUMB_SIZE, THUMB_SIZE, true);
                bmp.recycle();
                msg.thumbData = Util.bmpToByteArray(thumbBmp, true);

                SendMessageToWX.Req req = new SendMessageToWX.Req();
                req.transaction = buildTransaction("img");
                req.message = msg;
                req.scene = mTargetScene;
                api.sendReq(req);
            }
        });


        sendToWX=(LinearLayout)view.findViewById(R.id.sendToWX);
        sendToWX.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                WXTextObject textObj = new WXTextObject();
                textObj.text = "敲代码中，问题请自行解决";

                WXMediaMessage msg = new WXMediaMessage();
                msg.mediaObject = textObj;
                // msg.title = "Will be ignored";
                msg.description ="生命不息代码不止";

                SendMessageToWX.Req req = new SendMessageToWX.Req();
                req.message = msg;
                req.scene = mTargetScene;
                api.sendReq(req);
                //finish();
            }
        });

        join=(LinearLayout)view.findViewById(R.id.joinus);
        join.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final WXAppExtendObject appdata = new WXAppExtendObject();
                appdata.extInfo = "this is appdata info";
                final WXMediaMessage msg = new WXMediaMessage();
                msg.title = "Don't afraid";
                msg.description = "I want somebody";
                msg.mediaObject = appdata;

                SendMessageToWX.Req req = new SendMessageToWX.Req();
                req.transaction = buildTransaction("appdata");
                req.message = msg;
                //发送到朋友圈
                req.scene = friendCircle;
                api.sendReq(req);

            }
        });

    }

    private void showDialog() {

        final EditText inputServer = new EditText(getActivity());
        inputServer.setFocusable(true);

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(getString(R.string.we)).setIcon(
                R.mipmap.ic_action_group).setView(inputServer).setNegativeButton(
                getString(R.string.save), null);
        builder.setMessage("队长：王帅            " +
                "编码：刘鑫鹏           " +
                "界面：钟静雅         " +
                "需求分析：邹文勋         " +
                "请输入您的姓名和微信：");
        builder.show();
    }

    private String buildTransaction(final String type) {
        return (type == null) ? String.valueOf(System.currentTimeMillis()) : type + System.currentTimeMillis();
    }

}
