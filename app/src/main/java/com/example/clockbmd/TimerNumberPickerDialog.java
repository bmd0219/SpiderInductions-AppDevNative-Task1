package com.example.clockbmd;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.NumberPicker;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

public class TimerNumberPickerDialog extends DialogFragment {

    private NumberPicker secondsPicker;
    private NumberPicker minutesPicker;
    private int minutesValue;
    private int secondsValue;
    private OnDurationPickedListener listener;

    public interface OnDurationPickedListener {
        void onDurationPicked(int minutes, int seconds);
    }

    public static TimerNumberPickerDialog onCreateInstance (long duration) {
        TimerNumberPickerDialog timerNumberPickerDialog = new TimerNumberPickerDialog();
        Bundle bundle = new Bundle();
        bundle.putInt("minutesValue", (int) duration / 60000);
        bundle.putInt("secondsValue", (int) (duration / 1000) % 60);
        timerNumberPickerDialog.setArguments(bundle);

        return timerNumberPickerDialog;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getTargetFragment().getContext());

        LayoutInflater inflater = getTargetFragment().getLayoutInflater();
        View v = inflater.inflate(R.layout.timer_time_picker_dialog, null);

        minutesPicker = v.findViewById(R.id.minutes_picker);
        secondsPicker = v.findViewById(R.id.seconds_picker);

        if(getArguments() != null) {
            minutesValue = getArguments().getInt("minutesValue");
            secondsValue = getArguments().getInt("secondsValue");
        }

        minutesPicker.setMaxValue(59);
        minutesPicker.setMinValue(0);
        minutesPicker.setValue(minutesValue);
        secondsPicker.setMinValue(0);
        secondsPicker.setMaxValue(59);
        secondsPicker.setValue(secondsValue);

        builder.setView(v)
                .setTitle("Set Time")
                .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                })
                .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        listener.onDurationPicked(minutesPicker.getValue(), secondsPicker.getValue());
                    }
                });

        return builder.create();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        if (getTargetFragment() instanceof OnDurationPickedListener) {
            listener = (OnDurationPickedListener) getTargetFragment();
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnDurationPickedListener");
        }
        super.onAttach(context);
    }
}
