package com.bignerdranch.android.criminalintent;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TimePicker;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * TODO:Challenge: More Dialogs
 * Write another dialog fragment named TimePickerFragment that allows the user to select
 * what time of day the crime occurred using a TimePicker widget.
 * Add another button to CrimeFragment to display a TimePickerFragment.
 */
public class TimePickerFragment extends DialogFragment {
    public static final String EXTRA_TIME =
            "com.bignerdranch.android.criminalintent.time";
    private static final String ARG_TIME = "time";
    private TimePicker mTimePicker;

    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M){
            return null;
        }
        Date date = (Date) getArguments().getSerializable(ARG_TIME);

        final Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);

        View v = LayoutInflater.from(getActivity())
                .inflate(R.layout.dialog_time, null);

        mTimePicker = (TimePicker) v.findViewById(R.id.dialog_timePicker);

        return new AlertDialog.Builder(getActivity())
                .setView(v)
                .setTitle(R.string.time_picker_title)
                .setPositiveButton(android.R.string.ok,
                        new DialogInterface.OnClickListener() {
                            @RequiresApi(api = Build.VERSION_CODES.M)
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                int hour = mTimePicker.getHour();
                                int minute = mTimePicker.getMinute();
                                Date date = new GregorianCalendar(
                                        calendar.get(Calendar.YEAR),
                                        calendar.get(Calendar.MONTH),
                                        calendar.get(Calendar.DAY_OF_MONTH),
                                        hour,
                                        minute).getTime();
                                sendResult(Activity.RESULT_OK, date);
                            }
                        })
                .create();
    }

    public static TimePickerFragment newInstance(Date date) {
        Bundle args = new Bundle();
        args.putSerializable(ARG_TIME, date);

        TimePickerFragment fragment = new TimePickerFragment();
        fragment.setArguments(args);
        return fragment;
    }

    private void sendResult(int resultCode, Date date) {
        if (getTargetFragment() == null) {
            return;
        }

        Intent intent = new Intent();
        intent.putExtra(EXTRA_TIME, date);

        getTargetFragment()
                .onActivityResult(getTargetRequestCode(), resultCode, intent);
    }
}
