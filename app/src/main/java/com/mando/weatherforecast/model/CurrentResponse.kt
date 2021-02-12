package com.mando.weatherforecast.model

import androidx.annotation.Nullable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

@Entity
data class CurrentResponse(

    @SerializedName("id")
    @PrimaryKey(autoGenerate = true)
    @Expose
    var id: Long,

    @SerializedName("timezone")
    @Nullable
    @Expose
    var timezone: String?,

    @SerializedName("icon")
    @Expose
    var icon: String,

    @SerializedName("time")
    @Expose
    var time: Long,

    @SerializedName("summary")
    @Expose
    var summary: String?,

    @SerializedName("temperature")
    @Expose
    var temperature: String,

    @SerializedName("humidity")
    @Expose
    var humidity: Double,

    @SerializedName("pressure")
    @Expose
    var pressure: Double,

    @SerializedName("windSpeed")
    @Expose
    var windSpeed: Double,

    @SerializedName("cloudCover")
    @Expose
    var cloudCover: Double,

    @SerializedName("visibility")
    @Expose
    var visibility: Double,

    @SerializedName("precipProbability")
    @Expose
    var precipProbability: Double
)