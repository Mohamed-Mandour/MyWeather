package com.mando.weatherforecast.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

@Entity
data class HourlyResponse (
    @SerializedName("id")
    @PrimaryKey
    @Expose
    var id: Long,
    val icon: String?,
    val time: Long,
    val summary: String?,
    val temperature: Double,
    val precipProbability: Double,
    val humidity: Double,
    val windSpeed: Double
)

