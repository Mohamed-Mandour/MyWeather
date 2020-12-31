package com.mando.weatherforecast.base

import android.app.AlertDialog
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import com.mando.weatherforecast.R
import com.mando.weatherforecast.adpater.MainActivityTabPagerAdapter
import com.mando.weatherforecast.location.FusedLocationDataStore
import com.mando.weatherforecast.location.LocationDataStore
import com.mando.weatherforecast.utils.AndroidPermissionChecker
import com.mando.weatherforecast.utils.PermissionExaminer
import kotlinx.android.synthetic.main.activity_main.*
import timber.log.Timber

private const val LOCATION_REQUEST_CODE = 99

class MainActivity : AppCompatActivity() {
    private val locationDataStore: LocationDataStore?
        get() = FusedLocationDataStore.getInstance(application)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        requestLocationPermission()
        setToolBar()
        setViewPager()

    }

    private fun setToolBar() {
        mainActivityToolbar.title = ""
        mainActivityToolbar.subtitle = ""
        val toolbarTitle = findViewById<TextView>(R.id.toolbarTitle)
        toolbarTitle.text = getString(R.string.weather_forecast)
        setSupportActionBar(mainActivityToolbar)
    }

    private fun setViewPager() {
        mainActivityViewPager.adapter =
            MainActivityTabPagerAdapter(
                this,
                supportFragmentManager
            )
        mainActivityViewPager.offscreenPageLimit = 2
        mainActivityTabs.setupWithViewPager(mainActivityViewPager)
    }

    private fun requestLocationPermission() {
        val permissionChecker: PermissionExaminer = AndroidPermissionChecker(application)
        val hasAnyLocationPermissions = permissionChecker.hasAnyLocationPermissions
        Timber.d( "hasAnyLocationPermissions: $hasAnyLocationPermissions")
        if (!hasAnyLocationPermissions) {
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
        ) { _, _ ->
            Timber.i("Clicked")
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
                    locationDataStore?.location
                }
            }
        }
    }
}
