package com.example.mainaccount.inspire;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;

public class NotificationSettingsActivity extends AppCompatActivity {
    private PendingIntent pendingIntent;
    private int notificationId = 1;
    public static int userMinutes;
    public static int userHour;
    Calendar calendar;
    TextView minuteView, hourView;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification_settings);

        calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 16);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);

          /* Retrieve a PendingIntent that will perform a broadcast */
        Intent alarmIntent = new Intent(NotificationSettingsActivity.this, MyReceiver.class);
        pendingIntent = PendingIntent.getBroadcast(NotificationSettingsActivity.this, 0, alarmIntent, 0);

        minuteView = (TextView)findViewById(R.id.minute_view);
        NumberPicker minutePicker = (NumberPicker) findViewById(R.id.minute_picker);
        minutePicker.setMaxValue(60);
        minutePicker.setMinValue(0);
        minutePicker.setWrapSelectorWheel(true);
        minutePicker.setOnValueChangedListener( new NumberPicker.
                OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int
                    oldVal, int newVal) {
                userMinutes = newVal;
                minuteView.setText("Selected minute is "+
                        newVal);
            }
        });

        hourView = (TextView)findViewById(R.id.hour_view);
        NumberPicker hourPicker = (NumberPicker) findViewById(R.id.hour_picker);
        hourPicker.setMaxValue(23);
        hourPicker.setMinValue(0);
        hourPicker.setWrapSelectorWheel(true);
        hourPicker.setOnValueChangedListener( new NumberPicker.
                OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int
                    oldVal, int newVal) {
                userHour = newVal;
                hourView.setText("Selected hour is "+
                        newVal);
            }
        });


        findViewById(R.id.set_time).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calendar.set(Calendar.HOUR_OF_DAY, userHour);
                calendar.set(Calendar.MINUTE, userMinutes);
                start();
                Toast.makeText(NotificationSettingsActivity.this, "Notification time has been set!", Toast.LENGTH_SHORT).show();
            }
        });



        findViewById(R.id.startAlarm).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                start();
            }
        });

        findViewById(R.id.stopAlarm).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cancel();
            }
        });


    }




    public void start() {
        AlarmManager manager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);

        manager.setInexactRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_FIFTEEN_MINUTES , pendingIntent);
        Toast.makeText(this, "Notifications activated!", Toast.LENGTH_SHORT).show();
    }

    public void cancel() {
        AlarmManager manager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        manager.cancel(pendingIntent);
        Toast.makeText(this, "Notifications deactivated!", Toast.LENGTH_SHORT).show();
    }


}
