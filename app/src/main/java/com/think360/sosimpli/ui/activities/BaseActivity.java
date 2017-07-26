package com.think360.sosimpli.ui.activities;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by surinder on 17-Jul-17.
 */

public class BaseActivity extends AppCompatActivity {
    protected ProgressDialog pDialog;

    protected AlertDialog alertDialog;


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
        pDialog.setCanceledOnTouchOutside(true);


    }


}
