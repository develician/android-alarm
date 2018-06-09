package com.yoloholo.androidalarm;


import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    private Button alarmButton, alarmButton2;
    private String NOTIFICATION_CHANNEL_ID = "NOTICHANNELID";
    private CharSequence NOTIFICATION_CHANNEL_NAME = "NOTICHANNELNAME";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        alarmButton = (Button) findViewById(R.id.alarmButton);
        alarmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                setNotificationWithAlarm(System.currentTimeMillis() + 5000, 0, 100, 1000);

            }
        });

        alarmButton2 = (Button) findViewById(R.id.alarmButton2);
        alarmButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                setNotificationWithAlarm(System.currentTimeMillis() + 10000, 1 ,101, 1001);
                cancelNotification(0, 100);
            }
        });
    }

    private void setNotificationWithAlarm(long time, int requestCode, int flags, int notiId) {
        AlarmManager am = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(MainActivity.this, BroadCastD.class);
        intent.putExtra("requestCode", requestCode);
        intent.putExtra("notiId", notiId);

        PendingIntent sender = PendingIntent.getBroadcast(MainActivity.this, requestCode, intent, flags);

//        Calendar calendar = Calendar.getInstance();
//        //알람시간 calendar에 set해주기
//

//        calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DATE), 23, 12, 0);

        //알람 예약
        am.set(AlarmManager.RTC_WAKEUP, time, sender);


    }

    private void cancelNotification(int requestCode, int flags) {
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent myIntent = new Intent(MainActivity.this, BroadCastD.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(
                MainActivity.this, requestCode, myIntent,
                flags);

        alarmManager.cancel(pendingIntent);
        pendingIntent.cancel();
    }

    private void setNotification() {

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            Resources res = getResources();

            Intent notificationIntent = new Intent(MainActivity.this, NotificationSomething.class);
            notificationIntent.putExtra("notificationId", 9999); //전달할 값
            PendingIntent contentIntent = PendingIntent.getActivity(MainActivity.this, 0, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);

            NotificationCompat.Builder builder = new NotificationCompat.Builder(MainActivity.this);

            builder.setContentTitle("상태바 드래그시 보이는 타이틀")
                    .setContentText("상태바 드래그시 보이는 서브타이틀")
                    .setTicker("상태바 한줄 메시지")
                    .setSmallIcon(R.mipmap.ic_launcher)
                    .setLargeIcon(BitmapFactory.decodeResource(res, R.mipmap.ic_launcher))
                    .setContentIntent(contentIntent)
                    .setAutoCancel(true)
                    .setPriority(Notification.PRIORITY_MAX)
                    .setWhen(System.currentTimeMillis() + 2000)
                    .setDefaults(Notification.DEFAULT_ALL);
            builder.setCategory(Notification.CATEGORY_MESSAGE)
                    .setPriority(Notification.PRIORITY_HIGH)
                    .setVisibility(Notification.VISIBILITY_PUBLIC);
            NotificationManager nm = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            nm.notify(1234, builder.build());
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {


            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel notificationChannel = new NotificationChannel(NOTIFICATION_CHANNEL_ID, NOTIFICATION_CHANNEL_NAME, importance);
            notificationChannel.enableLights(true);
            notificationChannel.setLightColor(Color.RED);
            notificationChannel.enableVibration(true);
            notificationChannel.setVibrationPattern(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400});
            notificationChannel.setLockscreenVisibility(Notification.VISIBILITY_PUBLIC);

            NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            notificationManager.createNotificationChannel(notificationChannel);

            Notification.Builder mBuilder = new Notification.Builder(getApplicationContext(), NOTIFICATION_CHANNEL_ID);
            mBuilder.setContentTitle("MyTitle!!");
            mBuilder.setContentText("MyText!!!!");
            mBuilder.setSmallIcon(R.mipmap.ic_launcher);
//                    mBuilder.setWhen(Calendar.getInstance().getTimeInMillis() + 5000);
//                    mBuilder.setPriority(Notification.PRIORITY_HIGH);
//                    mBuilder.setChannelId(NOTIFICATION_CHANNEL_ID);

            if (notificationManager != null) {

                notificationManager.notify(100, mBuilder.build());
            }


        }

    }
}
