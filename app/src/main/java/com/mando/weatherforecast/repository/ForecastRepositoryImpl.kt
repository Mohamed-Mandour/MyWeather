package com.mando.weatherforecast.repository

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.mando.weatherforecast.location.FusedLocationDataStore
import com.mando.weatherforecast.location.LocationDataStore
import com.mando.weatherforecast.model.CurrentResponse
import com.mando.weatherforecast.model.ForecastResponse
import com.mando.weatherforecast.network.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import timber.log.Timber

class ForecastRepositoryImpl(private val context: Context) : ForecastRepository {

    private val retrofitClient = RetrofitClient()
    private val locationDataStore: LocationDataStore?
        get() = FusedLocationDataStore.getInstance(context)

    override fun getSavedCurrentForecast(): LiveData<CurrentResponse> {
        val data = MutableLiveData<CurrentResponse>()

        val forecast = retrofitClient.getForecast(locationDataStore?.location)

        forecast.enqueue(object : Callback<ForecastResponse> {
            override fun onResponse(
                call: Call<ForecastResponse>,
                response: Response<ForecastResponse>
            ) {
                if (response.isSuccessful) {
                    data.value = (response.body()?.currently)
                    Timber.d("response.isSuccessful: $response")

                    Timber.d("response.isSuccessful: ${data.value}")
                }
            }
            override fun onFailure(call: Call<ForecastResponse>, t: Throwable) {
                Timber.d("onFailure: $t")
            }

        })
        Timber.d("data:: ${data.value?.summary}")
        return data
    }

    override fun getTimeZone(): LiveData<String> {
        val timezone = MutableLiveData<String>()
        val forecast = retrofitClient.getForecast(locationDataStore?.location)
        forecast.enqueue(object : Callback<ForecastResponse> {
            override fun onResponse(
                call: Call<ForecastResponse>,
                response: Response<ForecastResponse>
            ) {
                if (response.isSuccessful) {
                    timezone.value = (response.body()?.timezone)
                    Timber.d("response.isSuccessful: $response")
                }
            }
            override fun onFailure(call: Call<ForecastResponse>, t: Throwable) {
                Timber.d("onFailure: $t")
            }

        })
        return timezone
    }
}