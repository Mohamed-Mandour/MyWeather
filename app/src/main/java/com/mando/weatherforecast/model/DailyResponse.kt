package com.mando.weatherforecast.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

@Entity
data class DailyResponse(
    @SerializedName("id")
    @PrimaryKey
    @Expose
    var id: Long,
    @SerializedName("icon")
    @Expose
    val icon: String,

    @SerializedName("time")
    @Expose
    val time: Long,

    @SerializedName("summary")
    @Expose
    val summary: String,

    @SerializedName("temperatureMin")
    @Expose
    val temperatureMin: Double,

    @SerializedName("temperatureMax")
    @Expose
    val temperatureMax: Double,

    @SerializedName("apparentTemperatureMinTime")
    @Expose
    val apparentTemperatureMinTime: Long,

    @SerializedName("apparentTemperatureMaxTime")
    @Expose
    val apparentTemperatureMaxTime: Long,

    @SerializedName("humidity")
    @Expose
    val humidity: Double,

    @SerializedName("pressure")
    @Expose
    val pressure: Double,

    @SerializedName("windSpeed")
    @Expose
    val windSpeed: Double,

    @SerializedName("visibility")
    @Expose
    val visibility: Double,

    @SerializedName("preCipIntensity")
    @Expose
    val preCipIntensity: Double,

    @SerializedName("precipType")
    @Expose
    val precipType: String
)
