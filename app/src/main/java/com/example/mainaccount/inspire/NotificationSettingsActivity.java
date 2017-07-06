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
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.mainaccount.inspire.model.BaseActivity;
import com.example.mainaccount.inspire.model.SigninActivity;
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
    public static boolean isRedirected;
    private boolean isUserTimeSet;
    public static final String MyPREFERENCES = "MyPrefs" ;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    public static long time;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification_settings);
        timePicker = (TimePicker) findViewById(R.id.timePicker);
        sharedPreferences = getApplicationContext().getSharedPreferences(MyPREFERENCES, 0);
        editor = sharedPreferences.edit();

        setTime = new SetTime();
        // Intent to begin BroadcastReceiver
        Intent alarmIntent = new Intent(NotificationSettingsActivity.this, MyReceiver.class);
        // PendingIntent holds Intent until called by AlarmManger
        pendingIntent = PendingIntent.getBroadcast(NotificationSettingsActivity.this, 0, alarmIntent, 0);

        clockText = (TextView) findViewById(R.id.clock_text);

        /* timePicker allows the user to define the time of the notifications */
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
                    editor.putBoolean("notificationsOn", true);
                    editor.putInt("hour", userHour);
                    editor.putInt("minute", userMinutes);
                    editor.putLong("time", setTime.getCalendar().getTimeInMillis());
                    time = sharedPreferences.getLong("time", setTime.getCalendar().getTimeInMillis());
                    editor.commit();
                    isUserTimeSet = true;
                    startService(new Intent(getBaseContext(), MyService.class));
                    Toast.makeText(NotificationSettingsActivity.this, "Notification time has been set! " + setTime.getCalendar().get(Calendar.HOUR_OF_DAY) + " : " + setTime.getCalendar().get(Calendar.MINUTE), Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(NotificationSettingsActivity.this, "You must be signed in to set notifications time!\n" +
                            "Redirecting to sigin...", Toast.LENGTH_LONG).show();
                    isRedirected = true;
                    Thread thread = new Thread(){
                        @Override
                        public void run() {
                            try {
                                Thread.sleep(2500); // Launch login Activity after Toast message has run
                                startActivity(new Intent(NotificationSettingsActivity.this, SigninActivity.class));
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
                Toast.makeText(NotificationSettingsActivity.this, "Notifications set at default time 18:00", Toast.LENGTH_LONG).show();
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

    public void startService(View view) {
        startService(new Intent(getBaseContext(), MyService.class));
    }

    // Method to stop the service
    public void stopService(View view) {
        stopService(new Intent(getBaseContext(), MyService.class));
    }

    public void onStop() {
        super.onStop();
        stopService(new Intent(getApplicationContext(), MyService.class));
    }

    // start() method defines an AlarmManger to start notifications for set time and launches Intent via PendingIntent
    public void start() {
        editor.putBoolean("notificationsOn", true);
        editor.apply();
        if(!isUserTimeSet){
            setTime.setDefaultTime();
        }

        AlarmManager manager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        int interval = 15000;


        manager.setRepeating(AlarmManager.RTC_WAKEUP, setTime.getCalendar().getTimeInMillis(), interval , pendingIntent);
        Toast.makeText(this, "Notifications activated!", Toast.LENGTH_SHORT).show();
    }

    // cancel() method stops the notifications
    public void cancel() {
        editor.putBoolean("notificationsOn", false);
        editor.apply();
        AlarmManager manager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        manager.cancel(pendingIntent);
        Toast.makeText(this, "Notifications deactivated!", Toast.LENGTH_SHORT).show();

    }

    // onRestart() method call refreshes user login data in the menu
    @Override
    public void onRestart() {
        super.onRestart();
        /** if back button has been pressed relaunch activity to update user login data
         *  (this boolean check helps to avoid a bug on screen sleep relaunch)
        */
        if(backPressed == true){
            finish();
            startActivity(getIntent());
            backPressed = false;
        }

    }

    @Override
    public void onBackPressed() {
        backPressed = true;
        super.onBackPressed();

    }

}
