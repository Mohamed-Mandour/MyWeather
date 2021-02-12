package com.mando.weatherforecast.repository

import androidx.lifecycle.LiveData
import com.mando.weatherforecast.model.CurrentResponse
import com.mando.weatherforecast.model.HourlyResponse
import java.util.*


interface ForecastRepository {

    fun getCurrentForecast(): LiveData<CurrentResponse>

    fun getTimeZone(): LiveData<String>
    
    fun getHourlyForecast(): LiveData<HourlyResponse>

    fun saveCurrentForecast(currentResponse: CurrentResponse)

    fun getLatestCurrentForecast(): LiveData<CurrentResponse>
}