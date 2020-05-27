package com.mando.myweather.model

import android.os.Parcel
import android.os.Parcelable
import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.roundToInt

class Current(
    var timezone: String?,
    var icon: String?,
    var time: Long,
    var summary: String?,
    var temperature: String?,
    var humidity: Double,
    var pressure: Double,
    var windSpeed: Double,
    var cloudCover: Double,
    var visibility: Double,
    var precipProbability: Double
) : Parcelable {
    val getLastUpdated: String
        get() {
            val formatter = SimpleDateFormat("hh:mm a", Locale.getDefault())
            return formatTime(formatter)
        }

    val getDate: String
        get() {
            val formatter = SimpleDateFormat("d-MMM-yyyy", Locale.getDefault())
            return formatTime(formatter)
        }

    val getIcon: Int
        get() {
            val forecast = Forecast()
            return forecast.getIconId(icon)
        }

    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readLong(),
        parcel.readString(),
        parcel.readString(),
        parcel.readDouble(),
        parcel.readDouble(),
        parcel.readDouble(),
        parcel.readDouble(),
        parcel.readDouble(),
        parcel.readDouble()
    ) {
    }

    private fun formatTime(formatter: SimpleDateFormat): String {
        formatter.timeZone = TimeZone.getTimeZone(timezone)
        val dateTime = Date(time * 1000)
        return formatter.format(dateTime)
    }

    fun getTemperature(): Int {
        return ((temperature?.toDouble()?.minus(32))?.roundToInt()?.times(5) ?: 0) / 9
    }

    override fun writeToParcel(dest: Parcel?, flags: Int) {
        dest?.writeString(timezone)
        dest?.writeString(icon)
        dest?.writeLong(time)
        dest?.writeString(summary)
        dest?.writeString(temperature)
        dest?.writeDouble(humidity)
        dest?.writeDouble(pressure)
        dest?.writeDouble(windSpeed)
        dest?.writeDouble(cloudCover)
        dest?.writeDouble(visibility)
        dest?.writeDouble(precipProbability)
    }

    override fun describeContents(): Int {
        TODO("Not yet implemented")
    }

    companion object CREATOR : Parcelable.Creator<Current> {
        override fun createFromParcel(parcel: Parcel): Current {
            return Current(parcel)
        }

        override fun newArray(size: Int): Array<Current?> {
            return arrayOfNulls(size)
        }
    }
}