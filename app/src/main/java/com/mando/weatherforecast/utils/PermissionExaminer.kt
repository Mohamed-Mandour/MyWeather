package com.mando.weatherforecast.utils

interface PermissionExaminer {

    val hasAnyLocationPermissions: Boolean

    val hasFineLocationPermission: Boolean
}