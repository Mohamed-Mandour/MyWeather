package com.mando.myweather.model

import java.text.SimpleDateFormat
import java.util.*

class Current {

    var timezone: String = ""

    var icon: String = ""

    var time: Long = 0L

    var summary: String = ""

    var temperature: String = ""

    var humidity: Double = 0.0

    var pressure: Double = 0.0

    var windSpeed: Double = 0.0

    var visibility: Double = 0.0

    var precipProbability: Double = 0.0

    val getTime: String
        get() {
            val formatter = SimpleDateFormat("h:mm a", Locale.getDefault())
            formatter.timeZone = TimeZone.getTimeZone(timezone)
            val dateTime = Date(time * 1000)
            return formatter.format(dateTime)
        }

}