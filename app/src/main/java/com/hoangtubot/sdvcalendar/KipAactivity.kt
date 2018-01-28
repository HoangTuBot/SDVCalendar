package com.hoangtubot.sdvcalendar

import android.content.Intent
import android.os.Bundle
import android.os.Message
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import com.google.android.gms.ads.*

import com.hoangtubot.sdvcalendar.Utils.BottomNavigationViewHelper
import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx
import com.prolificinteractive.materialcalendarview.MaterialCalendarView
import com.prolificinteractive.materialcalendarview.CalendarMode
import java.util.*
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener
import android.support.annotation.NonNull
import com.hoangtubot.sdvcalendar.decorators.OneDayDecorator
import com.hoangtubot.sdvcalendar.decorators.EventDecorator
import com.hoangtubot.sdvcalendar.decorators.HighlightWeekendsDecorator
import com.hoangtubot.sdvcalendar.decorators.MySelectorDecorator
import com.hoangtubot.sdvcalendar.decorators.PublicHoliday



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
        println("Kíp A Started")
        setupBottomNavigationView()
        setupAdview()
        setupCalendarView()
    }


    fun setupCalendarView(){
        calendarViewKipA = findViewById(R.id.calendarViewKipA)
        calendarViewKipA.setOnDateChangedListener(OnDateSelectedListener() {
            widget, date, selected ->  onDateListener(widget,date,selected)})

        calendarViewKipA.state().edit()
                .setFirstDayOfWeek(Calendar.MONDAY)
                .setCalendarDisplayMode(CalendarMode.MONTHS)
                .commit()
        calendarViewKipA.setHeaderTextAppearance(R.style.TextAppearance_AppCompat_Large)
        calendarViewKipA.setDateTextAppearance(R.style.TextAppearance_AppCompat_Medium)
        calendarViewKipA.setWeekDayTextAppearance(R.style.TextAppearance_AppCompat_Medium)
        val instance = Calendar.getInstance()
        calendarViewKipA.setSelectedDate(instance.getTime())

        val instance1 = Calendar.getInstance()
        instance1.set(instance1.get(Calendar.YEAR), Calendar.JANUARY, 1)
        val instance2 = Calendar.getInstance()
        instance2.set(instance2.get(Calendar.YEAR), Calendar.DECEMBER, 31)
        calendarViewKipA.state().edit()
                .setMinimumDate(instance1.getTime())
                .setMaximumDate(instance2.getTime())
                .commit()
        calendarViewKipA.addDecorators(
                HighlightWeekendsDecorator(),
                MySelectorDecorator(this),
                oneDayDecorator
        )
    }

    fun onDateListener(widget: MaterialCalendarView, date: CalendarDay, selected: Boolean) {
        //If you change a decorate, you need to invalidate decorators
        oneDayDecorator.setDate(date.date)
        widget.invalidateDecorators()
    }

    fun setupBottomNavigationView() {
        bottomNavViewBar = findViewById(R.id.bottomNavViewBar)
        BottomNavigationViewHelper.setupBottomNavigationView(bottomNavViewBar)
        BottomNavigationViewHelper.enableNavigation(this,bottomNavViewBar,2)
        var menu: Menu = bottomNavViewBar.getMenu()
        var menuItem: MenuItem = menu.getItem(ACTIVITY_NUM)
        menuItem.setChecked(true)
    }

    fun toast (message: String, tag: String = KipAactivity::class.java.simpleName,length: Int=Toast.LENGTH_SHORT){
        Toast.makeText(this,"[$tag] $message",length).show()
    }

    fun setupAdview() {
        MobileAds.initialize(this, "ca-app-pub-6463279426967492~1968408247");
        val adView = AdView(this)
        adView.adSize = AdSize.BANNER
        adView.adUnitId = "ca-app-pub-6463279426967492/5548506700"
        mAdView = findViewById(R.id.adView)
        val adRequest = AdRequest.Builder().build()
        mAdView.loadAd(adRequest)

    }

    override fun onBackPressed(){
        super.onBackPressed()
        val intent1 = Intent(this, MainActivity::class.java) //ACTIVITY_NUM = 0
        this.startActivity(intent1)
    }
}