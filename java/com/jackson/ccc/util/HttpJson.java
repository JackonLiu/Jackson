package com.jackson.ccc.util;

import android.content.Context;
import android.os.Handler;
import android.widget.ListView;
import android.widget.Toast;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by LXP on 17-3-8.


public class HttpJson  extends Thread {
    private String url;
    private ListView listView;
    private JsonAdapter adapter;
    private Context context;
    private Handler handler = new Handler();

    public HttpJson(String url, ListView listView, JsonAdapter adapter, Handler handler) {
        this.url = url;
        this.listView = listView;
        this.adapter = adapter;
        this.handler = handler;
    }

    @Override
    public void run() {
        URL httpUrl;
        String str;
        BufferedReader reader;
        try {
            httpUrl = new URL(url);
            HttpURLConnection conn = (HttpURLConnection) httpUrl.openConnection();
            conn.setReadTimeout(5000);
            conn.setRequestMethod("GET");
            reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            StringBuffer stringBuffer = new StringBuffer();

            while ((str = reader.readLine()) != null) {
                stringBuffer.append(str);
            }
            final List<Person> data = parseJson(stringBuffer.toString());
            handler.post(new Runnable() {
                @Override
                public void run() {
                    adapter.setData(data);
                    listView.setAdapter(adapter);
                }
            });
        }
        catch (ProtocolException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    private List<Person> parseJson(String json) {


        JSONObject object = new JSONObject();

        List<Person> persons = new ArrayList<Person>();
        int result = object.getInt("result");

        if (result == 1) {
            JSONArray personData = object.getJSONArray("personData");

            for (int i = 0; i < personData.size(); i++) {

                Person personObject = new Person();
                persons.add(personObject);

                JSONObject person = personData.getJSONObject(i);
                String name = person.getString("name");
                int age = person.getInt("age");
                String url = person.getString("url");
                personObject.setName(name);
                personObject.setAge(age);
                personObject.setUrl(url);
                JSONArray schoolInfos = person.getJSONArray("schoolInfo");
                List<SchoolInfo> schInfos = new ArrayList<SchoolInfo>();
                personObject.setSchoolInfo((SchoolInfo) schInfos);
                for (int j = 0; j < schoolInfos.size(); j++) {
                    JSONObject school = schoolInfos.getJSONObject(i);
                    String schoolName = school.getString("school_name");
                    SchoolInfo info = new SchoolInfo();
                    info.setSchoolInfo(schoolName);
                    schInfos.add(info);
                }
            }
            return persons;
        }else {
            Toast.makeText(context,"error",Toast.LENGTH_SHORT).show();
        }
        return null;
    }
}




*/