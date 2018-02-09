package com.hoangtubot.sdvcalendar

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import com.google.android.gms.ads.*
import com.hoangtubot.sdvcalendar.Utils.AdviewHelper

import com.hoangtubot.sdvcalendar.Utils.BottomNavigationViewHelper
import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx
import com.prolificinteractive.materialcalendarview.MaterialCalendarView
import com.prolificinteractive.materialcalendarview.CalendarDay
import com.hoangtubot.sdvcalendar.Utils.CalendarViewHelper
import com.hoangtubot.sdvcalendar.decorators.OneDayDecorator
import com.hoangtubot.sdvcalendar.decorators.HighlightWeekendsDecorator
import com.hoangtubot.sdvcalendar.decorators.MySelectorDecorator

/**
 * Created by hoang on 1/18/2018.
 */
class KipAactivity:AppCompatActivity() {

    lateinit var mAdView : AdView
    lateinit var bottomNavViewBar: BottomNavigationViewEx
    var ACTIVITY_NUM: Int = 1
    lateinit var calendarViewKipA: MaterialCalendarView
    private val oneDayDecorator = OneDayDecorator()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_kipa)
        //println("Kíp A Started")
        setupBottomNavigationView()
        setupAdview()
        setupCalendarView()
    }


    private fun setupCalendarView(){
        calendarViewKipA = findViewById(R.id.calendarViewKipA)
        calendarViewKipA.setOnDateChangedListener({
            widget, date, selected ->  onDateListener(widget,date,selected)})
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

    private fun setupBottomNavigationView() {
        bottomNavViewBar = findViewById(R.id.bottomNavViewBar)
        BottomNavigationViewHelper.setupBottomNavigationView(bottomNavViewBar)
        BottomNavigationViewHelper.enableNavigation(this,bottomNavViewBar,2)
        var menu: Menu = bottomNavViewBar.getMenu()
        var menuItem: MenuItem = menu.getItem(ACTIVITY_NUM)
        menuItem.setChecked(true)
    }

    private fun toast (message: String, tag: String = KipAactivity::class.java.simpleName,length: Int=Toast.LENGTH_SHORT){
        Toast.makeText(this,"[$tag] $message",length).show()
    }

    private fun setupAdview() {
        mAdView = findViewById(R.id.adView)
        MobileAds.initialize(this, "ca-app-pub-6463279426967492~1968408247");
        val adView = AdView(this)
        adView.adSize = AdSize.BANNER
        adView.adUnitId = "ca-app-pub-6463279426967492/5548506700"
        val adRequest = AdRequest.Builder().build()
        mAdView.loadAd(adRequest)
    }

    override fun onBackPressed(){
        super.onBackPressed()
        val intent1 = Intent(this, MainActivity::class.java) //ACTIVITY_NUM = 0
        this.startActivity(intent1)
    }
}