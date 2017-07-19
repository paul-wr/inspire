package com.example.mainaccount.inspire;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.widget.Toast;

import com.example.mainaccount.inspire.broadcasts.NotificationReceiver;

import java.util.Date;

import static com.example.mainaccount.inspire.activities.NotificationSettingsActivity.MyPREFERENCES;
import static com.example.mainaccount.inspire.broadcasts.RebootReceiver.isNotificationsOn;

/**
 *  Classname: NotificationService.java
 *  Version 1
 *  Date: 1 Jul 2017
 *  @author Paul Wrenn, x15020029
 */

public class NotificationService extends Service {
    private PendingIntent pendingIntent;
    SetTime setTime;
    SharedPreferences sharedPreferences;


    public NotificationService() {
        setTime = new SetTime();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        AlarmManager manager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        int interval = 15000;

        String date = new Date(setTime.getTime()).toString();

        sharedPreferences = getApplicationContext().getSharedPreferences(MyPREFERENCES, 0);
        isNotificationsOn = sharedPreferences.getBoolean("isNotificationsOn", false);


        Intent alarmIntent = new Intent(getApplicationContext(), NotificationReceiver.class);
        // PendingIntent holds Intent until called by AlarmManger
        pendingIntent = PendingIntent.getBroadcast(getApplicationContext(), 0, alarmIntent, 0);

        if(isNotificationsOn) {
            manager.setRepeating(AlarmManager.RTC_WAKEUP, setTime.getTime(), interval, pendingIntent);
            Toast.makeText(this, "Notifications activated!", Toast.LENGTH_SHORT).show();
            // Let it continue running until it is stopped.
            Toast.makeText(this, "Service Started", Toast.LENGTH_LONG).show();
            Toast.makeText(this, "Time: " + date, Toast.LENGTH_LONG).show();
        }
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Toast.makeText(this, "Service Destroyed", Toast.LENGTH_LONG).show();
    }


}
