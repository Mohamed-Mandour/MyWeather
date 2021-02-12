package com.mando.weatherforecast.base

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.IntentFilter
import android.content.pm.PackageManager
import android.net.ConnectivityManager
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.google.android.gms.location.*
import com.mando.weatherforecast.R
import com.mando.weatherforecast.adpater.MainActivityTabPagerAdapter
import com.mando.weatherforecast.database.ForecastDao
import com.mando.weatherforecast.location.DeviceLocation
import com.mando.weatherforecast.location.FusedLocationDataStore
import com.mando.weatherforecast.network.NetworkChangeReceiver
import com.mando.weatherforecast.network.RetrofitClient
import com.mando.weatherforecast.utils.AndroidPermissionChecker
import com.mando.weatherforecast.utils.NoConnectionDialog
import com.mando.weatherforecast.utils.PermissionExaminer
import com.mando.weatherforecast.utils.makeToast
import kotlinx.android.synthetic.main.activity_main.*
import timber.log.Timber


private const val NO_CONNECTION_DIALOG = "NoConnectionDialog"

private const val LOCATION_REQUEST_CODE = 99

class MainActivity : AppCompatActivity(), NetworkChangeReceiver.ConnectivityReceiverListener {

    private var mFusedLocationClient: FusedLocationDataStore? = null
    private var receiver: NetworkChangeReceiver? =  null
    private val forecastDao: ForecastDao = db.forecastDao()
    private val retrofitClient = RetrofitClient()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setToolBar()
        setViewPager()
        receiver = NetworkChangeReceiver()
        mFusedLocationClient = FusedLocationDataStore.getInstance(applicationContext)
        requestLocationPermission()
    }

    override fun onResume() {
        super.onResume()
        NetworkChangeReceiver.connectivityReceiverListener = this
        registerReceiver(receiver, IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION))
    }

    override fun onPause() {
        super.onPause()
        unregisterReceiver(receiver)
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
        Timber.d("hasAnyLocationPermissions: $hasAnyLocationPermissions")
        if (!hasAnyLocationPermissions) {
            makeLocationPermissionRequest()
        }
        if (ActivityCompat.shouldShowRequestPermissionRationale(this, android.Manifest.permission.ACCESS_FINE_LOCATION)
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

    @SuppressLint("MissingPermission")
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
                     mFusedLocationClient?.requestLocationUpdates()
                }
            }
        }
    }

    override fun onNetworkConnectionChanged(isConnected: Boolean) {
        showNetworkMessage(isConnected)
    }

    private fun showNetworkMessage(isConnected: Boolean) {
        if (!isConnected) {
            makeToast("You are offline ", this)
            onConnectionDialog()
        } else {
            makeToast("You are online ", this)
        }
    }

    private fun onConnectionDialog() {
        supportFragmentManager.let {
            NoConnectionDialog().show(
                it,
                NO_CONNECTION_DIALOG
            )
        }
    }
}
