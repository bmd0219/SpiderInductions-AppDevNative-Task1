package com.example.clockbmd;

import android.app.TimePickerDialog;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.TimePicker;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.Objects;

public class AlarmFragment extends Fragment implements TimePickerDialog.OnTimeSetListener {

    public static final String TAG = "AlarmFragment.class";

    private TextView alarmTextView;
    private FragmentActivity myContext;
    private AlarmFragmentListener listener;
    private String alarmTextViewString;
    private Calendar c;

    public interface AlarmFragmentListener {
        void startAlarm(Calendar c);
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putString("alarmTextView", alarmTextViewString);
        super.onSaveInstanceState(outState);
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.alarm_fragment,container, false);

        Log.println(Log.ASSERT, TAG, "onCreateView");

        alarmTextView = v.findViewById(R.id.alarm_textView);
        Button setAlarmButton = v.findViewById(R.id.set_alarm_button);

        if(savedInstanceState != null) {
            alarmTextViewString = savedInstanceState.getString("alarmTextView");
            if(alarmTextViewString != null) {
                alarmTextView.setText(alarmTextViewString);
            }
        }

        setAlarmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogFragment timePicker = new TimePickerFragment();
                timePicker.setTargetFragment(AlarmFragment.this, 1);
                timePicker.show(myContext.getSupportFragmentManager(), "time picker");
                }
            });
        return v;
    }

    @Override
    public void onTimeSet(TimePicker timePicker, int i, int i1) {
        c = Calendar.getInstance();
        c.set(Calendar.HOUR_OF_DAY, i);
        c.set(Calendar.MINUTE, i1);
        c.set(Calendar.SECOND, 0);
        updateTimeText(c);
        listener.startAlarm(c);
    }

    private void updateTimeText(Calendar c) {
        String timeText = "Alarm set for: ";
        timeText += DateFormat.getTimeInstance(DateFormat.SHORT).format(c.getTime());
        alarmTextView.setText(timeText);
        alarmTextViewString = timeText;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        myContext=(FragmentActivity) context;
        if (context instanceof AlarmFragmentListener) {
            listener = (AlarmFragmentListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement AlarmFragmentListener");
        }
        super.onAttach(context);
        Log.println(Log.ASSERT, TAG, "onAttach");
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


