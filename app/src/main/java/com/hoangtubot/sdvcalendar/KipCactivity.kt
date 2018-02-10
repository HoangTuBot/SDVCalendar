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
import com.hoangtubot.sdvcalendar.decorators.*
import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx
import com.prolificinteractive.materialcalendarview.CalendarDay
import com.prolificinteractive.materialcalendarview.MaterialCalendarView
import java.util.*
import java.util.concurrent.Executors

class KipCactivity :AppCompatActivity() {
    private lateinit var mAdView : AdView
    lateinit var bottomNavViewBar: BottomNavigationViewEx
    var ACTIVITY_NUM: Int = 3
    lateinit var calendarViewKipC: MaterialCalendarView
    private val oneDayDecorator = OneDayDecorator()
    private lateinit var mInterstitialAd: InterstitialAd
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_kipc)

        setupInterstitialAd()

        setupBottomNavigationView()
        setupAdview()
        setupCalendarView()
        KipCday().executeOnExecutor(Executors.newSingleThreadExecutor())
        KipCnight().executeOnExecutor(Executors.newSingleThreadExecutor())
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
    private fun toast (message: String, tag: String = KipAactivity::class.java.simpleName,length: Int= Toast.LENGTH_SHORT){
        Toast.makeText(this,"[$tag] $message",length).show()
    }

    fun setupBottomNavigationView() {
        bottomNavViewBar = findViewById(R.id.bottomNavViewBar)
        BottomNavigationViewHelper.setupBottomNavigationView(bottomNavViewBar)
        BottomNavigationViewHelper.enableNavigation(this,bottomNavViewBar,4,mInterstitialAd)
        var menu: Menu = bottomNavViewBar.getMenu()
        var menuItem: MenuItem = menu.getItem(ACTIVITY_NUM)
        menuItem.setChecked(true)
    }
    private fun setupAdview() {
        mAdView = findViewById(R.id.adViewC)
        MobileAds.initialize(this, "ca-app-pub-6463279426967492~1968408247");
        val adView = AdView(this)
        adView.adSize = AdSize.BANNER
        adView.adUnitId = "ca-app-pub-6463279426967492/5548506700"
        val adRequest = AdRequest.Builder().build()
        mAdView.loadAd(adRequest)
    }
    private fun setupCalendarView(){
        calendarViewKipC = findViewById(R.id.calendarViewKipC)
        calendarViewKipC.setOnDateChangedListener({
            widget, date, selected ->  onDateListener(widget,date,selected)})
        calendarViewKipC.setOnTitleClickListener(View.OnClickListener {
            oneDayDecorator.setDate(CalendarDay.today().date)
            calendarViewKipC.setSelectedDate(CalendarDay.today())
            calendarViewKipC.invalidateDecorators()
            calendarViewKipC.setCurrentDate(CalendarDay.today().date)
        })

        CalendarViewHelper.setupCalendarView(calendarViewKipC)
        calendarViewKipC.addDecorators(
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

    private inner class KipCday : AsyncTask<Void, Void, List<CalendarDay>>() {

        override fun doInBackground(vararg voids: Void): List<CalendarDay> {
            try {
                Thread.sleep(2000)
            } catch (e: InterruptedException) {
                e.printStackTrace()
            }

            val calendar = Calendar.getInstance()
            val KipCWorkfirstday = Workday(CalendarDay.from(2018, 0, 8), 4)

            var workdaysKipC: MutableList<Workday> = mutableListOf()
            workdaysKipC.add(KipCWorkfirstday)
            //Khoang cach giua cac ngay di lam
            val workdaybetweenlistC = intArrayOf(12,12,12,17,12,12,12,12,15,12,12,12,12,12,12,12,12,12,12,13,12,12,12,12,12,12,12,12)
            //Tong so ngay di lam 1 kip
            val workdaybetweenlistClong = intArrayOf(4,4,9,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,5,4,4,4,4,4,4,4,4,4)
            KipCWorkfirstday.calendarDay.copyTo(calendar)

            for (i in workdaybetweenlistC.indices) {
                calendar.add(Calendar.DATE, workdaybetweenlistC[i])
                val KipCWorkday = Workday(CalendarDay.from(calendar), workdaybetweenlistClong[i])
                workdaysKipC.add(KipCWorkday)
            }

            var dates: MutableList<CalendarDay> = mutableListOf()
            for (item in workdaysKipC) {
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
            calendarViewKipC.addDecorator(EventDecorator(Color.parseColor("#FF00B7F4"), calendarDays))
        }
    }

    private inner class KipCnight : AsyncTask<Void, Void, List<CalendarDay>>() {

        override fun doInBackground(vararg voids: Void): List<CalendarDay> {
            try {
                Thread.sleep(2000)
            } catch (e: InterruptedException) {
                e.printStackTrace()
            }

            val calendar = Calendar.getInstance()
            val worknightsKipC: MutableList<Workday> = mutableListOf()
            val KipCWorkfirstnight = Workday(CalendarDay.from(2018, 0, 1), 5)
            worknightsKipC.add(KipCWorkfirstnight)
            val worknightbetweenlistA = intArrayOf(13,12,12,17,12,12,12,12,13,14,12,12,12,12,12,12,12,12,12,13,12,12,12,12,12,12,12,12,12)
            val worknightbetweenlistAlong = intArrayOf(4,4,4,4,4,4,4,4,6,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4)
            KipCWorkfirstnight.calendarDay.copyTo(calendar)

            for (i in worknightbetweenlistA.indices) {
                calendar.add(Calendar.DATE, worknightbetweenlistA[i])
                val KipCWorknight = Workday(CalendarDay.from(calendar), worknightbetweenlistAlong[i])
                worknightsKipC.add(KipCWorknight)
            }

            val dates: MutableList<CalendarDay> = mutableListOf()
            for (item in worknightsKipC) {
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
            calendarViewKipC.addDecorator(EventDecorator(Color.DKGRAY, calendarDays))
        }
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
}