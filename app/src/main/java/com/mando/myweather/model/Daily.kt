package com.mando.myweather.model

data class Daily(
    val icon: String, val time: Long, val summary: String, val temperatureMin: Double,
    val temperatureMax: Double, val apparentTemperatureMinTime: Long,
    val apparentTemperatureMaxTime: Long, val humidity: Double, val pressure: Double,
    val windSpeed: Double, val visibility: Double, val preCipIntensity: Double,
    val precipType: String
)