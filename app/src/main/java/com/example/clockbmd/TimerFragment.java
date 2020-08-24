package com.example.clockbmd;

import android.content.Context;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import java.util.Locale;

public class TimerFragment extends Fragment implements TimerNumberPickerDialog.OnDurationPickedListener {

    public static final String TAG = "TimerFragment";

    private Long START_TIME_IN_MILLIS = 600000L;
    private Long timeLeftInMillis = START_TIME_IN_MILLIS;
    private boolean timerRunning = false;
    private CountDownTimer countDownTimer;
    private TextView timerTextView;
    private Button timerStartPauseButton;
    private Button timerEditButton;
    private Button timerResetButton;

    private FragmentActivity myContext;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.timer_fragment, container, false);

        Log.println(Log.ASSERT, TAG, "onCreateView");

        timerTextView = v.findViewById(R.id.timer_text_view);
        timerEditButton = v.findViewById(R.id.edit_timer);
        timerStartPauseButton = v.findViewById(R.id.start_pause_timer);
        timerResetButton = v.findViewById(R.id.reset_timer);

        timerStartPauseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (timerRunning) {
                    pauseTimer();
                } else {
                    startTimer();
                }
            }
        });

        timerResetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resetTimer();
            }
        });

        timerEditButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editTimer();
            }
        });

        updateTimerText(timeLeftInMillis);

        return v;
    }

    private void startTimer() {
        if (timeLeftInMillis != 0) {
            countDownTimer = new CountDownTimer(timeLeftInMillis, 1000) {
                @Override
                public void onTick(long l) {
                    timeLeftInMillis = l;
                    updateTimerText(timeLeftInMillis);
                }

                @Override
                public void onFinish() {
                    timeLeftInMillis = 0L;
                    updateTimerText(timeLeftInMillis);
                    timerRunning = false;
                    timerStartPauseButton.setText("START");
                    timerStartPauseButton.setVisibility(View.INVISIBLE);
                    timerResetButton.setVisibility(View.VISIBLE);
                }
            }.start();

            timerRunning = true;
            timerEditButton.setVisibility(View.INVISIBLE);
            timerResetButton.setVisibility(View.INVISIBLE);
            timerStartPauseButton.setText("PAUSE");
        } else {
            Toast.makeText(getActivity(), "PLEASE SET A DURATION", Toast.LENGTH_SHORT).show();
        }
    }

    private void pauseTimer() {
        countDownTimer.cancel();
        timerRunning = false;
        timerResetButton.setVisibility(View.VISIBLE);
        timerStartPauseButton.setText("RESUME");
    }

    private void resetTimer() {
        timeLeftInMillis = START_TIME_IN_MILLIS;
        updateTimerText(timeLeftInMillis);
        timerResetButton.setVisibility(View.INVISIBLE);
        timerEditButton.setVisibility(View.VISIBLE);
        timerStartPauseButton.setVisibility(View.VISIBLE);
        timerStartPauseButton.setText("START");
    }

    private void editTimer() {
        TimerNumberPickerDialog timerNumberPickerDialog = TimerNumberPickerDialog.onCreateInstance(START_TIME_IN_MILLIS);
        timerNumberPickerDialog.setTargetFragment(TimerFragment.this, 2);
        timerNumberPickerDialog.show(myContext.getSupportFragmentManager(), "timeNumberPicker");
    }

    private void updateTimerText(long timeLeft) {
        int minutes = (int) (timeLeft / 1000) / 60;
        int seconds = (int) (timeLeft / 1000) % 60;
        String timeLeftFormatted = String.format(Locale.getDefault(), "%02d:%02d", minutes, seconds);
        timerTextView.setText(timeLeftFormatted);
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.println(Log.ASSERT, TAG, "onSaveInstanceState");
    }

    @Override
    public void onAttach(@NonNull Context context) {
        myContext = (FragmentActivity) context;
        super.onAttach(context);
        Log.println(Log.ASSERT, TAG, "onAttach");
    }

    @Override
    public void onDurationPicked(int minutes, int seconds) {
        START_TIME_IN_MILLIS = (long) (minutes * 60000 + seconds * 1000);
        timeLeftInMillis = START_TIME_IN_MILLIS;
        updateTimerText(timeLeftInMillis);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.println(Log.ASSERT, TAG, "onCreate");
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
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.println(Log.ASSERT, TAG, "onActivityCreated");
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
