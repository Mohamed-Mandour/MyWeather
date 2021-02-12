package com.mando.weatherforecast.utils

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.roundToInt

var hourlyDateFormatter = SimpleDateFormat("hh:mm a", Locale.getDefault())

var dailyDateFormatter = SimpleDateFormat("d-MMM-yyyy", Locale.getDefault())

fun getTemperatureValue(temperature: String): Int {
    return ((temperature.toDouble().minus(32)).roundToInt().times(5) ?: 0) / 9
}

fun formatTime(formatter: SimpleDateFormat, timezone: String, time: Long): String {
    formatter.timeZone = TimeZone.getTimeZone(timezone)
    val dateTime = Date(time * 1000)
    return formatter.format(dateTime)
}

fun makeToast(message: CharSequence,context: Context, duration: Int = Toast.LENGTH_LONG) {
    Toast.makeText(context, message, duration).show()
}

fun ViewGroup.inflate(layoutRes: Int): View {
    return LayoutInflater.from(context).inflate(layoutRes, this, false)
}