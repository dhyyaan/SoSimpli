package com.think360.sosimpli.ui.activities;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import com.think360.sosimpli.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class SoSDetailActivity extends BaseActivity {
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.tvDecline)
    TextView tvDecline;

    @BindView(R.id.tvAccept)
    TextView tvAccept;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sos_detail);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("SOS Details");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }

    @OnClick(R.id.tvDecline)
    void onClickDecline() {
        showDialog("Your decline request has been successfully sent");
    }

    @OnClick(R.id.tvAccept)
    void onClickAccept() {
        showDialog("Your accept request has been successfully sent");
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    void showDialog(String text) {
        Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialog_send_request);
        dialog.show();
        ((TextView) dialog.findViewById(R.id.text)).setText(text);
        animateView(dialog.findViewById(R.id.imageView));
    }

    private void animateView(View view) {
        Animation myAnim = AnimationUtils.loadAnimation(this, R.anim.bounce);
        myAnim.setInterpolator(new MyBounceInterpolator(0.15, 30));
        view.startAnimation(myAnim);
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }
}
