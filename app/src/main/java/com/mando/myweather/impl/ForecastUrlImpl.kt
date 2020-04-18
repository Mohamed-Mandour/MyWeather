package com.mando.myweather.impl

import android.content.Context
import com.mando.myweather.`interface`.ForecastUrl
import com.mando.myweather.location.DeviceLocation

class ForecastUrlImpl(private val location: DeviceLocation) : ForecastUrl {

    override fun getForecastUrl(context: Context): String {
        val latitude = location.latitude
        val longitude = location.longitude
        val apiValue = "a1fca94d101abf452cf8c428d35f5978"
        return "https://api.darksky.net/forecast/$apiValue/$latitude,$longitude"
    }


}