package com.mando.weatherforecast.location

internal const val DEFAULT_LATITUDE = 0.0
internal const val DEFAULT_LONGITUDE = 0.0
internal const val DEFAULT_PROVIDER = "saved"

data class DeviceLocation(
    val latitude: Double = DEFAULT_LATITUDE,
    val longitude: Double = DEFAULT_LONGITUDE,
    val provider: String = DEFAULT_PROVIDER
)