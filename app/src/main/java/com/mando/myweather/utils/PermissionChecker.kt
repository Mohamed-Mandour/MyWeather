package com.mando.myweather.utils

interface PermissionChecker {
    val hasAnyLocationPermissions: Boolean

    val hasFineLocationPermission: Boolean
}