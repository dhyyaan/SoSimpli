package com.think360.sosimpli.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.think360.sosimpli.R;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class SplashActivity extends BaseActivity {

    private Handler handler = new Handler();
    private Runnable runnable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_splash);
        runnable = new Runnable() {
            @Override
            public void run() {
                callMainActivity();
            }
        };

    }

    private void callMainActivity() {
        startActivity(new Intent(SplashActivity.this, LoginActivity.class));
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
