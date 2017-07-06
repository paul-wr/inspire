package com.example.mainaccount.inspire;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.widget.Toast;

import java.util.Date;

import static com.example.mainaccount.inspire.NotificationSettingsActivity.time;

public class MyService extends Service {
    private PendingIntent pendingIntent;


    public MyService() {

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
        String date = new Date(time).toString();

        Intent alarmIntent = new Intent(getApplicationContext(), MyReceiver.class);
        // PendingIntent holds Intent until called by AlarmManger
        pendingIntent = PendingIntent.getBroadcast(getApplicationContext(), 0, alarmIntent, 0);


        manager.setRepeating(AlarmManager.RTC_WAKEUP, time, interval , pendingIntent);
        Toast.makeText(this, "Notifications activated!", Toast.LENGTH_SHORT).show();
        // Let it continue running until it is stopped.
        Toast.makeText(this, "Service Started", Toast.LENGTH_LONG).show();
        Toast.makeText(this, "Time: "+date, Toast.LENGTH_LONG).show();

        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        AlarmManager manager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        manager.cancel(pendingIntent);
        Toast.makeText(this, "Notifications deactivated!", Toast.LENGTH_SHORT).show();
        Toast.makeText(this, "Service Destroyed", Toast.LENGTH_LONG).show();
    }


}
