package com.example.mainaccount.inspire.broadcasts;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.app.NotificationCompat;

import com.example.mainaccount.inspire.R;
import com.example.mainaccount.inspire.activities.NotificationDetails;
import com.example.mainaccount.inspire.model.Gem;

import java.util.GregorianCalendar;

import static android.content.Context.MODE_PRIVATE;

/**
 *  Classname: NotificationReceiver.java
 *  Version 1
 *  Date: 1 Jul 2017
 *  @reference Derek Banas https://www.youtube.com/watch?v=WozSRUnYoNM&t=937s
 *  @author Paul Wrenn, x15020029
 */

public class NotificationReceiver extends BroadcastReceiver {
    Gem gem;
    String[] array;
    String gemData;
    public static String title;
    public static String text;
    public static String author;
    private int notificationId = 1; // int for notification id
    public static final String MyHistoryPREFERENCES = "MyHistoryPrefs";
    SharedPreferences sharedPreferencesHistory;
    SharedPreferences.Editor editorHistory;

    public NotificationReceiver() {
        gem = new Gem();
        gem.createList();
        array = gem.getRandomGem();
        title = array[0];
        text = array[1];
        author = array[2];
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        sharedPreferencesHistory = context.getApplicationContext().getSharedPreferences(MyHistoryPREFERENCES, MODE_PRIVATE);
        editorHistory = sharedPreferencesHistory.edit();
        GregorianCalendar c = (GregorianCalendar) GregorianCalendar.getInstance();
        String dateString = c.getTime().toString();;
        createNotification(context, "Inspire notification", text, "Inspire Notification");
        array = gem.getRandomGem();
        gemData = "\""+text+"\""+" ~ "+author+"\n\nNotification date: "+dateString;
        editorHistory.putString(gemData, gemData);
        editorHistory.commit();

    }
    

    public void createNotification(Context context, String msg, String msgText, String notificationAlert){

            // Define an Intent and an action to perform with it by another application
            PendingIntent notificIntent = PendingIntent.getActivity(context, Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP,
                    new Intent(context, NotificationDetails.class), PendingIntent.FLAG_UPDATE_CURRENT);

            // Builds a notification
            NotificationCompat.Builder mBuilder =
                    new NotificationCompat.Builder(context)
                            .setSmallIcon(R.drawable.notification_icon)
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
            mNotificationManager.notify(notificationId, mBuilder.build());

    }
}
