package com.mando.myweather.utils

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.util.Log

private const val TAG = "PermissionCheckerImpl"

class AndroidPermissionChecker(private val context: Context?) : PermissionExaminer {

    override val hasAnyLocationPermissions: Boolean
        get() {

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

    override val hasFineLocationPermission: Boolean
        get() {
            val finePermission = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                context?.checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION)
            } else {
                Log.d(TAG, "android Sdk < 23")
            }
            return finePermission == PackageManager.PERMISSION_GRANTED
        }
}