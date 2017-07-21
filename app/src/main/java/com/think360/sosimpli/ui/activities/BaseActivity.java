package com.think360.sosimpli.ui.activities;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by surinder on 17-Jul-17.
 */

public class BaseActivity extends AppCompatActivity {


    protected void navigateToOtherActivity(Context context, Class aClass) {

        startActivity(new Intent(context, aClass));
    }

}
