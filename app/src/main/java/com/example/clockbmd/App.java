package com.example.clockbmd;

import android.app.Application;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Build;

public class App extends Application {
    public static final String CHANNEL_ALARM = "alarmChannel";
    public static final String CHANNEL_TIMER = "timerChannel";

    @Override
    public void onCreate() {
        super.onCreate();

        createNotificationChannel();
    }

    private void createNotificationChannel() {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel alarmChannel = new NotificationChannel(
                    CHANNEL_ALARM,
                    "Alarm Channel",
                    NotificationManager.IMPORTANCE_HIGH);
            alarmChannel.setDescription("This is the alarm channel");

            NotificationChannel timerChannel = new NotificationChannel(
                    CHANNEL_TIMER,
                    "Timer Channel",
                    NotificationManager.IMPORTANCE_HIGH);
            timerChannel.setDescription("This is the alarm channel");

            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(alarmChannel);
            manager.createNotificationChannel(timerChannel);
        }
    }
}
