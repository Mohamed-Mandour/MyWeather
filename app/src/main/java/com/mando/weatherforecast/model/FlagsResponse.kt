package com.mando.weatherforecast.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


@Entity
data class FlagsResponse (
    @SerializedName("id")
    @PrimaryKey
    @Expose
    var id: Long
)