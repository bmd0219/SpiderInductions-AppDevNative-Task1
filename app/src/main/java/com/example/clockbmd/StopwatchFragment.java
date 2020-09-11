package com.example.clockbmd;

import android.content.Context;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.security.acl.LastOwnerException;

public class StopwatchFragment extends Fragment {

    public static final String TAG = "StopWatchFragment";

    private Chronometer chronometer;
    private CharSequence chronometerView;
    private Button startPauseButton;
    private Button resetButton;
    private long pauseOffset;
    private boolean running;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.println(Log.ASSERT, TAG, "onActivityCreated");
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.println(Log.ASSERT, TAG, "onCreate");
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.stopwatch_fragment, container, false);

        chronometer = v.findViewById(R.id.chronometer_view);

        if (chronometerView != null) {
            Log.println(Log.ASSERT, TAG, String.valueOf(pauseOffset));
            Log.println(Log.ASSERT, TAG, String.valueOf(running));
            chronometer.setText(chronometerView);
            running = !running;
            startChronometer();
        }
        startPauseButton = v.findViewById(R.id.start_pause_chronometer);
        resetButton = v.findViewById(R.id.reset_chronometer);

        startPauseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startChronometer();
            }
        });

        chronometer.setOnChronometerTickListener(new Chronometer.OnChronometerTickListener() {
            @Override
            public void onChronometerTick(Chronometer chronometer) {
                chronometerView = chronometer.getText();
                pauseOffset = SystemClock.elapsedRealtime() - chronometer.getBase();
                Log.println(Log.ASSERT, TAG, String.valueOf(pauseOffset));
            }
        });

        resetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resetChronometer();
            }
        });

        return v;
    }

    public void startChronometer() {
        if (!running) {
            startPauseButton.setText("PAUSE");
            chronometer.setBase(SystemClock.elapsedRealtime() - pauseOffset);
            chronometer.start();
            running = true;
        } else {
            startPauseButton.setText("START");
            resetButton.setVisibility(View.VISIBLE);
            chronometer.stop();
            pauseOffset = SystemClock.elapsedRealtime() - chronometer.getBase();
            running = false;
        }
    }

    public void resetChronometer() {
        chronometer.stop();
        chronometer.setBase(SystemClock.elapsedRealtime());
        pauseOffset = 0;
        running = false;
        startPauseButton.setText("START");
        resetButton.setVisibility(View.INVISIBLE);
        chronometerView = null;
    }
}
