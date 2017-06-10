package com.example.mainaccount.inspire;

import android.app.NotificationManager;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.widget.Toast;

/**
 * Created by mainaccount on 10/06/2017.
 */


public class NotificationService extends Service {
    private int notificationId = 1;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    // start service
    @Override
    public int onStartCommand(Intent intent, int flags, int startId){
        Toast.makeText(this, "Service Started", Toast.LENGTH_LONG).show();
        startNotifications();
        return START_STICKY;

    }

    // destroy service
    @Override
    public void onDestroy(){
        super.onDestroy();
        Toast.makeText(this, "Service Destroyed", Toast.LENGTH_LONG).show();
    }



    public void startNotifications(){

        // NotificationCompat Builder takes care of backwards compatibility and
        // provides clean API to create rich notifications
        NotificationCompat.Builder mBuilder = (NotificationCompat.Builder) new NotificationCompat.Builder(NotificationService.this)
                .setSmallIcon(R.drawable.ic_stat_android)
                .setContentTitle("Inspire Phrase Title")
                .setContentText("Content text");

        // Obtain NotificationManager system service in order to show the notification
        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        notificationManager.notify(notificationId, mBuilder.build());

    }

}