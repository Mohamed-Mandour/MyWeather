package com.mando.myweather.impl

import android.content.Context
import android.util.Log
import okhttp3.*
import java.io.IOException

const val TAG = "OkHttpClientImpl"
class OkHttpClientImpl(private val context: Context, private val forecastUrlImpl: ForecastUrlImpl) {
    private val client = OkHttpClient()

    fun run() {
        Log.d(TAG, "run " + forecastUrlImpl.getForecastUrl(context))
        val request = Request.Builder()
            .url(forecastUrlImpl.getForecastUrl(context))
            .build()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                Log.d(TAG, "onFailure " + call.isExecuted())
                e.printStackTrace()
            }

            override fun onResponse(call: Call, response: Response) {
                response.use {
                    if (!response.isSuccessful) throw IOException("Unexpected code $response")
                    val forecastJson = response.body.toString()
                    Log.d(TAG, "onResponse  forecastJson $forecastJson")
                }
            }
        })
    }
}