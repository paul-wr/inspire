package com.example.mainaccount.inspire;

import android.app.AlarmManager;

/**
 *  Classname: SetDuration.java
 *  Version 1
 *  Date: 22 Jul 2017
 *  @author Paul Wrenn, x15020029
 */


public class SetDuration {
    private static long duration;

    public SetDuration(){
        duration = AlarmManager.INTERVAL_DAY;
    }


    public long getAdminDuration() {
        return duration;
    }

    public void setAdminDuration(int time){
        switch(time){
            case 20:
                duration = 20000;
                break;
            case 60:
                duration = 60000;
                break;
            case 15:
                duration = AlarmManager.INTERVAL_FIFTEEN_MINUTES;
                break;
            case 30:
                duration = AlarmManager.INTERVAL_HALF_HOUR;
                break;
            default:
                duration = AlarmManager.INTERVAL_DAY;

        }

    }

}
