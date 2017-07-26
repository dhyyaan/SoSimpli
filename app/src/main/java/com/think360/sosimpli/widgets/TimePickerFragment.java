/*
package com.think360.sosimpli.widgets;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.text.format.DateFormat;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import com.think360.sosimpli.R;

import java.util.Calendar;

*/
/**
 * Created by surinder on 24-Jul-17.
 *//*


public class TimePickerFragment extends DialogFragment
        implements TimePickerDialog.OnTimeSetListener {

    public EditText et;

    public void TimePickerFragment(EditText view) {
        this.et = view;
    }

    public TimePickerFragment() {
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the current time as the default values for the picker
        final Calendar c = Calendar.getInstance();
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int minute = c.get(Calendar.MINUTE);

        // Create a new instance of TimePickerDialog and return it
        return new TimePickerDialog(getActivity(), this, hour, minute,
                DateFormat.is24HourFormat(getActivity()));
    }

    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        // Do something with the time chosen by the user.

        String status = "AM";

        if (hourOfDay > 11) {
            // If the hour is greater than or equal to 12
            // Then the current AM PM status is PM
            status = "PM";
        }
        // Initialize a new variable to hold 12 hour format hour value
        int hour_of_12_hour_format;

        if (hourOfDay > 11) {
            if (hourOfDay == 12) {
                hour_of_12_hour_format = 12;
            } else {
                // If the hour is greater than or equal to 12
                // Then we subtract 12 from the hour to make it 12 hour format time
                hour_of_12_hour_format = hourOfDay - 12;
            }

        } else {
            hour_of_12_hour_format = hourOfDay;
        }
        if (et.getId() == R.id.etStartTime) {
            startTime = convertTo(hourOfDay) + ":" + convertTo(minute) + ":00";
            startTimeCalendar = Calendar.getInstance();
            startTimeCalendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
            startTimeCalendar.set(Calendar.MINUTE, minute);

            et.setText(convertTo(hour_of_12_hour_format) + ":" + convertTo(minute) + " " + status);
        }
        if (et.getId() == R.id.etEndTime) {
            endTime = convertTo(hourOfDay) + ":" + convertTo(minute) + ":00";
            endTimeCalendar = Calendar.getInstance();
            endTimeCalendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
            endTimeCalendar.set(Calendar.MINUTE, minute);
            if (startTimeCalendar.compareTo(endTimeCalendar) > 0) {
                et.setText("");

                Toast.makeText(getActivity(), "End Time should be after start time", Toast.LENGTH_SHORT).show();

            } else {

                et.setText(convertTo(hour_of_12_hour_format) + ":" + convertTo(minute) + " " + status);
            }
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
