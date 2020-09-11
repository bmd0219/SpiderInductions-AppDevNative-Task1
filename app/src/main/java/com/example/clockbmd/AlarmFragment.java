package com.example.clockbmd;

import android.app.TimePickerDialog;
import android.content.Context;
import android.content.SharedPreferences;
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
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import java.text.DateFormat;
import java.util.Calendar;

public class AlarmFragment extends Fragment implements TimePickerDialog.OnTimeSetListener {

    public static final String TAG = "AlarmFragment.class";

    private TextView alarmTextView;
    private FragmentActivity myContext;
    private AlarmFragmentListener listener;
    private String alarmTextViewString;
    public static final String SHARED_PREFS = "alarmSharedPrefs";

    public interface AlarmFragmentListener {
        void startAlarm(Calendar c);
        void stopAlarm();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.alarm_fragment,container, false);

        Log.println(Log.ASSERT, TAG, "onCreateView");

        alarmTextView = v.findViewById(R.id.alarm_textView);

        SharedPreferences sharedPreferences = myContext.getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE);
        if(sharedPreferences.contains("hours") && sharedPreferences.contains("minutes")) {
            alarmTextView.setText("Alarm is set for: " + sharedPreferences.getInt("hours", Calendar.getInstance().get(Calendar.HOUR_OF_DAY)) + ":" + sharedPreferences.getInt("minutes", Calendar.getInstance().get(Calendar.MINUTE)));
        }

        if(alarmTextViewString != null) {
            alarmTextView.setText(alarmTextViewString);
        }

        Button setAlarmButton = v.findViewById(R.id.set_alarm_button);
        Button deleteAlarmButton = v.findViewById(R.id.delete_alarm_button);

        setAlarmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogFragment timePicker = new TimePickerFragment();
                timePicker.setTargetFragment(AlarmFragment.this, 1);
                timePicker.show(myContext.getSupportFragmentManager(), "time picker");
                }
            });

        deleteAlarmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alarmTextViewString = null;
                alarmTextView.setText("No Alarm has been set");
                listener.stopAlarm();
            }
        });
        return v;
    }

    @Override
    public void onTimeSet(TimePicker timePicker, int i, int i1) {
        Calendar c = Calendar.getInstance();
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
}


