package com.think360.sosimpli.ui.activities;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
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

public class ChangeScheduleActivity extends BaseActivity {


    @BindView(R.id.toolbar)
    Toolbar toolbar;


    @BindView(R.id.updateAvailability)
    TextView updateAvailability;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_availability);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Update Availability");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @OnClick(R.id.updateAvailability)
    public void onClickUpdateAvailability() {
        Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialog_send_request);

        ((TextView) dialog.findViewById(R.id.text)).setText("Your mentioned availability updated successfully ");
        dialog.show();
        animatemy(dialog.findViewById(R.id.imageView));

    }

    private void animatemy(View view) {

        Animation myAnim = AnimationUtils.loadAnimation(this, R.anim.bounce);
        myAnim.setInterpolator(new MyBounceInterpolator(0.20, 30));
        view.startAnimation(myAnim);
    }


  /*  class MyBounceInterpolator implements android.view.animation.Interpolator {
        private double mAmplitude = 5;
        private double mFrequency = 10;

        MyBounceInterpolator(double amplitude, double frequency) {
            mAmplitude = amplitude;
            mFrequency = frequency;
        }

        public float getInterpolation(float time) {
            return (float) (-1 * Math.pow(Math.E, -time / mAmplitude) *
                    Math.cos(mFrequency * time) + 1);
        }
    }
*/
    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }
}
