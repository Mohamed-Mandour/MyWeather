package com.mando.myweather.impl

import com.mando.myweather.`interface`.ForecastUrl

class ForecastUrlImpl() : ForecastUrl {

    override val darkSkyForecastUrl: String
        get() {
            val latitude = 51.5074
            val longitude = 0.1278
            val apiValue = "a1fca94d101abf452cf8c428d35f5978"
            return "https://api.darksky.net/forecast/$apiValue/$latitude,$longitude"
        }
}