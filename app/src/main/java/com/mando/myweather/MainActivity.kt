package com.mando.myweather

import android.app.AlertDialog
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.ActionBar
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.mando.myweather.fragments.Alert
import com.mando.myweather.fragments.Current
import com.mando.myweather.fragments.Daily
import com.mando.myweather.fragments.Hourly
import com.mando.myweather.tabs.MainScreenTab
import com.mando.myweather.utils.PermissionChecker
import com.mando.myweather.utils.PermissionCheckerImpl

class MainActivity : AppCompatActivity(), MainScreenTab.View {

    private val TAG = "MainActivity"
    private val LOCATION_REQUEST_CODE = 99
    private var permissionChecker: PermissionChecker? = null

    lateinit var toolbar: ActionBar
    private var bottomNavigation: BottomNavigationView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        toolbar = supportActionBar!!
        initialization()
        showCurrentFragment()
        requestLocationPermission()
    }

    private fun initialization() {
        bottomNavigation = findViewById(R.id.navigationView)
        bottomNavigation?.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
        permissionChecker = PermissionCheckerImpl(applicationContext)
    }

    private val mOnNavigationItemSelectedListener =
        BottomNavigationView.OnNavigationItemSelectedListener { item ->
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

    private fun requestLocationPermission() {
        val hasAnyLocationPermissions = permissionChecker?.hasAnyLocationPermissions()
        Log.d(TAG, "hasAnyLocationPermissions: $hasAnyLocationPermissions")
        if (hasAnyLocationPermissions != null && !hasAnyLocationPermissions) {
            makeLocationPermissionRequest()
        }
        if (ActivityCompat.shouldShowRequestPermissionRationale(
                this,
                android.Manifest.permission.ACCESS_FINE_LOCATION
            )
        ) {
            val dialog = createLocationRequestDialog()
            dialog?.show()
        } else {
            makeLocationPermissionRequest()
        }
    }

    private fun createLocationRequestDialog(): AlertDialog? {
        val builder = AlertDialog.Builder(this)
        builder.setMessage("Permission to access the Location is required for this app to forecast the weather.")
            .setTitle("Permission required")

        builder.setPositiveButton(
            "OK"
        ) { dialog, id ->
            Log.i(TAG, "Clicked")
            makeLocationPermissionRequest()
        }

        return builder.create()
    }

    private fun makeLocationPermissionRequest() {
        ActivityCompat.requestPermissions(
            this,
            arrayOf(
                android.Manifest.permission.ACCESS_COARSE_LOCATION,
                android.Manifest.permission.ACCESS_FINE_LOCATION
            ),
            LOCATION_REQUEST_CODE
        )
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            LOCATION_REQUEST_CODE -> {
                if (grantResults.isEmpty() || grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(
                        applicationContext,
                        "Permission has been denied",
                        Toast.LENGTH_SHORT
                    ).show()
                } else {
                    Toast.makeText(
                        applicationContext,
                        "Permission has been granted",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }
}
