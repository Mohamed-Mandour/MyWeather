package com.mando.myweather.model

import java.text.SimpleDateFormat
import java.util.*

data class Current(
    val timeZone: String, val icon: String, val time: Long, val summary: String, val temperature: String,
    val humidity: Double, val pressure: Double, val windSpeed: Double,
    val visibility: Double, val precipProbability: Double
){
    val getTime: String
        get() {
            val formatter = SimpleDateFormat("h:mm a", Locale.getDefault())
            formatter.timeZone = TimeZone.getTimeZone(timeZone)
            val dateTime = Date(time * 1000)
            return formatter.format(dateTime)
        }

}