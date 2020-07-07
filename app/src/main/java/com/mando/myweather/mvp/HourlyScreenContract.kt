package com.mando.myweather.mvp

import com.mando.adpater.HourlyRecyclerViewAdapter
import com.mando.myweather.model.Hourly

interface HourlyScreenContract {

    interface View {
       fun setHourlyAdapter(hourly: Array<Hourly?>?): HourlyRecyclerViewAdapter
    }

    interface Presenter {
        fun setHourlyForecast(hourly: Array<Hourly?>?)
    }
}