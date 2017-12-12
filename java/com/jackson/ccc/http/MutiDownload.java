package com.jackson.ccc.http;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.webkit.DownloadListener;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.util.Random;
import java.util.RandomAccess;

/**多线程下载
 * Created by LXP on 17-3-10.
 */

public class MutiDownload {

    //定义下载的路径
    private static String path = "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1490372128488&di=b6ce98532364a87d5f3be7be48c1d410&imgtype=0&src=http%3A%2F%2Fimg155.poco.cn%2Fmypoco%2Fmyphoto%2F20090624%2F19%2F31321397200906241951108440804044150_026_640.jpg";
    //假设开三个线程
    private static final int threadCount = 3;

    public void mainDownload(){
        //获取服务器文件大小，要计算每个线程下载的开始位置和结束位置

        try{
            URL url = new URL(path);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.setReadTimeout(5000);
            int code =urlConnection.getResponseCode();
            if (code==200){

            int length= urlConnection.getContentLength();
            //创建一个大小和服务器一模一样的文件 ，目的是提前将空间申请出来
            RandomAccessFile randomAccessFile = new RandomAccessFile("timg.jpg","rw");

            randomAccessFile.setLength(length);

            //算出每个线程下载的大小
            int blockSize = length/threadCount;

            //计算每个线程的开始位置和结束位置
            for(int i = 0 ; i<threadCount ; i++){
                int startIndex = i*blockSize-1;
                int endIndex = (i+1)*blockSize -1;
                //特殊情况就是最后一个线程
                if(i == threadCount-1){
                    //说明是最后一个线程
                    endIndex = length -1;
                }
                //开启线程去服务器下载文件
                DownLoadThread downLoadThread= new DownLoadThread(startIndex,endIndex,i);
                downLoadThread.start();

            }
                } }catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }


    //定义线程去服务器下载文件
    private static class DownLoadThread extends Thread{
        //通过构造方法把每个线程下载的开始位置和结束位置传递进来
        private int startIndex;
        private int endIndex;
        private int threadId;

        public DownLoadThread(int startIndex, int endIndex, int threadId) {
            this.startIndex = startIndex;
            this.endIndex = endIndex;
            this.threadId = threadId;
        }

        @Override
        public void run() {
            //实现去服务器下载文件的逻辑
            try{
                URL url = new URL(path);
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.setReadTimeout(5000);
                urlConnection.setRequestProperty("Range","bytes="+startIndex+"-"+endIndex);

                int code = urlConnection.getResponseCode();
                if(code==206){//获取资源部分成功

                    //创建随机读取文件对象
                    RandomAccessFile raf = new RandomAccessFile("timg.jpg","rw");
                    //每个线程都要从自己的位置开始写
                    raf.seek(startIndex);

                    //存的是feiq.exe
                    InputStream in = urlConnection.getInputStream();
                    int len=-1;
                    byte[] buffer = new byte[1024];
                    while((len = in.read())!=-1){
                        raf.write(buffer,0,len);
                    }
                    raf.close();
                }

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            super.run();
        }
    }

    class MyDownload implements DownloadListener {

        @Override
        public void onDownloadStart(String url, String userAgent, String contentDisposition, String mimetype, long contentLength) {
            Uri uri = Uri.parse(url);
            Intent intent =new Intent(Intent.ACTION_VIEW,uri);
            //startActivity(intent);

        }
    }
}
