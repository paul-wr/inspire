package com.example.mainaccount.inspire;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class NotificationActivity extends AppCompatActivity {

    private int notificationId = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);


    }

    public void startService(View view){
        startService(new Intent(getBaseContext(), NotificationService.class));
    }

    public void stopService(View view){
        stopService(new Intent(getBaseContext(), NotificationService.class));
    }

}
