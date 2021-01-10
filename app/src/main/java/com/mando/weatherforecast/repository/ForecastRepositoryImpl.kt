package com.mando.weatherforecast.repository

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.mando.weatherforecast.location.DeviceLocation
import com.mando.weatherforecast.location.FusedLocationDataStore
import com.mando.weatherforecast.location.LocationDataStore
import com.mando.weatherforecast.model.CurrentResponse
import com.mando.weatherforecast.model.ForecastResponse
import com.mando.weatherforecast.model.HourlyResponse
import com.mando.weatherforecast.network.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import timber.log.Timber

class ForecastRepositoryImpl(private val context: Context) : ForecastRepository {

    private val retrofitClient = RetrofitClient()

    private val deviceLocation = FusedLocationDataStore.getLastLocation()

    override fun getCurrentForecast(): LiveData<CurrentResponse> {
        val data = MutableLiveData<CurrentResponse>()

        val forecast = retrofitClient.getForecast(deviceLocation)

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
        return data
    }

    override fun getTimeZone(): LiveData<String> {
        val timezone = MutableLiveData<String>()
        val forecast = retrofitClient.getForecast(deviceLocation)
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

    override fun getHourlyForecast(): LiveData<HourlyResponse> {
        val data = MutableLiveData<HourlyResponse>()

        val forecast = retrofitClient.getForecast(deviceLocation)

        forecast.enqueue(object : Callback<ForecastResponse> {
            override fun onResponse(
                call: Call<ForecastResponse>,
                response: Response<ForecastResponse>
            ) {
                if (response.isSuccessful) {
                    data.value = (response.body()?.hourly)
                    Timber.d("response.isSuccessful: $response")
                    Timber.d("response.isSuccessful hourly Summary : ${data.value?.summary}")
                }
            }

            override fun onFailure(call: Call<ForecastResponse>, t: Throwable) {
                Timber.d("onFailure HourlyForecast : $t")
            }

        })
        return data
    }
}