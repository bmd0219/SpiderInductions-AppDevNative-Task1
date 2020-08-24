package com.example.clockbmd;

import android.app.Notification;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import java.util.Calendar;

import static com.example.clockbmd.App.CHANNEL_ALARM;

public class AlertReceiver extends BroadcastReceiver {

    public NotificationManagerCompat notificationManager;


    @Override
    public void onReceive(Context context, Intent intent) {

        Intent contentIntent = new Intent(context, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 1, contentIntent, 0);

        Notification notification = new NotificationCompat.Builder(context, CHANNEL_ALARM)
                .setSmallIcon(R.drawable.ic_baseline_android_24)
                .setContentTitle("WAKE UP!!")
                .setContentText(String.valueOf(Calendar.getInstance().getTimeInMillis()))
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setCategory(NotificationCompat.CATEGORY_ALARM)
                .setContentIntent(pendingIntent)
                .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM))
                .build();

        notificationManager = NotificationManagerCompat.from(context);

        notificationManager.notify(1, notification);
    }


}
