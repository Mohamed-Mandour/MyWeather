package com.mando.myweather.location

interface LocationDataStoreListener {
    fun onLocationChanged(location: DeviceLocation?)
}