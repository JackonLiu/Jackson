package com.jackson.ccc.upload;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.webkit.ValueCallback;
import android.webkit.WebView;

import com.jackson.ccc.test.R;

import java.io.File;

public class MyActivity extends Activity implements com.jackson.ccc.upload.ReWebChomeClient.OpenFileChooserCallBack {

    private static final String TAG = "MyActivity";
    private static final int REQUEST_CODE_PICK_IMAGE = 0;
    private static final int REQUEST_CODE_IMAGE_CAPTURE = 1;
    private WebView mWebView;
    private Intent mSourceIntent;
    private ValueCallback<Uri> mUploadMsg;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        mWebView = (WebView) findViewById(R.id.webview);

        mWebView.setWebChromeClient(new com.jackson.ccc.upload.ReWebChomeClient(this));
        mWebView.setWebViewClient(new ReWebViewClient());
        fixDirPath();
        mWebView.loadUrl("https://www.baidu.com/img/bd_logo1.png");
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != Activity.RESULT_OK) {
            return;
        }
        switch (requestCode) {
            case REQUEST_CODE_IMAGE_CAPTURE:
            case REQUEST_CODE_PICK_IMAGE: {
                try {
                    if (mUploadMsg == null) {
                        return;
                    }
                    String sourcePath = ImageUtil.retrievePath(this, mSourceIntent, data);
                    if (TextUtils.isEmpty(sourcePath) || !new File(sourcePath).exists()) {
                        Log.w(TAG, "sourcePath empty or not exists.");
                        break;
                    }
                    Uri uri = Uri.fromFile(new File(sourcePath));
                    mUploadMsg.onReceiveValue(uri);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            }
        }
    }

    @Override
    public void openFileChooserCallBack(ValueCallback<Uri> uploadMsg, String acceptType) {
        mUploadMsg = uploadMsg;
        showOptions();
    }

    public void showOptions() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        alertDialog.setOnCancelListener(new ReOnCancelListener());
        alertDialog.setTitle(R.string.agora);
        alertDialog.setItems(R.array.NewsType, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (which == 0) {
                            mSourceIntent = ImageUtil.choosePicture();
                            startActivityForResult(mSourceIntent, REQUEST_CODE_PICK_IMAGE);
                        } else {
                            mSourceIntent = ImageUtil.takeBigPicture();
                            startActivityForResult(mSourceIntent, REQUEST_CODE_IMAGE_CAPTURE);
                        }
                    }
                }
        );
        alertDialog.show();
    }

    private void fixDirPath() {
        String path = ImageUtil.getDirPath();
        File file = new File(path);
        if (!file.exists()) {
            file.mkdirs();
        }
    }

    private class ReOnCancelListener implements DialogInterface.OnCancelListener {

        @Override
        public void onCancel(DialogInterface dialogInterface) {
            if (mUploadMsg != null) {
                mUploadMsg.onReceiveValue(null);
                mUploadMsg = null;
            }
        }
    }
}
