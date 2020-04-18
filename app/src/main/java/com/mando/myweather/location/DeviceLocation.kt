/*
 * Copyright (c) 2018.  OpenSignal.com
 *
 */
package com.mando.myweather.location

import android.location.Location

class DeviceLocation(location: Location) {
    var latitude = 0.0
    var longitude = 0.0

    init {
        latitude = location.latitude
        longitude = location.longitude
    }
}