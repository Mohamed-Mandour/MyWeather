package com.mando.myweather.mvp

import android.content.Context
import android.graphics.drawable.Drawable
import com.mando.myweather.model.Current


interface CurrentScreenContract {

    interface View {
        fun showWeatherIcon(icon: Drawable?)
        fun showCurrentDay(day: String?)
        fun showSummary(summary: String?)
        fun showTimezone(timezone: String?)
        fun showDegreeIcon(degreeIcon: Drawable?)
        fun showLocationIcon(locationPin: Drawable?)
        fun showLastUpdated(lastUpdated: String?)
        fun showWhiteLine(line: Int)
        fun showTemperature(currentTemperature: String?)
        fun getContext(): Context?
    }

    interface Presenter {
        fun setCurrentForecast(Current: Current?)
    }
}