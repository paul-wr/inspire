package com.example.mainaccount.inspire;

/**
 *  Classname: NotificationSettingsActivity.java
 *  Version 1
 *  Date: 22 Jun 2017
 *  @author Paul Wrenn, x15020029
 */

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;


public class NotificationSettingsActivity extends AppCompatActivity {
    private PendingIntent pendingIntent; // PendingIntent stores Intent until Alarm fires
    private int notificationId = 1;
    public static int userMinutes; // user specified minute count
    public static int userHour; // user specified hour of day
    TextView clockText; // TextViews for each NumberPicker (hour/minute)
    TimePicker timePicker;
    SetTime setTime;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification_settings);
        timePicker = (TimePicker) findViewById(R.id.timePicker);

        // Intent to begin BroadcastReceiver
        Intent alarmIntent = new Intent(NotificationSettingsActivity.this, MyReceiver.class);
        // PendingIntent holds Intent until called by AlarmManger
        pendingIntent = PendingIntent.getBroadcast(NotificationSettingsActivity.this, 0, alarmIntent, 0);



        clockText = (TextView) findViewById(R.id.clock_text);

        TimePicker timePicker = (TimePicker) findViewById(R.id.timePicker);

        timePicker.setVisibility(View.INVISIBLE);

        timePicker.setIs24HourView(false);

        timePicker.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
            @Override
            public void onTimeChanged(TimePicker timePicker, int i, int i1) {
                clockText.setText("Time: "+i+" : "+i1);
                userHour = i;
                userMinutes = i1;
            }
        });




        /* set_time button sets values to calender variables and calls start()
        method to begin notifications at specified time
        */
        findViewById(R.id.set_time).setVisibility(View.INVISIBLE);
        findViewById(R.id.set_time).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setTime = new SetTime(userHour, userMinutes);
                start();
                Toast.makeText(NotificationSettingsActivity.this, "Notification time has been set! "+setTime.getCalendar().get(Calendar.HOUR_OF_DAY)+" : "+setTime.getCalendar().get(Calendar.MINUTE), Toast.LENGTH_SHORT).show();
            }
        });

        findViewById(R.id.edit_time).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                findViewById(R.id.set_time).setVisibility(View.VISIBLE);
                findViewById(R.id.timePicker).setVisibility(View.VISIBLE);

            }
        });


        // button onClick starts Notifications
        findViewById(R.id.start_notifications).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                start();
            }
        });

        // button onClick stops Notifications
        findViewById(R.id.stop_notifications).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cancel();
            }
        });


    }




    // start() method defines an AlarmManger to start notifications for set time and launches Intent via PendingIntent
    public void start() {
        AlarmManager manager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);

        manager.setRepeating(AlarmManager.RTC_WAKEUP, setTime.getCalendar().getTimeInMillis(), AlarmManager.INTERVAL_FIFTEEN_MINUTES , pendingIntent);
        Toast.makeText(this, "Notifications activated!", Toast.LENGTH_SHORT).show();
    }

    // cancel() method stops the notifications
    public void cancel() {
        AlarmManager manager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        manager.cancel(pendingIntent);
        Toast.makeText(this, "Notifications deactivated!", Toast.LENGTH_SHORT).show();
    }


}
