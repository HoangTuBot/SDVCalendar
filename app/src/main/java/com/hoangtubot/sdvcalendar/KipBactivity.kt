package com.hoangtubot.sdvcalendar

import android.content.Intent
import android.graphics.Color
import android.os.AsyncTask
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdSize
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.MobileAds
import com.hoangtubot.sdvcalendar.Utils.BottomNavigationViewHelper
import com.hoangtubot.sdvcalendar.Utils.CalendarViewHelper
import com.hoangtubot.sdvcalendar.Utils.CalendarViewHelper.setupCalendarView
import com.hoangtubot.sdvcalendar.decorators.*
import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx
import com.prolificinteractive.materialcalendarview.CalendarDay
import com.prolificinteractive.materialcalendarview.MaterialCalendarView
import java.util.*
import java.util.concurrent.Executors

class KipBactivity :AppCompatActivity() {
    private lateinit var mAdView : AdView
    lateinit var bottomNavViewBar: BottomNavigationViewEx
    var ACTIVITY_NUM: Int = 2
    lateinit var calendarViewKipB: MaterialCalendarView
    private val oneDayDecorator = OneDayDecorator()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_kipb)

        setupBottomNavigationView()
        setupAdview()
        setupCalendarView()

        PublicHolidays().executeOnExecutor(Executors.newSingleThreadExecutor())
        SamsungHolidays().executeOnExecutor(Executors.newSingleThreadExecutor())

    }
    private fun toast (message: String, tag: String = KipAactivity::class.java.simpleName,length: Int= Toast.LENGTH_SHORT){
        Toast.makeText(this,"[$tag] $message",length).show()
    }
    fun setupBottomNavigationView() {
        bottomNavViewBar = findViewById(R.id.bottomNavViewBar)
        BottomNavigationViewHelper.setupBottomNavigationView(bottomNavViewBar)
        BottomNavigationViewHelper.enableNavigation(this,bottomNavViewBar,3)
        var menu: Menu = bottomNavViewBar.getMenu()
        var menuItem: MenuItem = menu.getItem(ACTIVITY_NUM)
        menuItem.setChecked(true)
    }
    private fun setupAdview() {
        mAdView = findViewById(R.id.adViewB)
        MobileAds.initialize(this, "ca-app-pub-6463279426967492~1968408247");
        val adView = AdView(this)
        adView.adSize = AdSize.BANNER
        adView.adUnitId = "ca-app-pub-6463279426967492/5548506700"
        val adRequest = AdRequest.Builder().build()
        mAdView.loadAd(adRequest)
    }
    private fun setupCalendarView(){
        calendarViewKipB = findViewById(R.id.calendarViewKipB)
        calendarViewKipB.setOnDateChangedListener({
            widget, date, selected ->  onDateListener(widget,date,selected)})
        CalendarViewHelper.setupCalendarView(calendarViewKipB)
        calendarViewKipB.addDecorators(
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
            calendarViewKipB.addDecorator(PublicHoliday(Color.parseColor("#FF00C20A"), calendarDays))
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
            calendarViewKipB.addDecorator(PublicHoliday(Color.parseColor("#FFFF7118"), calendarDays))
        }
    }

}