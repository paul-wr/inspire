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
import android.view.View;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.mainaccount.inspire.model.BaseActivity;
import com.example.mainaccount.inspire.model.LoginActivity;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Calendar;


public class NotificationSettingsActivity extends BaseActivity {
    private PendingIntent pendingIntent; // PendingIntent stores Intent until Alarm fires
    private int notificationId = 1;
    public static int userMinutes; // user specified minute count
    public static int userHour; // user specified hour of day
    TextView clockText; // TextViews for clock
    TimePicker timePicker; // TimePicker allows user to define notification time
    SetTime setTime; // SetTime class for setting and retrieving time
    public static boolean isNotificationsOn; // check if notifications are on/off on reboot of system


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
            public void onTimeChanged(TimePicker timePicker, int hour, int minute) {
                clockText.setText("Time: "+hour+" : "+minute);
                userHour = hour;
                userMinutes = minute;
            }
        });

        /* set_time button sets values to calender variables and calls start()
        method to begin notifications at specified time
        */
        findViewById(R.id.set_time).setVisibility(View.INVISIBLE);
        findViewById(R.id.set_time).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(FirebaseAuth.getInstance().getCurrentUser() != null) {
                    setTime = new SetTime(userHour, userMinutes);
                    start();
                    Toast.makeText(NotificationSettingsActivity.this, "Notification time has been set! " + setTime.getCalendar().get(Calendar.HOUR_OF_DAY) + " : " + setTime.getCalendar().get(Calendar.MINUTE), Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(NotificationSettingsActivity.this, "You need to be logged in to set notifications time!", Toast.LENGTH_LONG).show();
                    Thread thread = new Thread(){
                        @Override
                        public void run() {
                            try {
                                Thread.sleep(2500); // Launch login Activity after Toast message has run
                                startActivity(new Intent(NotificationSettingsActivity.this, LoginActivity.class));
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    };

                    thread.start();
                }
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
                setTime = new SetTime();
                Toast.makeText(NotificationSettingsActivity.this, "Notifications set at defualt time 18:00", Toast.LENGTH_LONG).show();
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
        isNotificationsOn = true;

        AlarmManager manager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        int interval = 15000;


        manager.setRepeating(AlarmManager.RTC_WAKEUP, setTime.getCalendar().getTimeInMillis(), interval , pendingIntent);
        Toast.makeText(this, "Notifications activated!", Toast.LENGTH_SHORT).show();
    }

    // cancel() method stops the notifications
    public void cancel() {
        isNotificationsOn = false;

        AlarmManager manager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        manager.cancel(pendingIntent);
        Toast.makeText(this, "Notifications deactivated!", Toast.LENGTH_SHORT).show();

    }

    // onRestart() method call refreshes user login data in the menu
    @Override
    public void onRestart() {
        super.onRestart();
        finish();
        startActivity(getIntent());
    }

}
