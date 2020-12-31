package com.mando.weatherforecast.repository

import androidx.lifecycle.LiveData
import com.mando.weatherforecast.model.CurrentResponse


interface ForecastRepository {

    fun getSavedCurrentForecast(): LiveData<CurrentResponse>

    fun getTimeZone(): LiveData<String>

}