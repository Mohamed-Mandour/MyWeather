package com.mando.myweather.model

data class Current(
    val icon: String, val time: Long, val summary: String, val temperature: Double,
    val humidity: Double, val pressure: Double, val windSpeed: Double,
    val visibility: Double, val preCipIntensity: Double
)