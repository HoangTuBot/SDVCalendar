package com.hoangtubot.sdvcalendar.decorators

import android.graphics.Color
import android.graphics.Typeface
import android.text.style.ForegroundColorSpan
import android.text.style.StyleSpan

import com.prolificinteractive.materialcalendarview.CalendarDay
import com.prolificinteractive.materialcalendarview.DayViewDecorator
import com.prolificinteractive.materialcalendarview.DayViewFacade

import java.util.Arrays
import java.util.Calendar


/**
 * Highlight Saturdays and Sundays with a background
 */
class SamsungHolidaysDecorator : DayViewDecorator {

    private val calendar = Calendar.getInstance()

    override fun shouldDecorate(day: CalendarDay): Boolean {
        val SamsungHolidays: MutableList<Workday> = mutableListOf()
        val SamsungHoliday = Workday(CalendarDay.from(2018, 1, 19), 4)
        val SamsungHoliday2 = Workday(CalendarDay.from(2018, 11, 31), 1)
        SamsungHolidays.add(SamsungHoliday)
        SamsungHolidays.add(SamsungHoliday2)

        val dates: MutableList<CalendarDay> = mutableListOf()
        for (item in SamsungHolidays) {
            var day = item.calendarDay
            day.copyTo(calendar)
            for (j in 0 until item.daylong) {
                day = CalendarDay.from(calendar)
                dates.add(day)
                calendar.add(Calendar.DATE, 1)
            }
        }
        return dates.contains(day)
    }

    override fun decorate(view: DayViewFacade) {
        view.addSpan(ForegroundColorSpan(Color.parseColor("#FFFF7118")))
        view.addSpan(StyleSpan(Typeface.BOLD))
    }
}
