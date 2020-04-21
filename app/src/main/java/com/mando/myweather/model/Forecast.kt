package com.mando.myweather.model

import android.util.Log
import org.json.JSONObject

private const val CURRENTLY  = "currently"
private const val ICON = "icon"
private const val TIMEZONE = "timezone"
private const val SUMMARY = "summary"
private const val TEMPERATURE = "apparentTemperature"

private const val TAG = "Forecast"
class Forecast(private val forecastJson: String) {

    fun setCurrent(): Current? {
        val forecast = JSONObject(forecastJson)
        val timezone: String = forecast.getString("timezone")

        val currently: JSONObject = forecast.getJSONObject("currently")

        return null
    }
}