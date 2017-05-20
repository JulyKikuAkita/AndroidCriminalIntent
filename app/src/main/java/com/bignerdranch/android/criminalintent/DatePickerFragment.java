package com.bignerdranch.android.criminalintent;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.DatePicker;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * TODO: Challenge: A Responsive DialogFragment
 * For a more involved challenge, modify the presentation of the DatePickerFragment.
 * The first stage of this challenge is to supply the DatePickerFragment’s view
 * by overriding onCreateView(...) instead of onCreateDialog(Bundle).
 * When setting up a DialogFragment in this way, your dialog will not be presented with the
 * built-in title area and button area on the top and bottom of the dialog.
 * You will need to create your own OK button in dialog_date.xml.
 * OnceDatePickerFragment’s view is created inonCreateView(...),
 * you can presentDatePickerFragment as a dialog or embedded in an activity.
 * For the second stage of this challenge, create a new subclass of SingleFragmentActivity and host
 * DatePickerFragment in that activity.
 * When presenting DatePickerFragment in this way, you will use the startActivityForResult(...)
 * mechanism to pass the date back to CrimeFragment. In DatePickerFragment, if the target fragment
 * does not exist, use the setResult(int, intent) method on the hosting activity to send the date
 * back to the fragment.
 * For the final step of this challenge, modify CriminalIntent to present the DatePickerFragment
 * as a full-screen activity when running on a phone. When running on a tablet, present the
 * DatePickerFragment as a dialog. You may need to read ahead in Chapter 17 for details on how to
 * optimize your app for multiple screen sizes.
 */
public class DatePickerFragment extends DialogFragment {
    public static final String EXTRA_DATE =
            "com.bignerdranch.android.criminalintent.date";
    private static final String ARG_DATE = "date";
    private DatePicker mDatePicker;

    public static DatePickerFragment newInstance(Date date) {
        Bundle args = new Bundle();
        args.putSerializable(ARG_DATE, date);

        DatePickerFragment fragment = new DatePickerFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Date date = (Date) getArguments().getSerializable(ARG_DATE);

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        View v = LayoutInflater.from(getActivity())
                .inflate(R.layout.dialog_date, null);
        mDatePicker = (DatePicker) v.findViewById(R.id.dialog_date_picker);
        mDatePicker.init(year, month, day, null);

        return new AlertDialog.Builder(getActivity())
                .setView(v)
                .setTitle(R.string.date_picker_title)
                .setPositiveButton(android.R.string.ok,
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                int year = mDatePicker.getYear();
                                int month = mDatePicker.getMonth();
                                int day = mDatePicker.getDayOfMonth();
                                Date date = new GregorianCalendar(year, month, day).getTime();
                                sendResult(Activity.RESULT_OK, date);
                            }
                        })
                .create();
    }

    private void sendResult(int resultCode, Date date) {
        if (getTargetFragment() == null) {
            return;
        }

        Intent intent = new Intent();
        intent.putExtra(EXTRA_DATE, date);

        getTargetFragment()
                .onActivityResult(getTargetRequestCode(), resultCode, intent);
    }
}
