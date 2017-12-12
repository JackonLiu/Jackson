package  com.jackson.ccc.prasehtml;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebView;

import com.jackson.ccc.test.R;

/**
 * Created by classTC on 12/16/2015. 博客详细内容页
 */
public class DetailActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        String url = getIntent().getStringExtra("detailUrl");
        WebView mWebView = (WebView) findViewById(R.id.web);

        mWebView.getSettings().setLoadWithOverviewMode(true);
        mWebView.getSettings().setUseWideViewPort(true);

        mWebView.loadUrl(url);
    }
}
