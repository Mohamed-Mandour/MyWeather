package com.mando.weatherforecast.location

interface LocationDataStore {


    val location: DeviceLocation?

    fun requestLocationUpdates()

}