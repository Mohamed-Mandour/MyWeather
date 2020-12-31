package com.mando.weatherforecast.network

import com.mando.weatherforecast.location.DeviceLocation
import com.mando.weatherforecast.model.ForecastResponse
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitClient {

    private val forecastApi: ForecastApi

    companion object {
        private const val API_KEY = "a1fca94d101abf452cf8c428d35f5978"
        private const val BASE_URL = "https://api.darksky.net"
    }

    init {
        val builder = OkHttpClient.Builder()
        val okHttpClient = builder.build()
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
        forecastApi = retrofit.create(ForecastApi::class.java)
    }

    fun getForecast(location: DeviceLocation?): Call<ForecastResponse> {
        val latitude = location?.latitude
        val longitude = location?.longitude
        return forecastApi.getCurrentForecast(API_KEY,latitude, longitude)
    }
}