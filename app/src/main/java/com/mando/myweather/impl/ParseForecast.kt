package com.mando.myweather.impl

import com.mando.myweather.model.Current
import com.mando.myweather.model.Forecast
import com.mando.myweather.model.Hourly
import org.json.JSONObject

private const val TIMEZONE = "timezone"
private const val CURRENTLY = "currently"
private const val ICON = "icon"
private const val TIME = "time"
private const val SUMMARY = "summary"
private const val TEMPERATURE = "temperature"
private const val HUMIDITY = "humidity"
private const val PRESSURE = "pressure"
private const val WINDSPEED = "windSpeed"
private const val VISIBILITY = "visibility"
private const val PRECIPPROBABILITY = "precipProbability"
private const val CLOUDCOVER = "cloudCover"
private const val HOURLY = "hourly"
private const val DATA = "data"

class ParseForecast(private val forecastJson: String?) {

    fun parseForecastJson(): Forecast {
        val forecast = Forecast()
        forecast.setCurrent(mapCurrent(forecastJson))
        forecast.setHourly(mapHourly(forecastJson))
        return forecast
    }

    private fun mapCurrent(forecastJson: String?): Current? {
        val forecast = JSONObject(forecastJson)
        val currently: JSONObject = forecast.getJSONObject(CURRENTLY)
        return Current(
            forecast.getString(TIMEZONE),
            currently.getString(ICON),
            currently.getLong(TIME),
            currently.getString(SUMMARY),
            currently.getString(TEMPERATURE),
            currently.getDouble(HUMIDITY),
            currently.getDouble(PRESSURE),
            currently.getDouble(WINDSPEED),
            currently.getDouble(VISIBILITY),
            currently.getDouble(PRECIPPROBABILITY),
            currently.getDouble(CLOUDCOVER)
        )
    }


    private fun mapHourly(forecastJson: String?): Array<Hourly?> {
        val forecast = JSONObject(forecastJson)
        val hourly: JSONObject = forecast.getJSONObject(HOURLY)
        val data = hourly.getJSONArray(DATA)
        val hours: Array<Hourly?> = arrayOfNulls(data.length())

        for (i in 0 until data.length()) {
            val jsonHour = data.getJSONObject(i)
            hours[i] = Hourly(
                jsonHour.getString(ICON),
                jsonHour.getLong(TIME),
                jsonHour.getString(SUMMARY),
                jsonHour.getDouble(TEMPERATURE),
                jsonHour.getDouble(PRECIPPROBABILITY),
                jsonHour.getDouble(HUMIDITY),
                jsonHour.getDouble(WINDSPEED)
            )
        }
        return hours
    }

}
