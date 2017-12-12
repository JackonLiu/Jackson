package com.jackson.ccc.http;

import android.os.Handler;
import android.widget.TextView;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;



public class XmlThread extends Thread {
    private  String url;
    private TextView textView;
    private Handler handler;
    List<Girl> list;

    public XmlThread(String url, TextView textView, Handler handler) {
        this.url = url;
        this.textView=textView;
        this.handler=handler;
    }

    @Override
    public void run() {
        try {
            
            URL httpUrl = new URL(url);
            HttpURLConnection connection = (HttpURLConnection) httpUrl.openConnection();
            connection.setReadTimeout(5000);
            connection.setRequestMethod("GET");
            InputStream in = connection.getInputStream();
            XmlPullParserFactory factory =XmlPullParserFactory.newInstance();
            XmlPullParser parser = factory.newPullParser();
            parser.setInput(in,"UTF-8");
            int evenType = parser.getEventType();

            list = new ArrayList<Girl>();
            Girl girl = null;
            while(evenType!=XmlPullParser.END_DOCUMENT){
                String data = parser.getName();
                //通过这个方法拿到不同标签的名字
                switch (evenType){
                    case XmlPullParser.START_TAG:

                        if("girl".equals(data)){
                            girl = new Girl();
                        }
                        if("name".equals(data)){
                            girl.setName(parser.nextText());
                        }
                        if("age".equals(data)){
                            girl.setAge(Integer.parseInt(parser.nextText()));
                        }
                        if("school".equals(data)){
                            girl.setSchool(parser.nextText());
                        }

                        break;
                    case XmlPullParser.END_TAG:
                        if("girl".equals(data)&&girl!=null){
                            list.add(girl);
                        }
                        break;

                }
                evenType = parser.next();
                //手动添加调用
            }
            handler.post(new Runnable() {
                @Override
                public void run() {
                    textView.setText(list.toString());
                }
            });
       /* final StringBuffer stringBuffer = new StringBuffer();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String str;
            while ((str = bufferedReader.readLine()) != null) {
                stringBuffer.append(str);
            }

            handler.post(new Runnable() {
                @Override
                public void run() {
                    webView.loadData(stringBuffer.toString(), "", null);
                }
            });
*/
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        }
    }
}
