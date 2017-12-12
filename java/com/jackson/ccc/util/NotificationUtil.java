/*
package com.jackson.ccc.util;


import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

import com.jackson.ccc.test.R;
import com.jackson.ccc.upload.MyActivity;

import java.net.FileNameMap;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.FileHandler;

public class NotificationUtil  {
    private NotificationManager mNotificationManager=null;
    private Map<Integer,Notification> mNotification = null;
    private Context context;

    public NotificationUtil(NotificationManager mNotificationManager) {
        this.mNotificationManager = mNotificationManager;
    }

    public NotificationUtil(Context context) {
        mNotificationManager=(NotificationManager)context.getSystemService(context.NOTIFICATION_SERVICE);
        mNotification =new HashMap<Integer,Notification>();
    }

    public void showNotification(FileHandler fileInfo){
        if(!mNotification.containsKey(fileInfo.getId())){
            Notification notification = new Notification();
            notification.tickerText  = fileInfo.getName() + "start download";
            notification.when = System.currentTimeMillis();
            //设置图标
            notification.iconLevel = R.drawable.ic_action_back;
            notification.flags = Notification.FLAG_AUTO_CANCEL;
            Intent intent= new Intent(context, MyActivity.class);
            PendingIntent pintent = PendingIntent.getActivity(context,0,intent,0);
            notification.contentIntent = pintent;
            //创建RemoteViews对象
            RemoteViews remoteViews = new RemoteViews(context.getPackageName(),R.layout.notification);
            Intent intentStart = new Intent(context,DownloadService.class);
            intentStart.setAction(DownloadService.ACTION_START);
            intentStart.putExtra("fileinfo", String.valueOf(fileInfo));
            PendingIntent piStart = PendingIntent.getService(context,0,intentStart,0);
            remoteViews.setOnClickPendingIntent(R.id.yes,piStart);

            Intent intentStop = new Intent(context,DownloadService.class);
            intentStop.setAction(DownloadService.ACTION_STOP);
            intentStop.putExtra("fileinfo", String.valueOf(fileInfo));
            PendingIntent piStop = PendingIntent.getService(context,0,intentStop,0);
            remoteViews.setOnClickPendingIntent(R.id.cancel,piStop);

            notification.contentView=remoteViews;
            mNotificationManager.notify(fileInfo.getId(),notification);
            mNotification.put(fileInfo.getId(),notification);
        }
    }

    public void cancelNotification(int id){
        mNotificationManager.cancel(id);
        mNotification.remove(id);
    }

    public void updateNotification(int id ,int progress){
        Notification notification = mNotification.get(id);
        if (notification!=null){
            //修改进度条
            notification.contentView.setProgressBar(R.id.progressfile,100,progress,false);
            mNotificationManager.notify(id,notification);
        }
    }
}
*/
