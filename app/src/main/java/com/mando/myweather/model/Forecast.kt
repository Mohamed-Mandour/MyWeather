package com.mando.myweather.model

import org.json.JSONObject

private const val TIMEZONE = "timezone"
private const val CURRENTLY = "currently"
private const val ICON = "icon"
private const val TIME = "time"
private const val SUMMARY = "summary"
private const val TEMPERATURE = "apparentTemperature"
private const val HUMIDITY = "humidity"
private const val PRESSURE = "pressure"
private const val WINDSPEED = "windSpeed"
private const val VISIBILITY = "visibility"
private const val PRECIPPROBABILITY = "precipProbability"

private const val TAG = "Forecast"

class Forecast(private val forecastJson: String?) {

    fun setCurrent(): Current? {
        val forecast = JSONObject(forecastJson)
        val timezone: String = forecast.getString(TIMEZONE)
        val currently: JSONObject = forecast.getJSONObject(CURRENTLY)
        val icon = currently.getString(ICON)
        val time = currently.getLong(TIME)
        val summary = currently.getString(SUMMARY)
        val temperature = currently.getString(TEMPERATURE)
        val humidity = currently.getDouble(HUMIDITY)
        val pressure = currently.getDouble(PRESSURE)
        val windSpeed = currently.getDouble(WINDSPEED)
        val visibility = currently.getDouble(VISIBILITY)
        val precipProbability = currently.getDouble(PRECIPPROBABILITY)
        return Current(
            timezone,
            icon,
            time,
            summary,
            temperature,
            humidity,
            pressure,
            windSpeed,
            visibility,
            precipProbability
        )
    }
}