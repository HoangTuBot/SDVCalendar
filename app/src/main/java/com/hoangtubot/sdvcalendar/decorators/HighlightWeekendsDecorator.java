package com.hoangtubot.sdvcalendar.decorators;

import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.text.style.StyleSpan;

import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.DayViewDecorator;
import com.prolificinteractive.materialcalendarview.DayViewFacade;
import java.util.Arrays;
import java.util.Calendar;

import static java.security.AccessController.getContext;


/**
 * Highlight Saturdays and Sundays with a background
 */
public class HighlightWeekendsDecorator implements DayViewDecorator {

    private final Calendar calendar = Calendar.getInstance();

    public HighlightWeekendsDecorator() {

    }

    @Override
    public boolean shouldDecorate(CalendarDay day) {
        day.copyTo(calendar);
        int weekDay = calendar.get(Calendar.DAY_OF_WEEK);
        int weekNum = calendar.get(Calendar.WEEK_OF_YEAR);
        String SweekNum = Integer.toString(weekNum);
        String[] workSat = {"2", "4", "6", "11", "14", "16", "18", "20", "22", "24", "26", "28", "30", "32", "34", "36", "38", "40", "42", "44", "46", "48", "50", "52"};
        boolean i = Arrays.asList(workSat).contains(SweekNum);
        return weekDay == Calendar.SATURDAY && i || weekDay == Calendar.SUNDAY && weekNum != 7;
    }

    @Override
    public void decorate(DayViewFacade view) {
        view.addSpan(new ForegroundColorSpan(Color.RED));
        view.addSpan(new StyleSpan(Typeface.BOLD));
    }
}
