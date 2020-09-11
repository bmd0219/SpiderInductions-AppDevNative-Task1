package com.example.clockbmd;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;

import static com.example.clockbmd.App.CHANNEL_ALARM;

public class AlarmForegroundService extends Service {
    public static final String TAG = "AFS";
    public static final String SHARED_PREFS = "alarmSharedPrefs";
    private NotificationManagerCompat notificationManager;
    private NotificationCompat.Builder builder;
    private PendingIntent pendingIntent;
    private Notification notification;

    private Timer timer = new Timer();
    private Ringtone ringtone;
    private int minutes;
    private int date;
    private int hours;


    @Override
    public void onCreate() {
        Log.println(Log.ASSERT, TAG, "onCreateService");
        super.onCreate();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(final Intent intent, final int flags, final int startId) {

        Log.println(Log.ASSERT, TAG, "onStartCommand");

        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        minutes = sharedPreferences.getInt("minutes", Calendar.getInstance().get(Calendar.MINUTE));
        hours = sharedPreferences.getInt("hours", Calendar.getInstance().get(Calendar.HOUR_OF_DAY));
        date = sharedPreferences.getInt("date", Calendar.getInstance().get(Calendar.DAY_OF_WEEK));

        Log.println(Log.ASSERT, TAG, String.valueOf(minutes));
        Log.println(Log.ASSERT, TAG, String.valueOf(hours));
        Log.println(Log.ASSERT, TAG, String.valueOf(date));

        Intent contentIntent = new Intent(this, MainActivity.class);
        pendingIntent = PendingIntent.getActivity(this, 1, contentIntent, 0);

        builder = new NotificationCompat.Builder(getApplicationContext(), CHANNEL_ALARM)
                .setSmallIcon(R.drawable.ic_baseline_android_24)
                .setContentTitle("WAKE UP!!")
                .setContentText(String.valueOf(Calendar.getInstance().getTimeInMillis()))
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setCategory(NotificationCompat.CATEGORY_ALARM)
                .setContentIntent(pendingIntent)
                .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM));
        notificationManager = NotificationManagerCompat.from(getApplicationContext());
        notification = builder.build();

        ringtone = RingtoneManager.getRingtone(this, RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM));

        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                if (Calendar.getInstance().get(Calendar.MINUTE) == minutes && Calendar.getInstance().get(Calendar.HOUR_OF_DAY) == hours && Calendar.getInstance().get(Calendar.DAY_OF_WEEK) == date) {

                    notificationManager.notify(2, notification);
                    ringtone.play();
                    Log.println(Log.ASSERT, TAG, "ALARM!!!");
                    deleteAlarm();
                    stopSelf();
                } else {
                    ringtone.stop();
                    Log.println(Log.ASSERT, TAG, "stop ringtone");
                }
            }
        }, 0, 2000);

        startForeground(1, notification);
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        ringtone.stop();
        timer.cancel();
        deleteAlarm();
        super.onDestroy();
    }

    private void deleteAlarm() {
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove("minutes");
        editor.remove("hours");
        editor.remove("date");
        editor.commit();
    }
}
