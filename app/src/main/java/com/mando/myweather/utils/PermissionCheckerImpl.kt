package com.mando.myweather.utils

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.util.Log

class PermissionCheckerImpl(private val context: Context?) : PermissionChecker {

    private val TAG = "PermissionCheckerImpl"

    override fun hasAnyLocationPermissions(): Boolean {
        val coarsePermission = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            context?.checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION)
        } else {
            Log.d(TAG, "android Sdk < 23")

        }
        val finePermission = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            context?.checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION)
        } else {
            Log.d(TAG, "android Sdk < 23")
        }
        return coarsePermission == PackageManager.PERMISSION_GRANTED || finePermission == PackageManager.PERMISSION_GRANTED
    }

    override fun hasFineLocationPermission(): Boolean {
        val finePermission = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            context?.checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION)
        } else {
            Log.d(TAG, "android Sdk < 23")
        }
        return finePermission == PackageManager.PERMISSION_GRANTED
    }


}