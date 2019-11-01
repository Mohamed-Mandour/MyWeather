package com.mando.myweather.model

data class Hourly(
    val time: Long, val summary: String, val temperature: Double,
    val humidity: Double, val windSpeed: Double
)