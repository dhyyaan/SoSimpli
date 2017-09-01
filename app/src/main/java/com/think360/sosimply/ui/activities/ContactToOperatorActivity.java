package com.think360.sosimply.ui.activities;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.think360.sosimply.AppController;
import com.think360.sosimply.R;
import com.think360.sosimply.manager.ApiService;
import com.think360.sosimply.model.schedule.pending.Datum;
import com.think360.sosimply.model.schedule.pending.PedningScheduleResponse;
import com.think360.sosimply.model.trip.TripFinishStartResponse;
import com.think360.sosimply.ui.fragments.ChooserDialogFragment;

import java.util.AbstractList;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class ContactToOperatorActivity extends BaseActivity implements ChooserDialogFragment.CallBack {
    @Inject
    ApiService apiService;

    @BindView(R.id.tvSendRequest)
    TextView tvSendRequest;

    @BindView(R.id.toolbar)
    Toolbar toolbar;


    @BindView(R.id.etDescription)
    EditText etDescription;

    @BindView(R.id.etSubject)
    EditText etSubject;

    @BindView(R.id.etCountry)
    EditText etScheduleName;

    @BindView(R.id.progressBarCountry)
    ProgressBar progressBarCountry;

    private ChooserDialogFragment chooserDialogFragment;
    private String etScheduleId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((AppController) getApplication()).getComponent().inject(this);
        setContentView(R.layout.contact_to_operator);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Contact To Operator");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        progressBarCountry.setIndeterminate(true);
        progressBarCountry.setVisibility(View.VISIBLE);
        etScheduleName.setClickable(false);

        apiService.getPendingSchedule(driverId).enqueue(new Callback<PedningScheduleResponse>() {
            @Override
            public void onResponse(Call<PedningScheduleResponse> call, Response<PedningScheduleResponse> response) {
                if (response.body().getStatus() && response.isSuccessful()) {
                    progressBarCountry.setVisibility(View.GONE);
                    etScheduleName.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_down, 0);
                    etScheduleName.setClickable(true);
                    chooserDialogFragment = ChooserDialogFragment.newInstance();
                    chooserDialogFragment.setList((AbstractList) response.body().getData(), ContactToOperatorActivity.this);

                    etScheduleName.setClickable(true);
                } else {
                    progressBarCountry.setVisibility(View.GONE);
                    Toast.makeText(ContactToOperatorActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onFailure(Call<PedningScheduleResponse> call, Throwable t) {
                Toast.makeText(ContactToOperatorActivity.this, t.getStackTrace() + "", Toast.LENGTH_SHORT).show();

                progressBarCountry.setVisibility(View.GONE);
            }
        });


    }

    @OnClick(R.id.etCountry)
    public void onClickCountry() {
        if (chooserDialogFragment != null)
            chooserDialogFragment.show(getSupportFragmentManager(), "");
    }


    @OnClick(R.id.tvSendRequest)
    public void onClickSendRequest() {
        if (TextUtils.isEmpty(etScheduleId) || TextUtils.isEmpty(etSubject.getText().toString()) || TextUtils.isEmpty(etSubject.getText().toString())) {
            Toast.makeText(ContactToOperatorActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();

        } else {
            final Dialog dialog = new Dialog(this);
            dialog.setContentView(R.layout.dialog_send_request);
            ((ProgressBar) dialog.findViewById(R.id.progressbar)).setIndeterminate(true);
            (dialog.findViewById(R.id.progressbar)).setVisibility(View.VISIBLE);
            ((TextView) dialog.findViewById(R.id.text)).setText("Sending Request..");
            dialog.show();

            apiService.contactToOperator(driverId, "1", etSubject.getText().toString(), etDescription.getText().toString()).enqueue(new Callback<TripFinishStartResponse>() {
                @Override
                public void onResponse(Call<TripFinishStartResponse> call, Response<TripFinishStartResponse> response) {
                    dialog.dismiss();
                    if (response.body().getStatus()) {
                        (dialog.findViewById(R.id.progressbar)).setVisibility(View.GONE);
                        (dialog.findViewById(R.id.imageView)).setVisibility(View.VISIBLE);
                        ((TextView) dialog.findViewById(R.id.text)).setText(response.body().getMessage());
                        animatemy(dialog.findViewById(R.id.imageView));
                        dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                            @Override
                            public void onDismiss(DialogInterface dialog) {
                                onBackPressed();
                            }
                        });

                    } else {
                        dialog.dismiss();
                        Toast.makeText(ContactToOperatorActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();

                    }
                }

                @Override
                public void onFailure(Call<TripFinishStartResponse> call, Throwable t) {
                    dialog.dismiss();
                }
            });
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }


    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Override
    public void callBackMethod(Object object) {
        if (object instanceof Datum) {
            etScheduleName.setText(((com.think360.sosimply.model.schedule.pending.Datum) object).getSchduleName());
            etScheduleId = ((com.think360.sosimply.model.schedule.pending.Datum) object).getSchduleId();
        }
    }
}
