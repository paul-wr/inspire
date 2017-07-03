package com.example.mainaccount.inspire;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.widget.Toast;

import java.util.Calendar;
import java.util.Date;

import static com.example.mainaccount.inspire.NotificationSettingsActivity.MyPREFERENCES;


public class MyBootReceiver extends BroadcastReceiver {
    // version 2
    private PendingIntent pendingIntent;
    Boolean isNotificationsOn;
    long time;
    Calendar calendar;
    SetTime setTime;


    public MyBootReceiver() {
        setTime = new SetTime();
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        SharedPreferences prefs = context.getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        isNotificationsOn = prefs.getBoolean("notificationsOn", false);
        time = prefs.getLong("time", System.currentTimeMillis());
        Date date = new Date(time);




        if(isNotificationsOn) {
            // run check to see if notifications are turned on
            // Retrieve a PendingIntent that will perform a broadcast
            Intent alarmIntent = new Intent(context, MyReceiver.class);
            pendingIntent = PendingIntent.getBroadcast(context, 0, alarmIntent, 0);
            AlarmManager manager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
            int interval = 15000;

            manager.setRepeating(AlarmManager.RTC_WAKEUP, time, interval, pendingIntent);
            Toast.makeText(context, ""+date.toString(), Toast.LENGTH_SHORT).show();

        }

    }

}
