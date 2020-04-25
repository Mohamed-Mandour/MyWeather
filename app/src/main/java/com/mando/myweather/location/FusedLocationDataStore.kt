package com.mando.myweather.location

import android.annotation.SuppressLint
import android.content.Context
import android.os.Looper
import android.util.Log
import androidx.annotation.VisibleForTesting
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import java.util.concurrent.CopyOnWriteArrayList

const val TAG = "FusedLocationDataStore"

object FusedLocationDataStore : LocationDataStore {

    private var mFusedLocationClient: FusedLocationProviderClient? = null
    private var mLocationCallback: LocationCallback? = null
    private var mLocation: DeviceLocation? = null
    private val mListeners = CopyOnWriteArrayList<LocationDataStoreListener>()


    fun initialize(context: Context?) {
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(context!!)
        initLocationCallback()
        updateLastLocation()
    }

    private fun initLocationCallback() {
        mLocationCallback = object : LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult) {
                Log.i(
                    TAG,
                    "onLocationResult() called with: locationResult = [$locationResult] From thread: " + Thread.currentThread()
                        .id + " isMainThread [" + (Looper.myLooper() == Looper.getMainLooper()) + "]"
                )
                if (locationResult == null) {
                    Log.w(
                        TAG,
                        "onLocationResult() received invalid locationResult: [$locationResult]"
                    )
                    return
                }
                mLocation = DeviceLocation(locationResult.lastLocation)
                Log.i(
                    TAG,
                    "onLocationResult() called with: location = [$mLocation]"
                )
                dispatchLocationChanged()
            }
        }
    }

    override val location: DeviceLocation?
        get() {
            updateLastLocation()
            Log.d(TAG, "getLocation() returned: $mLocation")
            return mLocation
        }

    @SuppressLint("MissingPermission")
    @VisibleForTesting
    fun updateLastLocation() {
        Log.d(
            TAG, "updateLastLocation() called" +
                    " From thread: " + Thread.currentThread().id +
                    " isMainThread [" + (Looper.myLooper() == Looper.getMainLooper()) + "]"
        )
//        if (!permissionChecker.getHasAnyLocationPermissions()) {
//            Log.d(TAG, "No Location permission granted")
//            return
//        }
        val lastLocationTask =
            mFusedLocationClient!!.lastLocation
        lastLocationTask.addOnCompleteListener {
            if (lastLocationTask.isSuccessful) {
                val location = lastLocationTask.result
                if (location != null) {
                    mLocation = DeviceLocation(location)
                }
            } else {
                Log.d(TAG, "Location is null")
            }
        }
    }

    @VisibleForTesting
    fun dispatchLocationChanged() {
        for (listener in mListeners) {
            listener.onLocationChanged(mLocation)
        }
    }

    fun getInstance(context: Context?): FusedLocationDataStore? {
        val fusedLocationDataStore = InstanceHolder.sInstance
        if (fusedLocationDataStore.mFusedLocationClient == null) {
            fusedLocationDataStore.initialize(context)
        }
        return fusedLocationDataStore
    }

    private object InstanceHolder {
        val sInstance: FusedLocationDataStore = FusedLocationDataStore
    }
}