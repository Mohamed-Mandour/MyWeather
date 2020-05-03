package com.mando.myweather.impl

import com.mando.myweather.model.*
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

class ParseForecast(private val forecastJson: String?) {

    fun parseForecastJson(): Forecast{
        val forecast = Forecast()
        forecast.setCurrent(mapCurrent(forecastJson))
        return forecast
    }



    private fun mapCurrent(forecastJson: String?): Current? {
        val current = Current()

        val forecast = JSONObject(forecastJson)
        val timezone: String = forecast.getString(TIMEZONE)
        val currently: JSONObject = forecast.getJSONObject(CURRENTLY)
        current.timezone = timezone
        current.icon  = currently.getString(ICON)
        current.time = currently.getLong(TIME)
        current.summary = currently.getString(SUMMARY)
        current.temperature = currently.getString(TEMPERATURE)
        current.humidity = currently.getDouble(HUMIDITY)
        current.pressure = currently.getDouble(PRESSURE)
        current.windSpeed = currently.getDouble(WINDSPEED)
        current.visibility = currently.getDouble(VISIBILITY)
        current.precipProbability = currently.getDouble(PRECIPPROBABILITY)
        return current
    }

}
