package com.mando.weatherforecast.viewModel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.mando.weatherforecast.model.HourlyResponse
import com.mando.weatherforecast.repository.ForecastRepository
import com.mando.weatherforecast.repository.ForecastRepositoryImpl

class HourlyViewModel(context: Context) : ViewModel() {

    private val repository: ForecastRepository = ForecastRepositoryImpl(context)

    fun getHourlyForecast(): LiveData<HourlyResponse> {
        return repository.getHourlyForecast()
    }

}