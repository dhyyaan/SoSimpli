/*
package com.think360.sosimpli.widgets;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.think360.sosimpli.ui.fragments.ProfileFragment;

import java.util.Calendar;

*/
/**
 * Created by surinder on 24-Jul-17.
 *//*


public class SelectDateFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener {

    private EditText etDateAvailability;

    @SuppressLint("ValidFragment")
    public SelectDateFragment(EditText etDateAvailability) {
        this.etDateAvailability = etDateAvailability;
    }

    public SelectDateFragment() {
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final Calendar calendar = Calendar.getInstance();
        int yy = calendar.get(Calendar.YEAR);
        int mm = calendar.get(Calendar.MONTH);
        int dd = calendar.get(Calendar.DAY_OF_MONTH);
        return new DatePickerDialog(getActivity(), this, yy, mm, dd);
    }

    public void onDateSet(DatePicker view, int yy, int mm, int dd) {
        populateSetDate(yy, mm, dd);
    }

    public void populateSetDate(int year, int month, int day) {
        Calendar calendarToday = Calendar.getInstance();
        Calendar calendarSelected = Calendar.getInstance();
        calendarSelected.set(Calendar.YEAR, year);
        calendarSelected.set(Calendar.MONTH, month);
        calendarSelected.set(Calendar.DAY_OF_MONTH, day);

        if (calendarSelected.before(calendarToday)) {
            etDateAvailability.setText("");
            Toast.makeText(getActivity(), "You can't set Event Date in past.", Toast.LENGTH_SHORT).show();
        } else {
            month = month + 1;
            etDateAvailability.setText(year + "/" + convertTo(month) + "/" + convertTo(day));
        }


    }

    private String convertTo(int day) {
        String result;
        if (day < 10) {
            result = "0" + day;
        } else {
            result = String.valueOf(day);
        }
        return result;

    }
}

*/
