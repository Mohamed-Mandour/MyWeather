package com.mando.myweather

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.ActionBar
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.mando.myweather.fragments.Alert
import com.mando.myweather.fragments.Current
import com.mando.myweather.fragments.Daily
import com.mando.myweather.fragments.Hourly
import com.mando.myweather.tabs.MainScreenTab

class MainActivity : AppCompatActivity(), MainScreenTab.View {

    lateinit var toolbar: ActionBar
    private var bottomNavigation: BottomNavigationView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        toolbar = supportActionBar!!
        showCurrentFragment()


        bottomNavigation = findViewById(R.id.navigationView)
        bottomNavigation?.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
    }

    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.currentTap -> {
                showCurrentFragment()
                return@OnNavigationItemSelectedListener true
            }
            R.id.hourlyTap -> {
                showHourlyFragment()
                return@OnNavigationItemSelectedListener true

            }
            R.id.dailyTap -> {
                showDailyFragment()
                return@OnNavigationItemSelectedListener true

            }
            R.id.alertTap -> {
                showAlertFragment()
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }

    override fun showCurrentFragment() {
        val currentFragment = Current.newInstance()
        openFragment(currentFragment)

    }

    override fun showHourlyFragment() {
        val hourlyFragment = Hourly.newInstance()
        openFragment(hourlyFragment)
    }

    override fun showDailyFragment() {
        val dailyFragment = Daily.newInstance()
        openFragment(dailyFragment)
    }

    override fun showAlertFragment() {
        val alertFragment = Alert.newInstance()
        openFragment(alertFragment)
    }

    private fun openFragment(fragment: Fragment) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.bottomNavContainer, fragment)
        transaction.commit()
    }
}
