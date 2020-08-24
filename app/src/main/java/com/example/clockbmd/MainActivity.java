package com.example.clockbmd;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.Calendar;
import java.util.Objects;

public class MainActivity extends AppCompatActivity implements AlarmFragment.AlarmFragmentListener {

    private Fragment alarmFragment = new AlarmFragment();
    private Fragment timerFragment = new TimerFragment();
    private Fragment stopwatchFragment = new StopwatchFragment();
    private Fragment fragment;
    private int fragmentId;
    private FragmentManager fragmentManager;

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("fragmentId", fragmentId);
    }

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_nav);

        fragmentManager = getSupportFragmentManager();

        if(savedInstanceState == null) {
            fragmentId = R.id.alarm;
            fragmentManager.beginTransaction().replace(R.id.fragment_container, alarmFragment).commit();
        }


        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                fragmentId = item.getItemId();

                switch (item.getItemId()) {
                    case R.id.alarm:
                        fragment = alarmFragment;
                        break;

                    case R.id.timer:
                        fragment = timerFragment;
                        break;

                    case R.id.stopwatch:
                        fragment = stopwatchFragment;
                        break;
                }

                fragmentManager.beginTransaction().replace(R.id.fragment_container, fragment).commit();

                return true;
            }
        });


    }

    @Override
    public void startAlarm(Calendar c) {
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(this, AlertReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 1, intent, 0);
        if (c.before(Calendar.getInstance())) {
            c.add(Calendar.DATE, 1);
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            alarmManager.setExact(AlarmManager.RTC_WAKEUP, c.getTimeInMillis(), pendingIntent);
        }

    }

}