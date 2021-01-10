package com.mando.weatherforecast.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class ForecastResponse(
    @SerializedName("latitude")
    @Expose
    var latitude: Double? = null,

    @SerializedName("longitude")
    @Expose
    var longitude: Double? = null,

    @SerializedName("timezone")
    @Expose
    var timezone: String,

    @SerializedName("currently")
    @Expose
    var currently: CurrentResponse,

    @SerializedName("hourly")
    @Expose
    var hourly: HourlyResponse,

    @SerializedName("daily")
    @Expose
    var daily: DailyResponse,

    @SerializedName("flags")
    @Expose
    var flags: FlagsResponse,

    @SerializedName("offset")
    @Expose
    var offset: Int? = null
)
