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
        view.showLocationIcon(getLocationIcon())
        view.showDegreeIcon(getDegreeIcon())
        view.showPrecipitationIcon(getPrecipitationIcon())
        view.showPrecipitationText(getPrecipitationText())
        view.showPrecipitationValue(current?.precipProbability.toString())
        view.showWindIcon(getWindIcon())
        view.showWindText(getWindText())
        view.showWindValue("${current?.windSpeed.toString()} km/h")
        view.showPressureIcon(getPressureIcon())
        view.showPressureText(getPressureText())
        view.showPressureValue(current?.pressure.toString())
        getHorizontalLineColor()
    }

    private fun getPressureText(): String? = context?.resources?.getString(R.string.pressure)

    private fun getPressureIcon(): Drawable? = context?.resources?.getDrawable(R.drawable.ic_pressure)

    private fun getWindText(): String? = context?.resources?.getString(R.string.wind)

    private fun getWindIcon(): Drawable? = context?.resources?.getDrawable(R.drawable.ic_fan)

    private fun getLocationIcon() = context?.resources?.getDrawable(R.drawable.ic_signs)

    private fun getDegreeIcon() = context?.resources?.getDrawable(R.drawable.ic_celsius_degrees_symbol_of_temperature)

    private fun getPrecipitationText() = context?.resources?.getString(R.string.precipitation)

    private fun getPrecipitationIcon(): Drawable?  =  context?.resources?.getDrawable(R.drawable.ic_precipitation)

    private fun getHorizontalLineColor() = context?.resources?.getColor(R.color.main_color)?.let { view.showWhiteLine(it) }

    private fun getWeatherIcon(current: Current?): Drawable? = current?.getIcon?.let { context?.resources?.getDrawable(it) }

}

