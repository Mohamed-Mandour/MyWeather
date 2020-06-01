package com.mando.myweather

import android.app.AlertDialog
import android.content.pm.PackageManager
import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import com.mando.MainActivityTabPagerAdapter
import com.mando.myweather.background.ForecastJson
import com.mando.myweather.fragments.CurrentFragment
import com.mando.myweather.impl.ParseForecast
import com.mando.myweather.location.FusedLocationDataStore
import com.mando.myweather.location.LocationDataStore
import com.mando.myweather.model.Current
import com.mando.myweather.utils.AndroidPermissionChecker
import com.mando.myweather.utils.PermissionExaminer
import kotlinx.android.synthetic.main.activity_main.*
import timber.log.Timber
import java.lang.ref.WeakReference

private const val TAG = "MainActivity"
private const val LOCATION_REQUEST_CODE = 99

class MainActivity : AppCompatActivity() {

    private val locationDataStore: LocationDataStore?
        get() = FusedLocationDataStore.getInstance(application)

    private var current: Current? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setToolBar()
        setViewPager()
        requestLocationPermission()
        FetchForecastTask(this).execute()
    }

    private fun setToolBar() {
        mainActivityToolbar.title = ""
        mainActivityToolbar.subtitle = ""
        val toolbarTitle = findViewById<TextView>(R.id.toolbarTitle)
        toolbarTitle.text = getString(R.string.weather_forecast)
        setSupportActionBar(mainActivityToolbar)
    }

    private fun setViewPager() {
        mainActivityViewPager.adapter = MainActivityTabPagerAdapter(this, supportFragmentManager)
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

    private class FetchForecastTask(val mainActivity: MainActivity) :
        AsyncTask<Void?, Void?, String>() {

        private val activityReference: WeakReference<MainActivity> =
            WeakReference(mainActivity)

        override fun doInBackground(vararg params: Void?): String? {
            return activityReference.let { mainActivity.let { it1 -> ForecastJson(it1).getForecastJson() } }
        }

        override fun onPostExecute(result: String?) {
            super.onPostExecute(result)
            val parseForecast = ParseForecast(result)
            val forecast = parseForecast.parseForecastJson()
            val current = forecast.getCurrent()
            CurrentFragment.newInstance(
                mainActivity.supportFragmentManager.beginTransaction(),
                current
            )
        }
    }
}
