package com.mando.weatherforecast.location

import android.annotation.SuppressLint
import android.content.Context
import android.os.Looper
import androidx.annotation.VisibleForTesting
import com.google.android.gms.location.*
import com.google.android.gms.tasks.Tasks
import com.mando.weatherforecast.utils.AndroidPermissionChecker
import com.mando.weatherforecast.utils.PermissionExaminer
import timber.log.Timber
import java.util.concurrent.TimeUnit

private const val DEFAULT_INTERVAL = 0L
const val TAG = "FusedLocationDataStore"

object FusedLocationDataStore : LocationDataStore {

    private var mFusedLocationClient: FusedLocationProviderClient? = null
    private var mLocation: DeviceLocation? = null
    private var mPermissionExaminer: PermissionExaminer? = null

    private val locationCallback = object : LocationCallback() {

        override fun onLocationResult(locationResult: LocationResult?) {
            Timber.w( "onLocationResult($locationResult)")

            if (locationResult == null || locationResult.lastLocation == null) {
                Timber.w("Location is null. Returning")
                return
            }
            mLocation = DeviceLocation(locationResult.lastLocation.latitude, locationResult.lastLocation.longitude, locationResult.lastLocation.provider)
        }
    }

    private fun initialize(context: Context?) {
        Timber.d("initialize($context)")
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(context!!)
        mPermissionExaminer = AndroidPermissionChecker(context)
        updateLastLocation()
    }

    override val location: DeviceLocation?
        get() {
            updateLastLocation()
            Timber.d( "getLocation() returned: $mLocation")
            return mLocation
        }

    @SuppressLint("MissingPermission")
    override fun requestNewLocation() {
        if (mPermissionExaminer?.hasAnyLocationPermissions == false) {
            Timber.w("Cannot request a new location as we don't have permission.")
            return
        }

        val request = createLocationRequest()
        mFusedLocationClient?.requestLocationUpdates(
            request,
            locationCallback,
            Looper.getMainLooper()
        )
    }

    @SuppressLint("MissingPermission")
    override fun getLastLocation(): DeviceLocation {
        var deviceLocation = DeviceLocation()

        if (mPermissionExaminer?.hasAnyLocationPermissions == false) {
            Timber.d("Cannot get last location as we don't have permission.")
            return deviceLocation
        }

        try {
            val lastLocationTask = mFusedLocationClient?.lastLocation
            lastLocationTask?.let {
                Tasks.await(
                    it,
                    2,
                    TimeUnit.SECONDS
                )
            }
            val lastLocationResult = lastLocationTask?.result

            if (lastLocationResult != null) {
                deviceLocation = DeviceLocation(location?.latitude ?: 0.0, location?.latitude ?: 0.0, location?.provider ?: "")
            }
        } catch (e: Exception) {
            Timber.d(e)
        }

        return deviceLocation
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

    @SuppressLint("MissingPermission", "BinaryOperationInTimber")
    fun updateLastLocation() {
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
            val lastLocationTask =
                mFusedLocationClient?.lastLocation
            lastLocationTask?.addOnCompleteListener {
                if (lastLocationTask.isSuccessful) {
                    val location = lastLocationTask.result
                    if (location != null) {
                        mLocation = DeviceLocation(location.latitude, location.latitude, location.provider)
                    }
                }
                lastLocationTask?.addOnFailureListener {
                    Timber.d("Location can not be obtain")
                }
            }

        } catch (e: Exception) {
            Timber.d(e)
        }
    }

    fun getInstance(context: Context?): FusedLocationDataStore? {
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