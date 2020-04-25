/*
 * Copyright (c) 2018. OpenSignal.com
 */
package com.mando.myweather.location

interface LocationDataStoreListener {
    fun onLocationChanged(location: DeviceLocation?)
}