package com.example.mainaccount.inspire;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;

import com.example.mainaccount.inspire.model.Gem;

import static com.example.mainaccount.inspire.SystemRebootReceiver.rebootKey;

public class MyReceiver extends BroadcastReceiver {
    Gem gem;
    String[] array;
    public static String title;
    public static String text;

    public MyReceiver() {
        gem = new Gem();
        gem.createList();
        array = gem.getRandomGem();
        title = array[0];
        text = array[1];
    }

    @Override
    public void onReceive(Context context, Intent intent) {
            createNotification(context, "Inspire notification", text, "Inspire Notification");
            array = gem.getRandomGem();
    }

  
    public void createNotification(Context context, String msg, String msgText, String notificationAlert){
        if(!rebootKey) {

            // Define an Intent and an action to perform with it by another application
            PendingIntent notificIntent = PendingIntent.getActivity(context, 0,
                    new Intent(context, NotificationDetails.class), 0);

            // Builds a notification
            NotificationCompat.Builder mBuilder =
                    new NotificationCompat.Builder(context)
                            .setSmallIcon(R.drawable.ic_stat_android)
                            .setContentTitle(msg)
                            .setWhen(System.currentTimeMillis())
                            .setTicker(notificationAlert)
                            .addAction(android.R.drawable.ic_dialog_info, "Details", notificIntent)
                            .setContentText(msgText);

            // Defines the Intent to fire when the notification is clicked
            mBuilder.setContentIntent(notificIntent);


            // DEFAULT_SOUND : Make sound
            mBuilder.setDefaults(Notification.DEFAULT_SOUND);

            // Auto cancels the notification when clicked on in the task bar
            mBuilder.setAutoCancel(false);

            // Gets a NotificationManager which is used to notify the user of the background event
            NotificationManager mNotificationManager =
                    (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

            // Post the notification
            mNotificationManager.notify(1, mBuilder.build());

        }
        rebootKey = false;
    }
}
