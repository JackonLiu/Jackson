package  com.jackson.ccc.prasehtml;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

import com.jackson.ccc.test.R;

import org.jsoup.*;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class PraseHtmlActivity extends AppCompatActivity {
    private static final String URL = "http://218.196.42.2/weiccsu/bindingJwc.jsp?wechatId=oqv6VtxBgqrqtuhINRvZq7KHQKUM";

    private ListView lvTitle;
    private List<TitleBean> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.prase_html_main);
        lvTitle = (ListView) findViewById(R.id.main_title_list);
        list = new ArrayList<>();
        new Get();
    }

    protected class Get implements Runnable{

        @Override
        public void run() {
            try {
                Document doc = Jsoup.connect(URL).get();//通过url获取到网页内容

                Elements elements = doc.getElementsByClass("weui-cells weui-cells_form");//查找所有class为"weui-cells weui-cells_form"的元素

                for (Element e : elements) {
                    Elements titles = e.getElementsByTag("input");//在每一个找到的元素中，查找<input>标签
                    Element element1= e.getElementById("username");
                    Log.e("222",element1.toString());
                    for (Element title : titles) {
                        //将找到的标签数据封装起来
                        TitleBean bean = new TitleBean();
                        bean.setTitle(title.text());//获取标签的内容 + title.attr("href")
                        bean.setUrl("http://blog.csdn.net");//获取标签属性，也就是文章链接
                        list.add(bean);
                        /*TitleBean bean1 = new TitleBean();
                        bean1.setTitle("222");
                        bean1.setUrl(URL+ title.attr("href"));
                        list.add(bean1);
                        Elements select = parse.select("input[type=hidden]");
                Log.e("222",select.toString());
                Element element = select.get(0);
                xsrf = element.attr("value");
                Message msg = mHandler.obtainMessage();
                msg.what = 1;
                msg.obj = xsrf;
                Log.e("222", "google_lenve_fb"+"onResponse: xsrf:" + xsrf);
                mHandler.sendMessage(msg);
                Log.e("222",xsrf);
                        */
                    }

                }


                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        lvTitle.setAdapter(new ListViewAdapter(PraseHtmlActivity.this, list));//将获取到的数据填充到listView中
                    }
                });
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }
}
