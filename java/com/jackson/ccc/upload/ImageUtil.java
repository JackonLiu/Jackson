package com.jackson.ccc.upload;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.util.LruCache;
import android.widget.ImageView;
import android.widget.ListView;

import com.jackson.ccc.util.NewsAdapter;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Set;

public class ImageUtil {

    private static final String TAG ="ImageUtil";
    private ListView mListView;
    private Set<NewsAsynTask> mTask;

    public static Intent choosePicture() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        return Intent.createChooser(intent, null);
    }

    public static Intent takeBigPicture() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, newPictureUri(getNewPhotoPath()));
        return intent;
    }

    public static String getDirPath() {
        return Environment.getExternalStorageDirectory().getPath() + "/WebViewUploadImage";
    }

    private static String getNewPhotoPath() {
        return getDirPath() + "/" + System.currentTimeMillis() + ".jpg";
    }

    public static String retrievePath(Context context, Intent sourceIntent, Intent dataIntent) {
        String picPath = null;
        try {
            Uri uri;
            if (dataIntent != null) {
                uri = dataIntent.getData();
                if (uri != null) {
                    picPath = ContentUtil.getPath(context, uri);
                }
                if (isFileExists(picPath)) {
                    return picPath;
                }

                Log.w(TAG, String.format("retrievePath failed from dataIntent:%s, extras:%s", dataIntent, dataIntent.getExtras()));
            }

            if (sourceIntent != null) {
                uri = sourceIntent.getParcelableExtra(MediaStore.EXTRA_OUTPUT);
                if (uri != null) {
                    String scheme = uri.getScheme();
                    if (scheme != null && scheme.startsWith("file")) {
                        picPath = uri.getPath();
                    }
                }
                if (!TextUtils.isEmpty(picPath)) {
                    File file = new File(picPath);
                    if (!file.exists() || !file.isFile()) {
                        Log.w(TAG, String.format("retrievePath file not found from sourceIntent path:%s", picPath));
                    }
                }
            }
            return picPath;
        } finally {
            Log.d(TAG, "retrievePath(" + sourceIntent + "," + dataIntent + ") ret: " + picPath);
        }
    }

    private static Uri newPictureUri(String path) {
        return Uri.fromFile(new File(path));
    }

    private static boolean isFileExists(String path) {
        if (TextUtils.isEmpty(path)) {
            return false;
        }
        File f = new File(path);
        if (!f.exists()) {
            return false;
        }
        return true;
    }

    private LruCache<String,Bitmap> mCaches;
    public  ImageUtil(){
        //获取最大的可用内存
        int maxMemory = (int)Runtime.getRuntime().maxMemory();
        int cachesize = maxMemory / 4;

        mCaches = new LruCache<String,Bitmap>(cachesize){
            @Override
            protected int sizeOf(String key, Bitmap value) {
                //在每次存入缓存中使用
                return  value.getByteCount();
            }
        };
    }
//增加到缓存
    public void addBitmapToCache(String url,Bitmap bitmap){
        if (getBitmapFromCache(url)==null){
            mCaches.put(url,bitmap);
        }
    }
//从缓存（内存）中获取数据
    public Bitmap getBitmapFromCache(String url){
        return mCaches.get(url);
    }

    public void showImageByAsynTask(ImageView imageView,String url){
//从缓存中取出对应的图片
        Bitmap bitmap = getBitmapFromCache(url);
        //如果缓存中没有，就从网络中下载
        if(bitmap == null){
            new NewsAsynTask(url).execute(url);
        } else{
            imageView.setImageBitmap(bitmap);
        }


    }




    public void loadImages(int start ,int end){
        for(int i=start;i<end;i++){
            String url = NewsAdapter.urls[i];
//从缓存中取出对应的图片
            Bitmap bitmap = getBitmapFromCache(url);
            //如果缓存中没有，就村网络中下载
            if(bitmap == null){
             NewsAsynTask task=   new NewsAsynTask(url);task.execute(url);
                mTask.add(task);
            } else{
                ImageView imageView = (ImageView) mListView.findViewWithTag(url);
                imageView.setImageBitmap(bitmap);
            }

        }
    }

    public void cancelAllTask() {
       if(mTask!=null){
           for (NewsAsynTask task : mTask){
               task.cancel(false);
           }
       }
    }


    protected class NewsAsynTask extends AsyncTask<String,Void,Bitmap>{

        private String mUrl;

        public NewsAsynTask( String mUrl) {

            this.mUrl = mUrl;
        }

        @Override
        protected Bitmap doInBackground(String... params) {
            String url = params[0];
            //从网络上获取图片
            Bitmap bitmap = getBitmapFromURL(params[0]);
            if(bitmap!=null){
        //将不在缓存的图片加入到缓存
                addBitmapToCache(url,bitmap);
            }
            return bitmap;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            super.onPostExecute(bitmap);
            ImageView imageView = (ImageView)mListView.findViewWithTag(mUrl);
            if(imageView!=null&&bitmap!=null){
                imageView.setImageBitmap(bitmap);
            }
        }

        private Bitmap getBitmapFromURL(String urlString) {
        Bitmap bitmap;
            URL url = null;
            InputStream is = null;
            try {
                url = new URL(urlString);
                HttpURLConnection  connection;
                connection = (HttpURLConnection)url.openConnection();
            is=new BufferedInputStream(connection.getInputStream());
                bitmap= BitmapFactory.decodeStream(is);
                connection.disconnect();
                return bitmap;

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }
    }



}
