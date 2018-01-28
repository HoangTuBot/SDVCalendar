package com.hoangtubot.sdvcalendar.decorators;

import android.graphics.Typeface;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;

import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.DayViewDecorator;
import com.prolificinteractive.materialcalendarview.DayViewFacade;
import java.util.Collection;
import java.util.HashSet;

/**
 * Created by hoang on 1/12/2018.
 */

public class PublicHoliday implements DayViewDecorator {


    private int color;
    private HashSet<CalendarDay> dates;

    public PublicHoliday(int color, Collection<CalendarDay> dates) {
        this.color = color;
        this.dates = new HashSet<>(dates);
    }

    @Override
    public boolean shouldDecorate(CalendarDay day) {
        return dates.contains(day);
    }

    @Override
    public void decorate(DayViewFacade view) {
        view.addSpan(new ForegroundColorSpan(color));
        view.addSpan(new StyleSpan(Typeface.BOLD));
    }
}
