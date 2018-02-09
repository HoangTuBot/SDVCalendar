package com.hoangtubot.sdvcalendar.decorators;

import com.prolificinteractive.materialcalendarview.CalendarDay;

import java.util.Set;

/**
 * Created by hoang on 1/11/2018.
 */

public class Workday {

    public int year;
    public int month;
    public int day;
    public int daylong;
    public CalendarDay calendarDay;
    public Workday (CalendarDay calendarDay, int daylong)
    {
        this.calendarDay=calendarDay;
        this.year=calendarDay.getYear();
        this.month=calendarDay.getMonth();
        this.day=calendarDay.getDay();
        this.daylong=daylong;
    }
}
