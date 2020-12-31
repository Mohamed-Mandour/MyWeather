package com.mando.weatherforecast.viewModel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.mando.weatherforecast.model.CurrentResponse
import com.mando.weatherforecast.repository.ForecastRepository
import com.mando.weatherforecast.repository.ForecastRepositoryImpl
import timber.log.Timber


class CurrentViewModel(context: Context) : ViewModel() {

    private val repository: ForecastRepository = ForecastRepositoryImpl(context)

    fun getCurrentForecast(): LiveData<CurrentResponse> {
        Timber.d("getSavedCurrentForecast: values ${repository.getSavedCurrentForecast()}")
        return repository.getSavedCurrentForecast()
    }

    fun getTimeZone(): LiveData<String> {
        return repository.getTimeZone()
    }
}