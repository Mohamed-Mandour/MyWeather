package com.mando.myweather.mvp

import android.graphics.drawable.Drawable
import com.mando.myweather.R
import com.mando.myweather.model.Current

class CurrentScreenPresenter(private val view: CurrentScreenContract.View) :
    CurrentScreenContract.Presenter {

    private val context = view.getContext()

    override fun setCurrentForecast(current: Current?) {
        view.showCurrentDay(current?.getDate)
        view.showLastUpdated("Last updated: ${current?.getLastUpdated}")
        view.showTimezone(current?.timezone)
        view.showSummary(current?.summary)
        view.showWeatherIcon(getWeatherIcon(current))
        view.showLocationIcon(getLocationIcon())
        view.showDegreeIcon(getDegreeIcon())
        view.showPrecipitationIcon(getPrecipitationIcon())
        view.showPrecipitationText(getPrecipitationText())
        view.showPrecipitationValue(current?.precipProbability.toString())
        view.showTemperature(current?.getTemperature().toString())
        view.showWindIcon(getWindIcon())
        view.showWindText(getWindText())
        view.showWindValue("${current?.windSpeed.toString()} km/h")
        view.showPressureIcon(getPressureIcon())
        view.showPressureText(getPressureText())
        view.showPressureValue(current?.pressure.toString())
        view.showVisibilityIcon(getVisibilityIcon())
        view.showVisibilityText(setVisibilityText())
        view.showVisibilityValue(current?.visibility.toString())
        view.showHumidityIcon(getHumidityIcon())
        view.showHumidityText(getHumidityText())
        view.showHumidityValue(current?.humidity.toString())
        view.showCloudCoverIcon(getCloudCoverIcon())
        view.showCloudCoverText(getCloudCoverText())
        view.showCloudCoverValue(current?.cloudCover.toString())
        getHorizontalLineColor()
    }

    private fun getCloudCoverText(): String? = context?.resources?.getString(R.string.cloud_cover)

    private fun getCloudCoverIcon(): Drawable? = context?.resources?.getDrawable(R.drawable.ic_cloud)

    private fun getHumidityText(): String? = context?.resources?.getString(R.string.humidity)

    private fun getHumidityIcon(): Drawable? = context?.resources?.getDrawable(R.drawable.ic_humidity)

    private fun setVisibilityText(): String? = context?.resources?.getString(R.string.visibility)

    private fun getVisibilityIcon(): Drawable? = context?.resources?.getDrawable(R.drawable.ic_eye)

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

