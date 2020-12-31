package com.mando.weatherforecast.utils

import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.roundToInt

var lastUpdatedFormatter = SimpleDateFormat("hh:mm a", Locale.getDefault())

var currentDateFormatter = SimpleDateFormat("d-MMM-yyyy", Locale.getDefault())

fun getTemperatureValue(temperature: String): Int {
    return ((temperature.toDouble().minus(32)).roundToInt().times(5) ?: 0) / 9
}

fun formatTime(formatter: SimpleDateFormat, timezone: String, time: Long): String {
    formatter.timeZone = TimeZone.getTimeZone(timezone)
    val dateTime = Date(time * 1000)
    return formatter.format(dateTime)
}