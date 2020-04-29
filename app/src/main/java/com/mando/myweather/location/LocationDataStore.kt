package com.mando.myweather.location

interface LocationDataStore {
    val location: DeviceLocation?

    fun requestNewLocation()
}