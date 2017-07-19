package com.example.mainaccount.inspire.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

import com.example.mainaccount.inspire.R;

/**
 *  Classname: LaunchActivity.java
 *  Version 1
 *  Date: 25 Jun 2017
 *  @author Paul Wrenn, x15020029
 */

public class LaunchActivity extends AppCompatActivity {
    private static final int HOME_LAUNCH_DELAY = 3500;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        // Handler postDelayed method implements a delay before launching HomeActivity
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent i = new Intent(LaunchActivity.this, HomeActivity.class);
                startActivity(i);
                finish();
            }
        }, HOME_LAUNCH_DELAY);


    }
}
