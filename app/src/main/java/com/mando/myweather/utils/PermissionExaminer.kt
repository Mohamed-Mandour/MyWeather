package com.mando.myweather.utils

interface PermissionExaminer {
    val hasAnyLocationPermissions: Boolean

    val hasFineLocationPermission: Boolean
}