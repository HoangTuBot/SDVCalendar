package com.hoangtubot.sdvcalendar.Utils

import com.hoangtubot.sdvcalendar.R
import com.prolificinteractive.materialcalendarview.CalendarMode
import com.prolificinteractive.materialcalendarview.MaterialCalendarView
import java.util.*

/**
 * Created by Bot on 2/9/2018.
 */
object CalendarViewHelper {
    fun setupCalendarView(calendarView: MaterialCalendarView){
        calendarView.state().edit()
                .setFirstDayOfWeek(Calendar.MONDAY)
                .setCalendarDisplayMode(CalendarMode.MONTHS)
                .commit()
        calendarView.setHeaderTextAppearance(R.style.TextAppearance_AppCompat_Large)
        calendarView.setDateTextAppearance(R.style.TextAppearance_AppCompat_Medium)
        calendarView.setWeekDayTextAppearance(R.style.TextAppearance_AppCompat_Medium)
        val instance = Calendar.getInstance()
        calendarView.setSelectedDate(instance.getTime())

        val instance1 = Calendar.getInstance()
        instance1.set(instance1.get(Calendar.YEAR), Calendar.JANUARY, 1)
        val instance2 = Calendar.getInstance()
        instance2.set(instance2.get(Calendar.YEAR), Calendar.DECEMBER, 31)
        calendarView.state().edit()
                .setMinimumDate(instance1.getTime())
                .setMaximumDate(instance2.getTime())
                .commit()
    }
}