package com.hoangtubot.sdvcalendar

import android.content.Intent
import android.graphics.Color
import android.os.AsyncTask
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import com.google.android.gms.ads.*
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
    private lateinit var mInterstitialAd: InterstitialAd

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_kipb)
        setupInterstitialAd()
        setupBottomNavigationView()
        setupAdview()
        setupCalendarView()
        KipBday().executeOnExecutor(Executors.newSingleThreadExecutor())
        KipBnight().executeOnExecutor(Executors.newSingleThreadExecutor())
    }
    private fun setupInterstitialAd(){
        mInterstitialAd = InterstitialAd(this)
        mInterstitialAd.adUnitId = "ca-app-pub-6463279426967492/6473919021"
        mInterstitialAd.adListener = object : AdListener() {
            override fun onAdClosed() {
                mInterstitialAd.loadAd(AdRequest.Builder().build())
            }
            override fun onAdLoaded() {
                toast("Ad Loaded")
            }
        }
    }
    private fun toast (message: String, tag: String = KipBactivity::class.java.simpleName,length: Int= Toast.LENGTH_SHORT){
        Toast.makeText(this,"[$tag] $message",length).show()
    }
    fun setupBottomNavigationView() {
        bottomNavViewBar = findViewById(R.id.bottomNavViewBar)
        BottomNavigationViewHelper.setupBottomNavigationView(bottomNavViewBar)
        BottomNavigationViewHelper.enableNavigation(this,bottomNavViewBar,3,mInterstitialAd)
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
        calendarViewKipB.setOnTitleClickListener(View.OnClickListener {
            oneDayDecorator.setDate(CalendarDay.today().date)
            calendarViewKipB.setSelectedDate(CalendarDay.today())
            calendarViewKipB.invalidateDecorators()
            calendarViewKipB.setCurrentDate(CalendarDay.today().date)
        })
        CalendarViewHelper.setupCalendarView(calendarViewKipB)
        calendarViewKipB.addDecorators(
                HighlightWeekendsDecorator(),
                PublicHolidaysDecorator(),
                SamsungHolidaysDecorator(),
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
        if (mInterstitialAd.isLoaded()) {
            mInterstitialAd.show()
        } else {
        }
    }
    private inner class KipBday : AsyncTask<Void, Void, List<CalendarDay>>() {
        override fun doInBackground(vararg voids: Void): List<CalendarDay> {
            try {
                Thread.sleep(2000)
            } catch (e: InterruptedException) {
                e.printStackTrace()
            }

            val calendar = Calendar.getInstance()
            val kipBWorkfirstday = Workday(CalendarDay.from(2018, 0, 4), 4)

            var workdaysKipB: MutableList<Workday> = mutableListOf()
            workdaysKipB.add(kipBWorkfirstday)
            //Khoang cach giua cac ngay di lam
            val workdaybetweenlistA = intArrayOf(12,12,12,17,12,12,12,12,13,14,12,12,12,12,12,12,12,12,12,13,12,12,12,12,12,12,12)
            //Tong so ngay di lam 1 kip
            val workdaybetweenlistAlong = intArrayOf(4,4,4,4,4,4,4,4,4,6,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,5)
            kipBWorkfirstday.calendarDay.copyTo(calendar)

            for (i in workdaybetweenlistA.indices) {
                calendar.add(Calendar.DATE, workdaybetweenlistA[i])
                val kipBWorkday = Workday(CalendarDay.from(calendar), workdaybetweenlistAlong[i])
                workdaysKipB.add(kipBWorkday)
            }

            var dates: MutableList<CalendarDay> = mutableListOf()
            for (item in workdaysKipB) {
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
            calendarViewKipB.addDecorator(EventDecorator(Color.parseColor("#FF00B7F4"), calendarDays))
        }
    }

    private inner class KipBnight : AsyncTask<Void, Void, List<CalendarDay>>() {

        override fun doInBackground(vararg voids: Void): List<CalendarDay> {
            try {
                Thread.sleep(2000)
            } catch (e: InterruptedException) {
                e.printStackTrace()
            }

            val calendar = Calendar.getInstance()
            val worknightsKipB: MutableList<Workday> = mutableListOf()
            val kipAWorkfirstnight = Workday(CalendarDay.from(2018, 0, 10), 4)
            worknightsKipB.add(kipAWorkfirstnight)
            val worknightbetweenlistB = intArrayOf(12,12,17,12,12,12,12,12,15,12,12,12,12,12,12,12,12,12,13,12,12,12,12,12,12,12,12,12)
            val worknightbetweenlistBlong = intArrayOf(4,4,4,4,4,4,4,5,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4)
            kipAWorkfirstnight.calendarDay.copyTo(calendar)

            for (i in worknightbetweenlistB.indices) {
                calendar.add(Calendar.DATE, worknightbetweenlistB[i])
                val kipAWorknight = Workday(CalendarDay.from(calendar), worknightbetweenlistBlong[i])
                worknightsKipB.add(kipAWorknight)
            }

            val dates: MutableList<CalendarDay> = mutableListOf()
            for (item in worknightsKipB) {
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
            calendarViewKipB.addDecorator(EventDecorator(Color.DKGRAY, calendarDays))
        }
    }

}