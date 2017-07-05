package com.example.mainaccount.inspire;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;


public class SystemRebootReceiver extends BroadcastReceiver {
    SetTime setTime;
    public static boolean rebootKey;



    public SystemRebootReceiver() {
        setTime = new SetTime();
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        rebootKey = true;
        setTime.setAlarm(context);
    }

}
