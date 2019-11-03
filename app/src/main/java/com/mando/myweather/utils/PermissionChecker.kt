package com.mando.myweather.utils

interface PermissionChecker {

    fun hasAnyLocationPermissions(): Boolean
    fun hasFineLocationPermission(): Boolean
}