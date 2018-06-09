package com.yoloholo.androidalarm;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.widget.Toast;

public class BroadCastD extends BroadcastReceiver {
    String INTENT_ACTION = Intent.ACTION_BOOT_COMPLETED;

    private String NOTIFICATION_CHANNEL_ID = "NOTICHANNELID";
    private CharSequence NOTIFICATION_CHANNEL_NAME = "NOTICHANNELNAME";


    @Override
    public void onReceive(Context context, Intent intent) {



        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {

            Intent notificationIntent = new Intent(context, MainActivity.class);

            NotificationCompat.Builder builder = new NotificationCompat.Builder(context);

            PendingIntent contentIntent = PendingIntent.getActivity(context, intent.getIntExtra("requestCode", 1000), notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);

            builder.setContentTitle("상태바 드래그시 보이는 타이틀")
                    .setContentText("상태바 드래그시 보이는 서브타이틀")
                    .setTicker("상태바 한줄 메시지")
                    .setSmallIcon(R.mipmap.ic_launcher)
                    .setAutoCancel(true)
                    .setPriority(Notification.PRIORITY_HIGH)
                    .setDefaults(Notification.DEFAULT_ALL);
            builder.setCategory(Notification.CATEGORY_MESSAGE)
                    .setPriority(Notification.PRIORITY_HIGH)
                    .setContentIntent(contentIntent)
                    .setAutoCancel(true)
                    .setVisibility(Notification.VISIBILITY_PUBLIC);
            NotificationManager nm = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
            nm.notify(intent.getIntExtra("notiId", 10000), builder.build());
            Toast.makeText(context, intent.getIntExtra("requestCode", 1000) + "", Toast.LENGTH_SHORT).show();
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            int notiId = intent.getIntExtra("notiId", 10000);
            int requestCode = intent.getIntExtra("requestCode", 1000);

            Intent notificationIntent = new Intent(context, MainActivity.class);
            PendingIntent contentIntent = PendingIntent.getActivity(context, requestCode, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);

            int importance = android.app.NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel notificationChannel = new NotificationChannel(NOTIFICATION_CHANNEL_ID, NOTIFICATION_CHANNEL_NAME, importance);
            notificationChannel.enableLights(true);
            notificationChannel.setLightColor(Color.RED);
            notificationChannel.enableVibration(true);
            notificationChannel.setVibrationPattern(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400});
            notificationChannel.setLockscreenVisibility(Notification.VISIBILITY_PUBLIC);

            android.app.NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
            notificationManager.createNotificationChannel(notificationChannel);

            Notification.Builder mBuilder = new Notification.Builder(context, NOTIFICATION_CHANNEL_ID);
            mBuilder.setContentTitle("MyTitle!!");
            mBuilder.setContentText("MyText!!!!");
            mBuilder.setContentIntent(contentIntent);
            mBuilder.setAutoCancel(true);
            mBuilder.setSmallIcon(R.mipmap.ic_launcher);

//            mBuilder.setDefaults(Notification.DEFAULT_SOUND | Notification.DEFAULT_VIBRATE).setContentIntent(pendingIntent).setAutoCancel(true);


//                    mBuilder.setWhen(Calendar.getInstance().getTimeInMillis() + 5000);
//                    mBuilder.setPriority(Notification.PRIORITY_HIGH);
//                    mBuilder.setChannelId(NOTIFICATION_CHANNEL_ID);

            if (notificationManager != null) {

                notificationManager.notify(notiId, mBuilder.build());
            }


        }
    }
}
