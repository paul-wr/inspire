package com.example.mainaccount.inspire;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.widget.Toast;

import java.util.Calendar;
import java.util.Date;

import static com.example.mainaccount.inspire.NotificationSettingsActivity.MyPREFERENCES;

/**
 *  Classname: SetTime.java
 *  Version 1
 *  Date: 25 Jun 2017
 *  @author Paul Wrenn, x15020029
 */
public class SetTime {
    private static Calendar calendar;
    private PendingIntent pendingIntent;
    Boolean isNotificationsOn;
    long time;
    SharedPreferences prefs;


    public SetTime(){
    }

    public SetTime(int hour, int minute) {
        calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, hour);
        calendar.set(Calendar.MINUTE, minute);
        calendar.set(Calendar.SECOND, 0);
    }


    public void setDefaultTime() {
        // calender instance set to default notification time of 18:00
        calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 18);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
    }

    public void setAlarm(Context context){
        prefs = context.getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
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



    public Calendar getCalendar(){
        return calendar;
    }

}
