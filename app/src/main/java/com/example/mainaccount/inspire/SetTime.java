package com.example.mainaccount.inspire;

import java.util.Calendar;

/**
 *  Classname: SetTime.java
 *  Version 1
 *  Date: 25 Jun 2017
 *  @author Paul Wrenn, x15020029
 */
public class SetTime {
    private static Calendar calendar;

    public SetTime(){
    }

    public SetTime(int hour, int minute) {
        calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, hour);
        calendar.set(Calendar.MINUTE, minute);
        calendar.set(Calendar.SECOND, 0);
    }


    public void setDefaultTime() {
        // calender instance set to default notification time of 18:00
        calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 18);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
    }


    public Calendar getCalendar(){
        return calendar;
    }

}
