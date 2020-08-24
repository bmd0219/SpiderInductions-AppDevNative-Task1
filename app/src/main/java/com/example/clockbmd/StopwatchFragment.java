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

public class StopwatchFragment extends Fragment {

    public static final String TAG = "StopWatchFragment";

    private Chronometer chronometer;
    private TextView chronometerView;
    private Button startPauseButton;
    private Button resetButton;
    private long pauseOffset;
    private boolean running, wasReset;

//    @Override
//    public void onSaveInstanceState(@NonNull Bundle outState) {
//
//        super.onSaveInstanceState(outState);
//        Log.println(Log.ASSERT, TAG, "OnSaveInstanceState" + pauseOffset);
//        Log.println(Log.ASSERT, TAG, "OnSaveInstanceState" + running);
//        Log.println(Log.ASSERT, TAG, "OnSaveInstanceState" + wasReset);
//        Log.println(Log.ASSERT, TAG, "------------------------------------------------------");
////        if(!wasReset && pauseOffset == 0) {
////            pauseOffset = SystemClock.elapsedRealtime() - chronometer.getBase();
////        }
//        outState.putLong("pauseOffset", pauseOffset);
//        outState.putBoolean("running", running);
//        outState.putBoolean("wasReset", wasReset);
//    }



    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.println(Log.ASSERT, TAG, "onActivityCreated");

        if(savedInstanceState != null) {

//            pauseOffset = savedInstanceState.getLong("pauseOffset");
//            running = savedInstanceState.getBoolean("running");
//            wasReset = savedInstanceState.getBoolean("wasReset");

//            Log.println(Log.ASSERT, TAG, "OnActivityCreated" + pauseOffset);
//            Log.println(Log.ASSERT, TAG, "OnActivityCreated" + running);
//            Log.println(Log.ASSERT, TAG, "OnActivityCreated" + wasReset);
//            Log.println(Log.ASSERT, TAG, "------------------------------------------------------");

//            if(pauseOffset != 0) {
//                startChronometer();
//            }
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.println(Log.ASSERT, TAG, "onCreate");
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.stopwatch_fragment,container, false);

        Log.println(Log.ASSERT, TAG, "onCreateView");

//        chronometer = new Chronometer(getContext());

        chronometer = v.findViewById(R.id.chronometer_view);
        startPauseButton = v.findViewById(R.id.start_pause_chronometer);
        resetButton = v.findViewById(R.id.reset_chronometer);

        startPauseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startChronometer();
            }
        });

//        chronometer.setOnChronometerTickListener(new Chronometer.OnChronometerTickListener() {
//            @Override
//            public void onChronometerTick(Chronometer chronometer) {
//                int minutes = (int) chronometer.getBase() / 60000;
//                int seconds = (int) chronometer.getBase() / 1000 % 60;
//                chronometerView.setText(minutes + ":" + seconds);
//            }
//        });

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
        wasReset = false;
    }

    public void resetChronometer() {
        chronometer.stop();
        chronometer.setBase(SystemClock.elapsedRealtime());
        pauseOffset = 0;
        running = false;
        startPauseButton.setText("START");
        resetButton.setVisibility(View.INVISIBLE);
        wasReset = true;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        Log.println(Log.ASSERT, TAG, "onAttach");
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.println(Log.ASSERT, TAG, "onStart");
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.println(Log.ASSERT, TAG, "onResume");
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.println(Log.ASSERT, TAG, "onPause");
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.println(Log.ASSERT, TAG, "onStop");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.println(Log.ASSERT, TAG, "onDestroyView");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.println(Log.ASSERT, TAG, "onDestroy");
    }

    @Override
    public void onDetach() {
        super.onDetach();
        Log.println(Log.ASSERT, TAG, "onDetach");
    }
}
