package com.mando.myweather.mvp

import android.graphics.drawable.Drawable
import com.mando.myweather.R
import com.mando.myweather.model.Current

class CurrentScreenPresenter(private val view: CurrentScreenContract.View) :
    CurrentScreenContract.Presenter {

    private val context = view.getContext()

    override fun setCurrentForecast(current: Current?) {
        view.showTemperature(current?.getTemperature().toString())
        view.showCurrentDay(current?.getDate)
        view.showLastUpdated(current?.getLastUpdated)
        view.showTimezone(current?.timezone)
        view.showSummary(current?.summary)
        view.showWeatherIcon(getWeatherIcon(current))
        view.showLocationIcon(context?.resources?.getDrawable(R.drawable.ic_signs))
        view.showDegreeIcon(context?.resources?.getDrawable(R.drawable.degree))
        setHorizontalLine()
    }

    private fun setHorizontalLine() {
        context?.resources?.getColor(R.color.main_color)?.let { view.showWhiteLine(it) }
    }

    private fun getWeatherIcon(current: Current?): Drawable? {
        return current?.getIcon?.let { context?.resources?.getDrawable(it) }
    }
}

