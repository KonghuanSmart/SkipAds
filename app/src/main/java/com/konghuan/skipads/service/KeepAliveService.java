package com.konghuan.skipads.service;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

import com.konghuan.skipads.Constants;
import com.konghuan.skipads.R;
import com.konghuan.skipads.activities.MainActivity;

public class KeepAliveService extends Service {

    private final String TAG = getClass().getName() + Constants.TAG_TAIL;
    private final int PID = android.os.Process.myPid();


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        Log.d(TAG, "onBind");
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "onCreate");
        Notification keepAliveNotification = createKeepAliveNotification();
        keepAliveNotification.flags |= Notification.FLAG_AUTO_CANCEL;
        startForeground(PID, keepAliveNotification);
    }



    @Override
    public void onDestroy() {
        super.onDestroy();
        stopForeground(true);
        Log.d(TAG, "onDestroy");
    }


    private Notification createKeepAliveNotification(){
        String channelId = "KeepAliveService";
        String channelName = "Service";
        int importance = NotificationManager.IMPORTANCE_HIGH;
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.O){
            NotificationChannel channel = new NotificationChannel(channelId,channelName,importance);
            channel.setLightColor(Color.BLUE);
            channel.setLockscreenVisibility(Notification.VISIBILITY_PRIVATE);
            NotificationManager notificationManager = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
            notificationManager.createNotificationChannel(channel);
        }

        Intent intent = new Intent(this, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);

        return new NotificationCompat.Builder(this,channelId)
                .setContentTitle("SkipAds服务运行中")
                .setContentText("点击回到软件")
                .setSmallIcon(R.drawable.icon)
                .setTicker("SkipAds")
                .setContentIntent(pendingIntent)
                .setWhen(System.currentTimeMillis())
                .setAutoCancel(true)
                .build();
    }

}
