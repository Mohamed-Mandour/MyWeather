package com.mando.myweather.model

import android.os.Parcel
import android.os.Parcelable
import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.roundToInt

class Current() : Parcelable {

    var timezone: String = ""

    var icon: String = ""

    var time: Long = 0L

    var summary: String = ""

    var temperature: String = ""

    var humidity: Double = 0.0

    var pressure: Double = 0.0

    var windSpeed: Double = 0.0

    var visibility: Double = 0.0

    var precipProbability: Double = 0.0

    val getTime: String
        get() {
            val formatter = SimpleDateFormat("h:mm a", Locale.getDefault())
            formatter.timeZone = TimeZone.getTimeZone(timezone)
            val dateTime = Date(time * 1000)
            return formatter.format(dateTime)
        }

    fun getTemperture(): Int {
        return (temperature.toDouble() - 32).roundToInt().toInt() * 5 / 9
    }

    constructor(parcel: Parcel) : this() {
        timezone = parcel.readString().toString()
        icon = parcel.readString().toString()
        time = parcel.readLong()
        summary = parcel.readString().toString()
        temperature = parcel.readString().toString()
        humidity = parcel.readDouble()
        pressure = parcel.readDouble()
        windSpeed = parcel.readDouble()
        visibility = parcel.readDouble()
        precipProbability = parcel.readDouble()
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