package com.example.mainaccount.inspire;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import static com.example.mainaccount.inspire.NotificationSettingsActivity.MyPREFERENCES;

public class RebootReceiver extends BroadcastReceiver {
    SharedPreferences sharedPreferences;
    SetTime setTime;
    int hour;
    int minute;
    public  static boolean isNotificationsOn;


    @Override
    public void onReceive(Context context, Intent intent) {
        sharedPreferences = context.getApplicationContext().getSharedPreferences(MyPREFERENCES, 0);
        hour = sharedPreferences.getInt("hour", 0);
        minute = sharedPreferences.getInt("minute", 0);
        isNotificationsOn = sharedPreferences.getBoolean("isNotificationsOn", false);

        setTime = new SetTime(hour, minute);


        Intent serviceIntent = new Intent(context, NotificationService.class);
        context.startService(serviceIntent);


    }
}
