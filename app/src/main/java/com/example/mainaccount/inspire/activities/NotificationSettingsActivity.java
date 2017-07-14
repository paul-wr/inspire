package com.example.mainaccount.inspire.activities;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.mainaccount.inspire.NotificationService;
import com.example.mainaccount.inspire.R;
import com.example.mainaccount.inspire.SetTime;
import com.example.mainaccount.inspire.broadcasts.NotificationReceiver;
import com.example.mainaccount.inspire.model.BaseActivity;
import com.example.mainaccount.inspire.model.SigninActivity;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Calendar;


/**
 *  Classname: NotificationSettingsActivity.java
 *  Version 1
 *  Date: 22 Jun 2017
 *  @author Paul Wrenn, x15020029
 */


public class NotificationSettingsActivity extends BaseActivity {
    private PendingIntent pendingIntent; // pendingIntent for broadcastReceiver
    public static int userMinutes; // user specified minute
    public static int userHour; // user specified hour
    TimePicker timePicker; // TimePicker allows user to define notification time
    SetTime setTime; // SetTime class for setting and retrieving time
    public static boolean isRedirected; // redirect user back to this activity if redirected to sign in
    private boolean isUserTimeSet; // set default alarm time if none set
    public static final String MyPREFERENCES = "MyPrefs" ; // data member to store name of SharedPreferences instance
    SharedPreferences sharedPreferences; // store hour, minute, isNotificationsOn variables for access on reboot
    SharedPreferences.Editor editor; // declare editor to edit sharedPreferences


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification_settings);
        timePicker = (TimePicker) findViewById(R.id.timePicker); // user will set time using timePicker
        sharedPreferences = getApplicationContext().getSharedPreferences(MyPREFERENCES, 0); // instantiate SharedPreferences
        editor = sharedPreferences.edit(); // instantiate editor
        setTime = new SetTime(); // instantiate SetTime class

        // Intent to begin BroadcastReceiver
        Intent alarmIntent = new Intent(NotificationSettingsActivity.this, NotificationReceiver.class);
        // pendingIntent holds BroadcastReceiver
        pendingIntent = PendingIntent.getBroadcast(NotificationSettingsActivity.this, 0, alarmIntent, 0);

        // timePicker allows the user to define the time of the notifications
        TimePicker timePicker = (TimePicker) findViewById(R.id.timePicker);
        timePicker.setVisibility(View.INVISIBLE);
        timePicker.setIs24HourView(false); // spinning wheel style

        // set variables on time change
        timePicker.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
            @Override
            public void onTimeChanged(TimePicker timePicker, int hour, int minute) {
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
                    editor.putBoolean("isNotificationsOn", true);
                    editor.putInt("hour", userHour);
                    editor.putInt("minute", userMinutes);
                    editor.commit();
                    isUserTimeSet = true;
                    startService(new Intent(getBaseContext(), NotificationService.class));
                    Toast.makeText(NotificationSettingsActivity.this, "Notification time has been set! " + setTime.getCalendar().get(Calendar.HOUR_OF_DAY) + " : " + setTime.getCalendar().get(Calendar.MINUTE), Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(NotificationSettingsActivity.this, "You must be signed in to set notifications time!\n" +
                            "Redirecting to sign in...", Toast.LENGTH_LONG).show();
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
        startService(new Intent(getBaseContext(), NotificationService.class));
    }

    // Method to stop the service
    public void stopService(View view) {
        editor.putBoolean("isNotificationsOn", false);
        stopService(new Intent(getBaseContext(), NotificationService.class));
    }

    public void onStop() {
        super.onStop();
        stopService(new Intent(getApplicationContext(), NotificationService.class));
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
        editor.putBoolean("isNotificationsOn", false);
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
