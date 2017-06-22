package com.example.mainaccount.inspire;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class MyBootReceiver extends BroadcastReceiver {
    private PendingIntent pendingIntent;

    public MyBootReceiver() {

    }

    @Override
    public void onReceive(Context context, Intent intent) {
             /* Retrieve a PendingIntent that will perform a broadcast */
        Intent alarmIntent = new Intent(context, MyReceiver.class);
        pendingIntent = PendingIntent.getBroadcast(context, 0, alarmIntent, 0);
        AlarmManager manager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        int interval = 15000;

        manager.setInexactRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(), interval, pendingIntent);
        Toast.makeText(context, "Alarm Set", Toast.LENGTH_SHORT).show();


    }


}