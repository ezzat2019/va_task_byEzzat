package com.talabto.vataskbyezzat.services;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;

import androidx.annotation.Nullable;

import com.talabto.vataskbyezzat.R;


public class RunAllTimeService extends IntentService {


    public RunAllTimeService() {
        super("MathService");
    }

    @Override
    public int onStartCommand(@Nullable Intent intent, int flags, int startId) {


        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel("ch1", "calc", NotificationManager.IMPORTANCE_LOW);
            NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
            manager.createNotificationChannel(channel);

            Notification notification = new Notification.Builder(getApplicationContext(), "ch1")
                    .setContentTitle("this app use background services")
                    .setSmallIcon(R.mipmap.ic_launcher)
                    .build();
            startForeground(1, notification);
        }
        return START_STICKY;
    }

    @Override
    protected void onHandleIntent(Intent intent) {

    }


}