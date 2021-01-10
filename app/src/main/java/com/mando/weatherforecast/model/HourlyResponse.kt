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

    @SerializedName("summary")
    @Expose
    val summary: String?,

    @SerializedName("icon")
    @Expose
    val icon: String?,

    @SerializedName("data")
    @Expose
    val data: MutableList<CurrentResponse>
)

