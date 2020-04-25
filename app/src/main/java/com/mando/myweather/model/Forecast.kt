package com.mando.myweather.model

import com.mando.myweather.R


private const val TAG = "Forecast"

class Forecast() {
    private var current: Current? = null

    fun getCurrent(): Current? {
        return current
    }

    fun setCurrent(mapCurrent: Current?) {
        current = mapCurrent
    }

    fun getIconId(iconString: String): Int {
        var iconId: Int = R.drawable.clear_day
        when (iconString) {
            "clear-day" -> {
                iconId = R.drawable.clear_day
            }
            "clear-night" -> {
                iconId = R.drawable.clear_night
            }
            "rain" -> {
                iconId = R.drawable.rain
            }
            "snow" -> {
                iconId = R.drawable.snow
            }
            "sleet" -> {
                iconId = R.drawable.sleet
            }
            "wind" -> {
                iconId = R.drawable.wind
            }
            "fog" -> {
                iconId = R.drawable.fog
            }
            "cloudy" -> {
                iconId = R.drawable.cloudy
            }
            "partly-cloudy-day" -> {
                iconId = R.drawable.partly_cloudy
            }
            "partly-cloudy-night" -> {
                iconId = R.drawable.cloudy_night
            }
        }
        return iconId
    }
}