package com.think360.sosimply.ui.fragments;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;



/**
 * Created by think360 on 22/03/17.
 */

public class RootFragment extends Fragment{


    protected ProgressDialog pDialog;
    protected AlertDialog.Builder alertDialog;


    public void showProgressBar() {

        pDialog = new ProgressDialog(getActivity());
        pDialog.setMessage("Loading...");
        pDialog.setCancelable(false);
        pDialog.setCanceledOnTouchOutside(true);
        pDialog.show();

    }

    protected void showProgressBarWithMessage(String message) {

        pDialog = new ProgressDialog(getActivity());
        pDialog.setMessage(message);
        pDialog.setCancelable(false);
        pDialog.setCanceledOnTouchOutside(false);
        pDialog.show();

    }

    protected void showDialog(String message) {
        alertDialog = new AlertDialog.Builder(getActivity());
        alertDialog.setMessage(message);
        alertDialog.setCancelable(true);

        alertDialog.setPositiveButton(
                "Ok",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });


        AlertDialog alert11 = alertDialog.create();
        alert11.show();
    }

    protected void showMessageInSnackBar(String message) {
        Snackbar.make(getActivity().findViewById(android.R.id.content),
                message, Snackbar.LENGTH_SHORT).show();
    }




    @Override
    public void onPause() {
        if (pDialog != null) {
            pDialog.hide();
        }
        if (alertDialog != null) {

        }

        super.onPause();
    }
}
