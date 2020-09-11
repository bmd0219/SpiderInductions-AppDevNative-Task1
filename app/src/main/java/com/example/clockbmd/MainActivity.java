package com.example.clockbmd;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity implements AlarmFragment.AlarmFragmentListener {

    private Fragment alarmFragment = new AlarmFragment();
    private Fragment timerFragment = new TimerFragment();
    private Fragment stopwatchFragment = new StopwatchFragment();
    private Fragment fragment;
    private int fragmentId;
    private FragmentManager fragmentManager;
    public static final String SHARED_PREFS = "alarmSharedPrefs";

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
        Intent intent = new Intent(this, AlarmForegroundService.class);
        if (c.before(Calendar.getInstance())) {
            c.add(Calendar.DATE, 1);
        }
        int minutes = c.get(Calendar.MINUTE);
        int hours = c.get(Calendar.HOUR_OF_DAY);
        int date = c.get(Calendar.DAY_OF_WEEK);
        Log.println(Log.ASSERT, "mainActivity", String.valueOf(c.getTimeInMillis()));

        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("minutes", minutes);
        editor.putInt("hours", hours);
        editor.putInt("date", date);
        editor.commit();

        ContextCompat.startForegroundService(this, intent);
        Toast.makeText(this, "ALARM SET AT " + hours + ":" + minutes, Toast.LENGTH_SHORT).show();
        Log.println(Log.ASSERT, "mainActivity", "startedService");
    }

    @Override
    public void stopAlarm() {
        Intent intent = new Intent(this, AlarmForegroundService.class);
        stopService(intent);
        Toast.makeText(this, "Alarm Deleted", Toast.LENGTH_SHORT).show();
    }

}