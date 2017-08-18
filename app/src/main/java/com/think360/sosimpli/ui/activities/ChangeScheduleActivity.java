package com.think360.sosimpli.ui.activities;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.widget.Toolbar;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.think360.sosimpli.AppController;
import com.think360.sosimpli.R;
import com.think360.sosimpli.manager.ApiService;
import com.think360.sosimpli.model.Zone;
import com.think360.sosimpli.model.availability.AvailabilityResponse;
import com.think360.sosimpli.model.city.CityResponse;
import com.think360.sosimpli.model.country.Country;
import com.think360.sosimpli.model.country.CountryResponse;
import com.think360.sosimpli.model.states.StateResponse;
import com.think360.sosimpli.ui.fragments.CountriesDialogFragment;
import com.think360.sosimpli.utils.AddAvailbailtyChanged;
import com.think360.sosimpli.utils.AppConstants;

import java.util.AbstractList;
import java.util.ArrayList;
import java.util.Calendar;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class ChangeScheduleActivity extends BaseActivity implements CountriesDialogFragment.CallBack {


    private static String startTime, endTime, startDate;
    private static Calendar startTimeCalendar, endTimeCalendar;
    private static Calendar startDateCalendar;
    private String country_id, state_id, city_id, zones;


    @Inject
    ApiService apiService;

    @BindView(R.id.etCountry)
    EditText etCountry;

    @BindView(R.id.progressBarStates)
    ProgressBar progressBarStates;

    @BindView(R.id.etStates)
    EditText etStates;

    @BindView(R.id.progressBarCountry)
    ProgressBar progressBarCountry;

    @BindView(R.id.etCity)
    EditText etCity;

    @BindView(R.id.editZone)
    EditText editZone;

    @BindView(R.id.progressBarCity)
    ProgressBar progressBarCity;


    @BindView(R.id.etDateAvailability)
    EditText etDateAvailability;

    @BindView(R.id.etStartTime)
    EditText etStartTime;

    @BindView(R.id.etEndTime)
    EditText etEndTime;

    private CountriesDialogFragment countriesDialogFragment;
    private CountriesDialogFragment statesDialogFragment;
    private CountriesDialogFragment cityDialogFragment;
    private CountriesDialogFragment zoneDialogFragment;

    @BindView(R.id.toolbar)
    Toolbar toolbar;


    @BindView(R.id.updateAvailability)
    TextView updateAvailability;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.change_schedule_activity);
        ButterKnife.bind(this);
        ((AppController) getApplication()).getComponent().inject(this);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Update Availability");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);


        progressBarCountry.setIndeterminate(true);
        progressBarCountry.setVisibility(View.VISIBLE);
        etCountry.setClickable(false);

        apiService.getCountries().enqueue(new Callback<CountryResponse>() {
            @Override
            public void onResponse(Call<CountryResponse> call, Response<CountryResponse> response) {
                if (response.isSuccessful() && response.body().getStatus()) {
                    progressBarCountry.setVisibility(View.GONE);
                    etCountry.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_down, 0);
                    etCountry.setClickable(true);
                    countriesDialogFragment = CountriesDialogFragment.newInstance();
                    countriesDialogFragment.setList((AbstractList) response.body().getData().getCountry(), ChangeScheduleActivity.this);
                    etCountry.setClickable(true);
                } else {
                    showSimpleDialog(ChangeScheduleActivity.this, response.body().getMessage());
                }
            }

            @Override
            public void onFailure(Call<CountryResponse> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

 /*   @OnClick(R.id.updateAvailability)
    public void onClickUpdateAvailability() {
        Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialog_send_request);

        ((TextView) dialog.findViewById(R.id.text)).setText("Your mentioned availability updated successfully ");
        dialog.show();
        animatemy(dialog.findViewById(R.id.imageView));

    }*/


    @OnClick(R.id.etDateAvailability)
    public void onClickDateAvailability() {
        DialogFragment newFragment1 = new AddAvailabilityActivity.SelectDateFragment(etDateAvailability);
        newFragment1.show(getSupportFragmentManager(), "DatePicker");
    }


    @OnClick(R.id.etStartTime)
    public void onClickStartTime() {
        DialogFragment newFragment1 = new AddAvailabilityActivity.TimePickerFragment(etStartTime);
        newFragment1.show(getSupportFragmentManager(), "TimePicker");
    }


    @OnClick(R.id.etEndTime)
    public void onClickEndTime() {
        DialogFragment newFragment1 = new AddAvailabilityActivity.TimePickerFragment(etEndTime);
        newFragment1.show(getSupportFragmentManager(), "TimePicker");
    }

    @OnClick(R.id.updateAvailability)
    public void onClickAddAvailability() {
        if (startDateCalendar != null && startTimeCalendar != null && endTimeCalendar != null) {
            final Dialog dialog = new Dialog(ChangeScheduleActivity.this);
            dialog.setContentView(R.layout.dialog_send_request);
            ((ProgressBar) dialog.findViewById(R.id.progressbar)).setIndeterminate(true);
            (dialog.findViewById(R.id.progressbar)).setVisibility(View.VISIBLE);
            ((TextView) dialog.findViewById(R.id.text)).setText("Sending Request..");
            dialog.show();
            apiService.addAvailability(AppController.sharedPreferencesCompat.getInt(AppConstants.DRIVER_ID, 0), startDate, startTime, endTime, country_id, state_id, city_id, zones)
                    // apiService.addAvailability(AppController.sharedPreferencesCompat.getInt(AppConstants.DRIVER_ID, 0), String.valueOf(startDateCalendar.getTimeInMillis() / 1000L), String.valueOf(startTimeCalendar.getTimeInMillis() / 1000L), String.valueOf(endTimeCalendar.getTimeInMillis() / 1000L), country_id, state_id, city_id, "4")
                    .enqueue(new Callback<AvailabilityResponse>() {
                        @Override
                        public void onResponse(Call<AvailabilityResponse> call, Response<AvailabilityResponse> response) {

                            if (response.body().getStatus()) {
                                ((AppController) getApplication()).bus().send(new AddAvailbailtyChanged(response.body()));
                                (dialog.findViewById(R.id.progressbar)).setVisibility(View.GONE);
                                (dialog.findViewById(R.id.imageView)).setVisibility(View.VISIBLE);
                                ((TextView) dialog.findViewById(R.id.text)).setText(response.body().getMessage());
                                animatemy(dialog.findViewById(R.id.imageView));
                                new Handler().postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        onBackPressed();
                                    }
                                }, 100);
                            } else {
                                dialog.dismiss();
                                Toast.makeText(ChangeScheduleActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<AvailabilityResponse> call, Throwable t) {
                            dialog.dismiss();
                        }
                    });
        } else {
            Toast.makeText(this, "Please select Time/Date correctly", Toast.LENGTH_SHORT).show();
        }
    }


    @OnClick(R.id.etCountry)
    public void onClickCountry() {
        if (countriesDialogFragment != null)
            countriesDialogFragment.show(getSupportFragmentManager(), "");
    }

    @OnClick(R.id.etStates)
    public void onClickStates() {
        if (statesDialogFragment != null)
            statesDialogFragment.show(getSupportFragmentManager(), "");
    }

    @OnClick(R.id.etCity)
    public void onClickCity() {
        if (cityDialogFragment != null)
            cityDialogFragment.show(getSupportFragmentManager(), "");
    }

    @OnClick(R.id.editZone)
    public void onClickZone() {
        if (cityDialogFragment != null) {
            zoneDialogFragment = CountriesDialogFragment.newInstance();
            AbstractList<Zone> abstractList = new ArrayList();
            abstractList.add(new Zone(0, "NORTH ZONE"));
            abstractList.add(new Zone(1, "WEST ZONE"));
            abstractList.add(new Zone(2, "EAST ZONE"));
            abstractList.add(new Zone(3, "SOUTH ZONE"));
            zoneDialogFragment.setList(abstractList, ChangeScheduleActivity.this);
            zoneDialogFragment.show(getSupportFragmentManager(), "");
        }

    }


    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Override
    public void callBackMethod(Object object) {
        if (object instanceof Country) {
            progressBarStates.setVisibility(View.VISIBLE);
            etCountry.setText(((Country) object).getCountryName());
            country_id = ((Country) object).getId();

            state_id = "";
            etStates.setText("");
            etStates.setHint("SELECT STATE");
            statesDialogFragment = null;

            city_id = "";
            etCity.setText("");
            etCity.setHint("SELECT CITY");
            cityDialogFragment = null;

            apiService.getStates(((Country) object).getId() + "", "").enqueue(new Callback<StateResponse>() {
                @Override
                public void onResponse(Call<StateResponse> call, Response<StateResponse> response) {
                    if (response.isSuccessful() && response.body().getStatus()) {
                        progressBarStates.setVisibility(View.GONE);
                        etStates.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_down, 0);
                        etStates.setClickable(true);
                        statesDialogFragment = CountriesDialogFragment.newInstance();
                        statesDialogFragment.setList((AbstractList) response.body().getData().getState(), ChangeScheduleActivity.this);
                    } else {
                        showSimpleDialog(ChangeScheduleActivity.this, response.body().getMessage());
                        progressBarStates.setVisibility(View.GONE);
                        etStates.setText("No State Available");
                    }
                }

                @Override
                public void onFailure(Call<StateResponse> call, Throwable t) {
                    etCity.setClickable(false);
                    t.printStackTrace();
                }
            });
        }
        if (object instanceof StateResponse.State) {
            etStates.setText(((StateResponse.State) object).getName());
            state_id = ((StateResponse.State) object).getId();

            city_id = "";
            etCity.setText("");
            etCity.setHint("SELECT CITY");
            cityDialogFragment = null;

            progressBarCity.setVisibility(View.VISIBLE);
            apiService.getCity("", ((StateResponse.State) object).getId() + "").enqueue(new Callback<CityResponse>() {
                @Override
                public void onResponse(Call<CityResponse> call, Response<CityResponse> response) {
                    if (response.isSuccessful() && response.body().getStatus()) {
                        progressBarCity.setVisibility(View.GONE);
                        etCity.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_down, 0);
                        etCity.setClickable(true);
                        cityDialogFragment = CountriesDialogFragment.newInstance();
                        cityDialogFragment.setList((AbstractList) response.body().getData().getCity(), ChangeScheduleActivity.this);
                    } else {
                        showSimpleDialog(ChangeScheduleActivity.this, response.body().getMessage());
                        etCity.setText("No City Available");
                        city_id = "";
                        cityDialogFragment = null;
                        progressBarCity.setVisibility(View.GONE);
                    }
                }

                @Override
                public void onFailure(Call<CityResponse> call, Throwable t) {
                    etStates.setClickable(false);
                    t.printStackTrace();
                }
            });
        }
        if (object instanceof CityResponse.City) {
            etCity.setText(((CityResponse.City) object).getName());
            city_id = ((CityResponse.City) object).getId();
            progressBarCity.setVisibility(View.GONE);
            etCity.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_down, 0);
            etCity.setClickable(true);
        }

        if (object instanceof Zone) {
            editZone.setText(((Zone) object).getZoneName());
            zones = ((Zone) object).getZoneName();
        }
    }

    public static class TimePickerFragment extends DialogFragment implements TimePickerDialog.OnTimeSetListener {

        public EditText et;

        @SuppressLint("ValidFragment")
        public TimePickerFragment(EditText view) {
            this.et = view;
        }

        public TimePickerFragment() {
        }

        @NonNull
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
                startTime = convertTo(hour_of_12_hour_format) + ":" + convertTo(minute) + " " + status;
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
                    endTime = convertTo(hour_of_12_hour_format) + ":" + convertTo(minute) + " " + status;
                }
            }
        }
    }

    public static class SelectDateFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener {

        private EditText etDateAvailability;

        @SuppressLint("ValidFragment")
        public SelectDateFragment(EditText etDateAvailability) {
            this.etDateAvailability = etDateAvailability;
        }

        public SelectDateFragment() {
        }

        @NonNull
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
                startDateCalendar = calendarSelected;
                month = month + 1;
                etDateAvailability.setText(year + "/" + convertTo(month) + "/" + convertTo(day));
                startDate = etDateAvailability.getText().toString();
            }
        }

    }

    public static String convertTo(int day) {
        String result;
        if (day < 10) {
            result = "0" + day;
        } else {
            result = String.valueOf(day);
        }
        return result;

    }
}
