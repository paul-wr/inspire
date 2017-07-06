package com.example.mainaccount.inspire;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import static com.example.mainaccount.inspire.NotificationSettingsActivity.MyPREFERENCES;
import static com.example.mainaccount.inspire.NotificationSettingsActivity.time;

public class RebootReceiver extends BroadcastReceiver {
    SharedPreferences sharedPreferences;

    @Override
    public void onReceive(Context context, Intent intent) {
        sharedPreferences = context.getApplicationContext().getSharedPreferences(MyPREFERENCES, 0);
        time = sharedPreferences.getLong("time", System.currentTimeMillis());

        Intent serviceIntent = new Intent(context, MyService.class);
        context.startService(serviceIntent);


    }
}
