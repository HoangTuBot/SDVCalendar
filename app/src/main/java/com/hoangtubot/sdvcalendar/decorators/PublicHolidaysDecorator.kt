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
class PublicHolidaysDecorator : DayViewDecorator {

    private val calendar = Calendar.getInstance()

    override fun shouldDecorate(day: CalendarDay): Boolean {

        val PublicHolidays: MutableList<Workday> = mutableListOf()
        val firstPublicHolidays = Workday(CalendarDay.from(2018, 0, 1), 1)
        PublicHolidays.add(firstPublicHolidays)

        val PublicHolidaystbetweenlist = intArrayOf(44, 70, 5, 125)
        val PublicHolidaysbetweenlistlong = intArrayOf(5, 1, 2, 1)
        firstPublicHolidays.calendarDay.copyTo(calendar)

        for (i in PublicHolidaystbetweenlist.indices) {
            calendar.add(Calendar.DATE, PublicHolidaystbetweenlist[i])
            val kipAWorknight = Workday(CalendarDay.from(calendar), PublicHolidaysbetweenlistlong[i])
            PublicHolidays.add(kipAWorknight)
        }

        val dates: MutableList<CalendarDay> = mutableListOf()
        for (item in PublicHolidays) {
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
        view.addSpan(ForegroundColorSpan(Color.parseColor("#FF00C20A")))
        view.addSpan(StyleSpan(Typeface.BOLD))
    }
}
