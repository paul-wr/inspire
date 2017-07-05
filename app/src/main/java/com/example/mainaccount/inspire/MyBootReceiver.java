package com.example.mainaccount.inspire;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;


public class MyBootReceiver extends BroadcastReceiver {
    SetTime setTime;


    public MyBootReceiver() {
        setTime = new SetTime();
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        setTime.setAlarm(context);
    }

}
