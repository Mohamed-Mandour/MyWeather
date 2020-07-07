package com.mando.myweather.mvp

import com.mando.myweather.model.Hourly
import timber.log.Timber

class HourlyScreenPresenter(private val view: HourlyScreenContract.View): HourlyScreenContract.Presenter {

    override fun setHourlyForecast(hourly: Array<Hourly?>?) {
        Timber.d("setHourlyForecast called() with ${hourly?.get(1)?.summary}")
        view.setHourlyAdapter(hourly)
    }
}