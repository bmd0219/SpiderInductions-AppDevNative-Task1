package com.example.clockbmd;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;

import static com.example.clockbmd.App.CHANNEL_ALARM;

public class AlarmForegroundService extends Service {
    public static final String TAG = "AFS";

    private Timer timer = new Timer();
    private Ringtone ringtone;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(final Intent intent, final int flags, final int startId) {

        Log.println(Log.ASSERT, TAG, "onStrartCommand");

        final int minutes = intent.getIntExtra("minutes", 0);
        final int hours = intent.getIntExtra("hours", 0);
        final int date = intent.getIntExtra("date", 0);
        Log.println(Log.ASSERT, TAG, String.valueOf(minutes));
        Log.println(Log.ASSERT, TAG, String.valueOf(hours));
        Log.println(Log.ASSERT, TAG, String.valueOf(date));


        Intent contentIntent = new Intent(this, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 1, contentIntent, 0);

        final Notification notification = new NotificationCompat.Builder(this, CHANNEL_ALARM)
                .setSmallIcon(R.drawable.ic_baseline_android_24)
                .setContentTitle("WAKE UP!!")
                .setContentText(String.valueOf(Calendar.getInstance().getTimeInMillis()))
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setCategory(NotificationCompat.CATEGORY_ALARM)
                .setContentIntent(pendingIntent)
                .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM))
                .build();

        final NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);

        ringtone = RingtoneManager.getRingtone(this, RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM));

        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                if(Calendar.getInstance().get(Calendar.MINUTE) == minutes && Calendar.getInstance().get(Calendar.HOUR_OF_DAY) == hours && Calendar.getInstance().get(Calendar.DAY_OF_WEEK) == date) {
                    notificationManager.notify(1, notification);
                    ringtone.play();
                    Log.println(Log.ASSERT, TAG, "ALARM!!!");
                    stopSelf();
                } else {
                    ringtone.stop();
                }
            }
        }, 0, 2000);


        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        ringtone.stop();
        timer.cancel();
        super.onDestroy();
    }
}
