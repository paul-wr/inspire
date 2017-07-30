package com.example.mainaccount.inspire.activities;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.mainaccount.inspire.NotificationService;
import com.example.mainaccount.inspire.R;
import com.example.mainaccount.inspire.SetDuration;
import com.example.mainaccount.inspire.SetTime;
import com.example.mainaccount.inspire.VerifyAdmin;
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


public class NotificationSettingsActivity extends BaseActivity  {
    private PendingIntent pendingIntent; // pendingIntent for broadcastReceiver
    public static int userMinutes; // user specified minute
    public static int userHour; // user specified hour
    TextView infoTV;
    TextView adminTV;
    TimePicker timePicker; // TimePicker allows user to define notification time
    SetTime setTime; // SetTime class for setting and retrieving time
    public static boolean isRedirected; // redirect user back to this activity if redirected to sign in
    private boolean isUserTimeSet; // set default alarm time if none set
    public static final String MyPREFERENCES = "MyPrefs" ; // data member to store name of SharedPreferences instance
    SharedPreferences sharedPreferences; // store hour, minute, isNotificationsOn variables for access on reboot
    SharedPreferences.Editor editor; // declare editor to edit sharedPreferences
    int infoCount;
    LinearLayout adminLinearLayout;
    VerifyAdmin verifyAdmin;
    SetDuration setDuration;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification_settings);

        setDuration = new SetDuration();

        adminLinearLayout = (LinearLayout) findViewById(R.id.adminLinearL);
        adminLinearLayout.setVisibility(View.INVISIBLE);

        verifyAdmin = new VerifyAdmin();

        sharedPreferences = getApplicationContext().getSharedPreferences(MyPREFERENCES, 0); // instantiate SharedPreferences
        editor = sharedPreferences.edit(); // instantiate editor

        timePicker = (TimePicker) findViewById(R.id.timePicker); // user will set time using timePicker
        setTime = new SetTime(); // instantiate SetTime class

        setHeadingText("Settings");

        Button setTimeBtn = (Button) findViewById(R.id.set_time);
        Button editTimeBtn = (Button) findViewById(R.id.edit_time);
        Button startNotificationsBtn = (Button) findViewById(R.id.start_notifications);
        Button stopNotificationsBtn = (Button) findViewById(R.id.stop_notifications);
        ImageButton infoBtn = (ImageButton) findViewById(R.id.info_btn);

        Button halfHour = (Button) findViewById(R.id.half_hour);
        Button fifteenMinutes = (Button) findViewById(R.id.fifteen_mins);
        Button sixtySeconds = (Button) findViewById(R.id.sixty_seconds);
        Button twentySeconds = (Button) findViewById(R.id.twenty_seconds);


        setTimeBtn.setVisibility(View.INVISIBLE);

        infoTV = (TextView) findViewById(R.id.info_tv);
        adminTV = (TextView) findViewById(R.id.admin_tv);
        infoTV.setVisibility(View.INVISIBLE);
        adminTV.setVisibility(View.INVISIBLE);

        // Intent to begin BroadcastReceiver
        Intent alarmIntent = new Intent(NotificationSettingsActivity.this, NotificationReceiver.class);
        // pendingIntent holds BroadcastReceiver
        pendingIntent = PendingIntent.getBroadcast(NotificationSettingsActivity.this, 0, alarmIntent, 0);


        if(FirebaseAuth.getInstance().getCurrentUser() != null) {
            if (verifyAdmin.isAdmin(FirebaseAuth.getInstance().getCurrentUser().getUid().toString())) {
                adminLinearLayout.setVisibility(View.VISIBLE);
                adminTV.setVisibility(View.VISIBLE);
            }
        }

        // timePicker allows the user to define the time of the notifications
        TimePicker timePicker = (TimePicker) findViewById(R.id.timePicker);
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
        setTimeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(FirebaseAuth.getInstance().getCurrentUser() != null) {
                    setTime = new SetTime(userHour, userMinutes);
                    editor.putBoolean("isNotificationsOn", true);
                    editor.putInt("hour", userHour);
                    editor.putInt("minute", userMinutes);
                    editor.putLong("duration", setDuration.getAdminDuration());
                    editor.commit();
                    isUserTimeSet = true;

                    startService(new Intent(getBaseContext(), NotificationService.class));
                    Toast.makeText(NotificationSettingsActivity.this, "Notification time has been set! " + setTime.getCalendar().get(Calendar.HOUR_OF_DAY) + " : " + setTime.getCalendar().get(Calendar.MINUTE), Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(NotificationSettingsActivity.this, "You must be signed in to set notifications time!\n" +
                            "Redirecting to sign in...", Toast.LENGTH_LONG).show();
                    userIntent = getIntent();
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

        editTimeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                findViewById(R.id.set_time).setVisibility(View.VISIBLE);
                findViewById(R.id.timePicker).setVisibility(View.VISIBLE);

            }
        });


        // button onClick starts Notifications
        startNotificationsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(NotificationSettingsActivity.this, "Notifications set at default time 18:00", Toast.LENGTH_LONG).show();
                start();
            }
        });

        // button onClick stops Notifications
        stopNotificationsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cancel();
            }
        });

        infoCount = 1;
        infoBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(infoCount %2 == 1){
                    infoTV.setVisibility(View.VISIBLE);
                }else {
                    infoTV.setVisibility(View.INVISIBLE);
                }
                infoCount++;
            }
        });

        halfHour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setDuration.setAdminDuration(30);
                Toast.makeText(NotificationSettingsActivity.this, "duration of 30 minutes set", Toast.LENGTH_LONG).show();
            }
        });

        fifteenMinutes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setDuration.setAdminDuration(15);
                Toast.makeText(NotificationSettingsActivity.this, "duration of 15 minutes set", Toast.LENGTH_LONG).show();

            }
        });

        sixtySeconds.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setDuration.setAdminDuration(60);
                Toast.makeText(NotificationSettingsActivity.this, "duration of 60 seconds set", Toast.LENGTH_LONG).show();

            }
        });

        twentySeconds.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setDuration.setAdminDuration(20);
                Toast.makeText(NotificationSettingsActivity.this, "duration of 20 seconds set", Toast.LENGTH_LONG).show();

            }
        });

    }



    // start() method defines an AlarmManger to start notifications for set time and launches Intent via PendingIntent
    public void start() {
        editor.putBoolean("notificationsOn", true);
        editor.apply();
        if(!isUserTimeSet){
            setTime.setDefaultTime();
        }

        AlarmManager manager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);

        manager.setRepeating(AlarmManager.RTC_WAKEUP, setTime.getCalendar().getTimeInMillis(), AlarmManager.INTERVAL_DAY , pendingIntent);
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
