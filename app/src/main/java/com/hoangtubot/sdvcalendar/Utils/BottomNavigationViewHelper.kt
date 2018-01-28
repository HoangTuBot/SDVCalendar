package com.hoangtubot.sdvcalendar.Utils

import android.content.Context
import android.content.Intent
import android.support.design.widget.BottomNavigationView
import android.util.Log
import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx
import org.jetbrains.annotations.Nullable
import android.view.MenuItem
import com.hoangtubot.sdvcalendar.*
import kotlinx.android.synthetic.main.activity_main.*

/**
 * Created by hoang on 1/17/2018.
 */

object BottomNavigationViewHelper {
    /*
    BottomNavigationView setup
    */
    fun setupBottomNavigationView(bottomNavigationViewEx: BottomNavigationViewEx) {
        bottomNavigationViewEx.enableAnimation(false)
        bottomNavigationViewEx.enableItemShiftingMode(false)
        bottomNavigationViewEx.enableShiftingMode(false)
    }
    fun enableNavigation(context: Context, view: BottomNavigationView, page: Int) {
        view.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.menu_bus -> {
                    if (page != 1) {
                        val intent1 = Intent(context, MainActivity::class.java) //ACTIVITY_NUM = 0
                        context.startActivity(intent1)
                    }
                    else println("You're at this page")
                }
                R.id.menu_a -> {
                    if (page != 2){
                        val intent2 = Intent(context, KipAactivity::class.java)//ACTIVITY_NUM = 1
                        context.startActivity(intent2)
                    }
                    else println("You're at this page")
                }
                R.id.menu_b -> {
                    if (page != 3) {
                        val intent3 = Intent(context, KipBactivity::class.java)//ACTIVITY_NUM = 2
                        context.startActivity(intent3)
                    }
                    else println("You're at this page")
                }
                R.id.menu_c -> {
                    if (page != 4) {
                        val intent4 = Intent(context, KipCactivity::class.java)//ACTIVITY_NUM = 4
                        context.startActivity(intent4)
                    }
                    else println("You're at this page")
                }
            }
            false
        }
    }
}
