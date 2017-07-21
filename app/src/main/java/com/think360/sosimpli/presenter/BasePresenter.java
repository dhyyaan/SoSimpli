package com.think360.sosimpli.presenter;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;

/**
 * Created by think360 on 26/04/17.
 */

public class BasePresenter {

    protected ProgressDialog pDialog;

    protected AlertDialog alertDialog;

    public BasePresenter(Context context) {


        AlertDialog.Builder alertDialogBuilder;
        alertDialogBuilder = new AlertDialog.Builder(context);

        pDialog = new ProgressDialog(context);
        pDialog.setMessage("Loading...");
        pDialog.setCancelable(false);
        pDialog.setCanceledOnTouchOutside(true);
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

    protected void showDialog(String message) {


    }

}
