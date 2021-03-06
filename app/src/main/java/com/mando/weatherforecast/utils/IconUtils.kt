package com.mando.weatherforecast.utils

import com.mando.weatherforecast.R

private const val CLEAR_DAY = "clear-day"
private const val CLEAR_Night = "clear-night"
private const val RAIN = "rain"
private const val SNOW = "snow"
private const val SLEET = "sleet"
private const val WIND = "wind"
private const val FOG = "fog"
private const val CLOUDY = "cloudy"
private const val PARTLY_CLOUD_DAY = "partly-cloudy-day"
private const val PARTLY_CLOUD_NIGHT = "partly-cloudy-night"

class IconUtils {

    fun getIconId(iconString: String?): Int {
        when (iconString) {
            RAIN -> {
                return R.drawable.ic_rain
            }
            SNOW -> {
                return R.drawable.ic_snow
            }
            SLEET -> {
                return R.drawable.ic_sleet
            }
            WIND -> {
                return R.drawable.ic_wind
            }
            FOG -> {
                return R.drawable.ic_fog
            }
            CLOUDY -> {
                return R.drawable.ic_cloud
            }
            CLEAR_DAY -> {
                return R.drawable.ic_clear_day
            }
            CLEAR_Night -> {
                return R.drawable.ic_clear
            }
            PARTLY_CLOUD_DAY -> {
                return R.drawable.ic_cloudy
            }
            PARTLY_CLOUD_NIGHT -> {
                return R.drawable.ic_cloud_night
            }
        }
        return R.drawable.ic_clear_day
    }

    fun getHourlyIconId(iconString: String?): Int {
        when (iconString) {
            RAIN -> {
                return R.drawable.hourly_ic_rain
            }
            SNOW -> {
                return R.drawable.hourly_ic_snow
            }
            SLEET -> {
                return R.drawable.hourly_ic_sleet
            }
            WIND -> {
                return R.drawable.hourly_ic_wind
            }
            FOG -> {
                return R.drawable.hourly_ic_fog
            }
            CLOUDY -> {
                return R.drawable.hourly_ic_cloud
            }
            CLEAR_DAY -> {
                return R.drawable.hourly_ic_clear_day
            }
            CLEAR_Night -> {
                return R.drawable.hourly_ic_clear_night
            }
            PARTLY_CLOUD_DAY -> {
                return R.drawable.hourly_ic_cloud
            }
            PARTLY_CLOUD_NIGHT -> {
                return R.drawable.hourly_ic_cloudy_night
            }
        }
        return R.drawable.hourly_ic_clear_day
    }
}