package com.think360.sosimpli.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import com.think360.sosimpli.AppController;
import com.think360.sosimpli.R;
import com.think360.sosimpli.utils.AppConstants;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class SplashActivity extends BaseActivity {

    private static final String TAG = SplashActivity.class.getSimpleName();
    private Handler handler = new Handler();
    private Runnable runnable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_splash);
        runnable = new Runnable() {
            @Override
            public void run() {

                if (AppController.sharedPreferencesCompat.getBoolean(AppConstants.IS_LOGIN, false) && AppController.sharedPreferencesCompat.getBoolean(AppConstants.IS_REMEMBER_TAPPED, false)) {
                    callActivity(HomeActivity.class);
                    // Contacts permissions have not been granted.
                    Log.i(TAG, "Contact permissions has NOT been granted. Requesting permissions.");
                } else {
                    callActivity(LoginActivity.class);
                }
                finish();

            }
        };

    }

    private void callActivity(Class aClass) {
        startActivity(new Intent(SplashActivity.this, aClass));
        finish();
        overridePendingTransition(R.anim.zoom_exit, 0);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        handler.postDelayed(runnable, 1000);

    }

    @Override
    protected void onPause() {
        super.onPause();
        // handler.removeCallbacks(runnable);
    }

    @Override
    protected void onResume() {
        super.onResume();
        // handler.postDelayed(runnable, 1000);
    }
}
