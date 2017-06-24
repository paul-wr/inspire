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
    Calendar calendar;
    TextView clockText; // TextViews for each NumberPicker (hour/minute)
    TimePicker timePicker;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification_settings);
        timePicker = (TimePicker) findViewById(R.id.timePicker);
        clockText = (TextView) findViewById(R.id.clock_text);

        // calender instance set to default notification time of 18:00
        calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 18);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);

        // Intent to begin BroadcastReceiver
        Intent alarmIntent = new Intent(NotificationSettingsActivity.this, MyReceiver.class);
        // PendingIntent holds Intent until called by AlarmManger
        pendingIntent = PendingIntent.getBroadcast(NotificationSettingsActivity.this, 0, alarmIntent, 0);


        timePicker.setIs24HourView(false);

        timePicker.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
            @Override
            public void onTimeChanged(TimePicker timePicker, int hour, int minute) {
                updateDisplay(hour, minute);
            }

        });




        /* set_time button sets values to calender variables and calls start()
        method to begin notifications at specified time
        */
        findViewById(R.id.set_time).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calendar.set(Calendar.HOUR_OF_DAY, userHour);
                calendar.set(Calendar.MINUTE, userMinutes);
                start();
                Toast.makeText(NotificationSettingsActivity.this, "Notification time has been set!", Toast.LENGTH_SHORT).show();
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

    private void updateDisplay(int hourOfDay, int minute) {
        clockText.setText(hourOfDay+" "+minute);

    }



    // start() method defines an AlarmManger to start notifications for set time and launches Intent via PendingIntent
    public void start() {
        AlarmManager manager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);

        manager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_FIFTEEN_MINUTES , pendingIntent);
        Toast.makeText(this, "Notifications activated!", Toast.LENGTH_SHORT).show();
    }

    // cancel() method stops the notifications
    public void cancel() {
        AlarmManager manager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        manager.cancel(pendingIntent);
        Toast.makeText(this, "Notifications deactivated!", Toast.LENGTH_SHORT).show();
    }


}
