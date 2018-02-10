package com.hoangtubot.sdvcalendar

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import com.google.android.gms.ads.*

import com.hoangtubot.sdvcalendar.Utils.BottomNavigationViewHelper
import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx
import com.prolificinteractive.materialcalendarview.MaterialCalendarView
import com.prolificinteractive.materialcalendarview.CalendarDay
import com.hoangtubot.sdvcalendar.Utils.CalendarViewHelper
import com.hoangtubot.sdvcalendar.decorators.OneDayDecorator
import com.hoangtubot.sdvcalendar.decorators.HighlightWeekendsDecorator
import com.hoangtubot.sdvcalendar.decorators.MySelectorDecorator
import android.graphics.Color.parseColor
import com.hoangtubot.sdvcalendar.decorators.EventDecorator
import com.hoangtubot.sdvcalendar.decorators.Workday
import android.os.AsyncTask
import android.view.View
import java.util.*
import java.util.concurrent.Executors
import com.hoangtubot.sdvcalendar.decorators.PublicHoliday

class KipAactivity:AppCompatActivity() {
    private lateinit var mAdView : AdView
    lateinit var bottomNavViewBar: BottomNavigationViewEx
    var ACTIVITY_NUM: Int = 1
    lateinit var calendarViewKipA: MaterialCalendarView
    private val oneDayDecorator = OneDayDecorator()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_kipa)

        setupBottomNavigationView()
        setupAdview()
        setupCalendarView()
        KipAday().executeOnExecutor(Executors.newSingleThreadExecutor())
        KipAnight().executeOnExecutor(Executors.newSingleThreadExecutor())
        PublicHolidays().executeOnExecutor(Executors.newSingleThreadExecutor())
        SamsungHolidays().executeOnExecutor(Executors.newSingleThreadExecutor())
    }
    private fun toast (message: String, tag: String = KipAactivity::class.java.simpleName,length: Int=Toast.LENGTH_SHORT){
        Toast.makeText(this,"[$tag] $message",length).show()
    }
    private fun setupBottomNavigationView() {
        bottomNavViewBar = findViewById(R.id.bottomNavViewBar)
        BottomNavigationViewHelper.setupBottomNavigationView(bottomNavViewBar)
        BottomNavigationViewHelper.enableNavigation(this,bottomNavViewBar,2)
        val menu: Menu = bottomNavViewBar.getMenu()
        val menuItem: MenuItem = menu.getItem(ACTIVITY_NUM)
        menuItem.setChecked(true)
    }
    private fun setupAdview() {
        mAdView = findViewById(R.id.adViewA)
        MobileAds.initialize(this, "ca-app-pub-6463279426967492~1968408247");
        val adView = AdView(this)
        adView.adSize = AdSize.BANNER
        adView.adUnitId = "ca-app-pub-6463279426967492/5548506700"
        val adRequest = AdRequest.Builder().build()
        mAdView.loadAd(adRequest)
    }
    private fun setupCalendarView(){
        calendarViewKipA = findViewById(R.id.calendarViewKipA)
        calendarViewKipA.setOnDateChangedListener({
            widget, date, selected ->  onDateListener(widget,date,selected)})
        calendarViewKipA.setOnTitleClickListener(View.OnClickListener {
            oneDayDecorator.setDate(CalendarDay.today().date)
            calendarViewKipA.setSelectedDate(CalendarDay.today())
            calendarViewKipA.invalidateDecorators()
            calendarViewKipA.setCurrentDate(CalendarDay.today().date)
        })

        CalendarViewHelper.setupCalendarView(calendarViewKipA)
        calendarViewKipA.addDecorators(
                HighlightWeekendsDecorator(),
                MySelectorDecorator(this),
                oneDayDecorator
        )
    }
    private fun onDateListener(widget: MaterialCalendarView, date: CalendarDay, selected: Boolean) {
        //If you change a decorate, you need to invalidate decorators
        oneDayDecorator.setDate(date.date)
        widget.invalidateDecorators()
    }
    override fun onBackPressed(){
        super.onBackPressed()
        val intent1 = Intent(this, MainActivity::class.java) //ACTIVITY_NUM = 0
        this.startActivity(intent1)
    }

    private inner class KipAday : AsyncTask<Void, Void, List<CalendarDay>>() {

        override fun doInBackground(vararg voids: Void): List<CalendarDay> {
            try {
                Thread.sleep(2000)
            } catch (e: InterruptedException) {
                e.printStackTrace()
            }

            val calendar = Calendar.getInstance()
            val kipAWorkfirstday = Workday(CalendarDay.from(2018, 0, 2), 2)

            var workdaysKipA: MutableList<Workday> = mutableListOf()
            workdaysKipA.add(kipAWorkfirstday)
            //Khoang cach giua cac ngay di lam
            val workdaybetweenlistA = intArrayOf(10, 12, 12, 17, 12, 12, 12, 12, 12, 15, 12, 12, 12, 12, 12, 12, 12, 12, 12, 13, 12, 12, 12, 12, 12, 12, 12, 12, 12)
            //Tong so ngay di lam 1 kip
            val workdaybetweenlistAlong = intArrayOf(4, 4, 4, 4, 4, 4, 4, 4, 5, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4)
            kipAWorkfirstday.calendarDay.copyTo(calendar)

            for (i in workdaybetweenlistA.indices) {
                calendar.add(Calendar.DATE, workdaybetweenlistA[i])
                val kipAWorkday = Workday(CalendarDay.from(calendar), workdaybetweenlistAlong[i])
                workdaysKipA.add(kipAWorkday)
            }

            var dates: MutableList<CalendarDay> = mutableListOf()
            for (item in workdaysKipA) {
                var day = item.calendarDay
                day.copyTo(calendar)
                for (j in 0 until item.daylong) {
                    day = CalendarDay.from(calendar)
                    dates.add(day)
                    calendar.add(Calendar.DATE, 1)
                }
            }
            return dates
        }

        override fun onPostExecute(calendarDays: List<CalendarDay>) {
            super.onPostExecute(calendarDays)

            if (isFinishing) {
                return
            }
            calendarViewKipA.addDecorator(EventDecorator(Color.parseColor("#FF00B7F4"), calendarDays))
        }
    }

    private inner class KipAnight : AsyncTask<Void, Void, List<CalendarDay>>() {

        override fun doInBackground(vararg voids: Void): List<CalendarDay> {
            try {
                Thread.sleep(2000)
            } catch (e: InterruptedException) {
                e.printStackTrace()
            }

            val calendar = Calendar.getInstance()
            val worknightsKipA: MutableList<Workday> = mutableListOf()
            val kipAWorkfirstnight = Workday(CalendarDay.from(2018, 0, 6), 4)
            worknightsKipA.add(kipAWorkfirstnight)
            val worknightbetweenlistA = intArrayOf(12, 12, 12, 17, 12, 12, 12, 12, 15, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 13, 12, 12, 12, 12, 12, 12, 12, 12, 12)
            val worknightbetweenlistAlong = intArrayOf(4, 4, 9, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 5, 4, 4, 4, 4, 4, 4, 4, 4, 4, 3)
            kipAWorkfirstnight.calendarDay.copyTo(calendar)

            for (i in worknightbetweenlistA.indices) {
                calendar.add(Calendar.DATE, worknightbetweenlistA[i])
                val kipAWorknight = Workday(CalendarDay.from(calendar), worknightbetweenlistAlong[i])
                worknightsKipA.add(kipAWorknight)
            }

            val dates: MutableList<CalendarDay> = mutableListOf()
            for (item in worknightsKipA) {
                var day = item.calendarDay
                day.copyTo(calendar)
                for (j in 0 until item.daylong) {
                    day = CalendarDay.from(calendar)
                    dates.add(day)
                    calendar.add(Calendar.DATE, 1)
                }
            }
            return dates
        }

        override fun onPostExecute(calendarDays: List<CalendarDay>) {
            super.onPostExecute(calendarDays)

            if (isFinishing) {
                return
            }
            calendarViewKipA.addDecorator(EventDecorator(Color.DKGRAY, calendarDays))
        }
    }

    private inner class PublicHolidays : AsyncTask<Void, Void, List<CalendarDay>>() {

        override fun doInBackground(vararg voids: Void): List<CalendarDay> {
            try {
                Thread.sleep(2000)
            } catch (e: InterruptedException) {
                e.printStackTrace()
            }

            val calendar = Calendar.getInstance()
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
            return dates
        }

        override fun onPostExecute(calendarDays: List<CalendarDay>) {
            super.onPostExecute(calendarDays)
            if (isFinishing) {
                return
            }
            calendarViewKipA.addDecorator(PublicHoliday(Color.parseColor("#FF00C20A"), calendarDays))
        }
    }

    private inner class SamsungHolidays : AsyncTask<Void, Void, List<CalendarDay>>() {

        override fun doInBackground(vararg voids: Void): List<CalendarDay> {
            try {
                Thread.sleep(2000)
            } catch (e: InterruptedException) {
                e.printStackTrace()
            }

            val calendar = Calendar.getInstance()
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
            return dates
        }

        override fun onPostExecute(calendarDays: List<CalendarDay>) {
            super.onPostExecute(calendarDays)
            if (isFinishing) {
                return
            }
            calendarViewKipA.addDecorator(PublicHoliday(Color.parseColor("#FFFF7118"), calendarDays))
        }
    }

}