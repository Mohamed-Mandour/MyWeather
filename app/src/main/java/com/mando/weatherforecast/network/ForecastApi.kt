package com.mando.weatherforecast.network

import com.mando.weatherforecast.model.ForecastResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface ForecastApi {

    @GET("/forecast/{api_key}/{latitude},{longitude}")
    fun getCurrentForecast(
        @Path("api_key") api_key: String,
        @Path("latitude")latitude: Double?,
        @Path("longitude")longitude: Double?
    ): Call<ForecastResponse>

}