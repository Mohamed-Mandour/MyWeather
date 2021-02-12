package com.mando.weatherforecast.location

import android.annotation.SuppressLint
import android.content.Context
import android.os.Looper
import com.google.android.gms.location.*
import com.mando.weatherforecast.utils.AndroidPermissionChecker
import com.mando.weatherforecast.utils.PermissionExaminer
import timber.log.Timber

object FusedLocationDataStore : LocationDataStore {

    private var mFusedLocationClient: FusedLocationProviderClient? = null
    private var mLocationRequest: LocationRequest? = null
    private var mLocation: DeviceLocation? = null
    private var mPermissionExaminer: PermissionExaminer? = null

    private val locationCallback = object : LocationCallback() {

        override fun onLocationResult(locationResult: LocationResult?) {
            Timber.w("onLocationResult($locationResult)")

            if (locationResult == null || locationResult.lastLocation == null) {
                Timber.w("Location is null. Returning")
                return
            }
            mLocation = DeviceLocation(
                locationResult.lastLocation.latitude,
                locationResult.lastLocation.longitude,
                locationResult.lastLocation.provider
            )
        }
    }
    private fun initialize(context: Context) {
        Timber.d("initialize($context)")
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(context)
        mPermissionExaminer = AndroidPermissionChecker(context)
        createLocationRequest()
        getLastLocation()

    }

    override val location: DeviceLocation? = null
        get() {
            if (field != null) {
                requestLocationUpdates()
            }else {
                getLastLocation()
            }

            Timber.d("getLocation() returned: $mLocation")
            return mLocation
        }

    @SuppressLint("MissingPermission")
    override fun requestLocationUpdates() {
        if (mPermissionExaminer?.hasAnyLocationPermissions == false) {
            Timber.w("Cannot request a new location as we don't have permission.")
            return
        }

        mFusedLocationClient?.requestLocationUpdates(
            mLocationRequest,
            locationCallback,
            Looper.getMainLooper()
        )
    }
    private fun createLocationRequest()  {
        mLocationRequest = LocationRequest.create()
        mLocationRequest?.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        mLocationRequest?.interval = 10 * 100000
        mLocationRequest?.fastestInterval = 5 * 1000

    }

    @SuppressLint("MissingPermission", "BinaryOperationInTimber")
    fun getLastLocation() {
        Timber.d(
            "updateLastLocation() called" +
                    " From thread: " + Thread.currentThread().id +
                    " isMainThread [" + (Looper.myLooper() == Looper.getMainLooper()) + "]"
        )
        if (mPermissionExaminer?.hasAnyLocationPermissions == false) {
            Timber.d("No Location permission granted")
            return
        }
        try {
            val lastLocationTask = mFusedLocationClient?.lastLocation
            lastLocationTask?.addOnCompleteListener {
                if (lastLocationTask.isComplete && lastLocationTask.isSuccessful) {
                    val location = lastLocationTask.result

                    Timber.d("lastLocationTask.result ${location?.latitude}")
                    mLocation = DeviceLocation(
                        location?.latitude!!,
                        location.longitude,
                        location.provider
                    )
                }
            }

        } catch (e: Exception) {
            Timber.d(e)
        }
    }

    fun getInstance(context: Context): FusedLocationDataStore {
        val fusedLocationDataStore = InstanceHolder.sInstance
        if (mFusedLocationClient == null) {
            initialize(context)
        }
        return fusedLocationDataStore
    }

    private object InstanceHolder {
        val sInstance: FusedLocationDataStore = FusedLocationDataStore
    }
}