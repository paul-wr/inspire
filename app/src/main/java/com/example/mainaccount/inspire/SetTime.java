package com.example.mainaccount.inspire;

import java.util.Calendar;

/**
 *  Classname: SetTime.java
 *  Version 1
 *  Date: 25 Jun 2017
 *  @author Paul Wrenn, x15020029
 */
public class SetTime {
    // declare static calendar to store time
    private static Calendar calendar;

    public SetTime(){
    }

    // override constructor with int args for setting time
    public SetTime(int hour, int minute) {
        calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, hour);
        calendar.set(Calendar.MINUTE, minute);
        calendar.set(Calendar.SECOND, 0);
    }

    // method to allow for a default time setting
    public void setDefaultTime() {
        // calender instance set to default notification time of 18:00
        calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 18);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
    }

    // calender accessor method
    public Calendar getCalendar(){
        return calendar;
    }

    // return long variable of calendar
    public long getTime(){
        return calendar.getTimeInMillis();
    }

}
