package com.mando.myweather.impl

import com.mando.myweather.`interface`.ForecastUrl
import com.mando.myweather.location.DeviceLocation

class ForecastUrlImpl(private val location: DeviceLocation) : ForecastUrl {

    override val darkSkyForecastUrl: String
        get() {
            val latitude = location.latitude
            val longitude = location.longitude
            val apiValue = "a1fca94d101abf452cf8c428d35f5978"
            return "https://api.darksky.net/forecast/$apiValue/$latitude,$longitude"
        }
}