package com.mando.myweather.location

import android.annotation.SuppressLint
import android.content.Context
import android.os.Looper
import android.util.Log
import androidx.annotation.VisibleForTesting
import com.google.android.gms.location.*
import com.mando.myweather.utils.AndroidPermissionChecker
import com.mando.myweather.utils.PermissionExaminer
private const val DEFAULT_INTERVAL = 0L
const val TAG = "FusedLocationDataStore"

object FusedLocationDataStore : LocationDataStore {

    private var mFusedLocationClient: FusedLocationProviderClient? = null
    private var mLocation: DeviceLocation? = null
    private var mPermissionExaminer: PermissionExaminer? = null

    private val locationCallback = object : LocationCallback() {

        override fun onLocationResult(locationResult: LocationResult?) {
            Log.w(TAG, "onLocationResult($locationResult)")

            if (locationResult == null || locationResult.lastLocation == null) {
                Log.w(TAG, "Location is null. Returning")
                return
            }
            mLocation = DeviceLocation(locationResult.lastLocation)
        }
    }

    private fun initialize(context: Context?) {
        Log.d(TAG, "initialize($context)")
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(context!!)
        mPermissionExaminer = AndroidPermissionChecker(context)
        updateLastLocation()
    }

    override val location: DeviceLocation?
        get() {
            updateLastLocation()
            Log.d(TAG, "getLocation() returned: $mLocation")
            return mLocation
        }

    override fun requestNewLocation() {
        if (mPermissionExaminer?.hasAnyLocationPermissions == false) {
            Log.w(TAG, "Cannot request a new location as we don't have permission.")
            return
        }

        val request = createLocationRequest()
        mFusedLocationClient?.requestLocationUpdates(
            request,
            locationCallback,
            Looper.getMainLooper()
        )
    }
    private fun createLocationRequest() = when (mPermissionExaminer?.hasFineLocationPermission) {
        true -> createSingleLocationRequestHighAccuracy()
        else -> createSingleLocationRequestBalancedPower()
    }
    private fun createSingleLocationRequestHighAccuracy() =
        createLocationRequest(LocationRequest.PRIORITY_HIGH_ACCURACY)

    private fun createSingleLocationRequestBalancedPower() =
        createLocationRequest(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY)

    private fun createLocationRequest(requestPriority: Int): LocationRequest {
        val locationUpdateInterval =  DEFAULT_INTERVAL
        val locationUpdateFastestInterval =  DEFAULT_INTERVAL

        return LocationRequest().apply {
            interval = locationUpdateInterval
            fastestInterval = locationUpdateFastestInterval
            priority = requestPriority
        }
    }

    @SuppressLint("MissingPermission")
    @VisibleForTesting
    fun updateLastLocation() {
        Log.d(
            TAG, "updateLastLocation() called" +
                    " From thread: " + Thread.currentThread().id +
                    " isMainThread [" + (Looper.myLooper() == Looper.getMainLooper()) + "]"
        )
        if (mPermissionExaminer?.hasAnyLocationPermissions == false) {
            Log.d(TAG, "No Location permission granted")
            return
        }
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