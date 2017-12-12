package com.jackson.ccc.upload;
import android.net.Uri;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;

/**
 * ReWebChomeClient
 *
 * @Author KenChung
 */
public class ReWebChomeClient extends WebChromeClient {

    private OpenFileChooserCallBack mOpenFileChooserCallBack;

    public ReWebChomeClient(OpenFileChooserCallBack openFileChooserCallBack) {
        mOpenFileChooserCallBack = openFileChooserCallBack;
    }

    //For Android 3.0+
    public void openFileChooser(ValueCallback<Uri> uploadMsg, String acceptType) {
        mOpenFileChooserCallBack.openFileChooserCallBack(uploadMsg, acceptType);
    }

    // For Android < 3.0
    public void openFileChooser(ValueCallback<Uri> uploadMsg) {
        openFileChooser(uploadMsg, "");
    }

    // For Android  > 4.1.1
    public void openFileChooser(ValueCallback<Uri> uploadMsg, String acceptType, String capture) {
        openFileChooser(uploadMsg, acceptType);
    }

    public interface OpenFileChooserCallBack {
        void openFileChooserCallBack(ValueCallback<Uri> uploadMsg, String acceptType);
    }

}
