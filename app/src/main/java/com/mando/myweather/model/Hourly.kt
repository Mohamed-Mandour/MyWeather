package com.mando.myweather.model

import android.os.Parcel
import android.os.Parcelable

data class Hourly(
    val icon: String?,
    val time: Long,
    val summary: String?,
    val temperature: Double,
    val precipProbability: Double,
    val humidity: Double,
    val windSpeed: Double
): Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readLong(),
        parcel.readString(),
        parcel.readDouble(),
        parcel.readDouble(),
        parcel.readDouble(),
        parcel.readDouble()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(icon)
        parcel.writeLong(time)
        parcel.writeString(summary)
        parcel.writeDouble(temperature)
        parcel.writeDouble(precipProbability)
        parcel.writeDouble(humidity)
        parcel.writeDouble(windSpeed)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Hourly> {
        override fun createFromParcel(parcel: Parcel): Hourly {
            return Hourly(parcel)
        }

        override fun newArray(size: Int): Array<Hourly?> {
            return arrayOfNulls(size)
        }
    }
}