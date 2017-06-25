package com.example.mainaccount.inspire;

import java.util.Calendar;

/**
 * Created by mainaccount on 25/06/2017.
 */

public class SetTime {
    private Calendar calendar;

    public SetTime() {
        // calender instance set to default notification time of 18:00
        calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 18);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
    }

    public SetTime(int hour, int minute) {
        calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, hour);
        calendar.set(Calendar.MINUTE, minute);
        calendar.set(Calendar.SECOND, 0);
    }
    public Calendar getCalendar(){
        return calendar;
    }

}
