package com.think360.sosimply.ui.activities;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.think360.sosimply.AppController;
import com.think360.sosimply.R;
import com.think360.sosimply.AppConstants;

/**
 * Created by surinder on 17-Jul-17.
 */

public class BaseActivity extends AppCompatActivity {
    protected ProgressDialog pDialog;

    protected AlertDialog alertDialog;

    protected int driverId = AppController.sharedPreferencesCompat.getInt(AppConstants.DRIVER_ID, 0);


    protected void navigateToOtherActivity(Context context, Class aClass) {

        startActivity(new Intent(context, aClass));
    }


    protected class MyBounceInterpolator implements android.view.animation.Interpolator {
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

    protected void animatemy(View view) {
        Animation myAnim = AnimationUtils.loadAnimation(this, R.anim.bounce);
        myAnim.setInterpolator(new MyBounceInterpolator(0.20, 30));
        view.startAnimation(myAnim);
    }


    protected void showSimpleDialog(Context context, String s) {

        AlertDialog.Builder alertDialogBuilder;
        alertDialogBuilder = new AlertDialog.Builder(context);
        alertDialogBuilder.setMessage(s);
        alertDialogBuilder.setCancelable(true);
        alertDialogBuilder.setPositiveButton(
                "Ok",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        alertDialog = alertDialogBuilder.create();
    }

    protected void showProgressDialog(Context context) {
        pDialog = new ProgressDialog(context);
        pDialog.setMessage("Loading...");
        pDialog.setCancelable(false);
        pDialog.setCanceledOnTouchOutside(false);


    }


}
