package com.yoloholo.androidalarm;

import android.app.Application;
import android.os.Build;

public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//            NotificationManager.createChannel(this);
//        }
    }
}
