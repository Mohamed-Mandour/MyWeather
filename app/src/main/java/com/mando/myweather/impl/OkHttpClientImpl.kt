package com.mando.myweather.impl

import android.content.Context
import android.util.Log
import com.mando.myweather.model.Forecast
import okhttp3.*
import org.json.JSONException
import java.io.IOException

const val TAG = "OkHttpClientImpl"
class OkHttpClientImpl(private val context: Context, private val forecastUrlImpl: ForecastUrlImpl) {


    fun run() {
        val client = OkHttpClient()
        val forecastUrl = forecastUrlImpl.getForecastUrl(context)
        Log.d(TAG, "forecastUrl: $forecastUrl")

        val request: Request = Request.Builder().url(forecastUrl).build()
        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                Log.d(TAG, "onFailure: ${e.message}")
                e.printStackTrace()
            }

            @Throws(IOException::class)
            override fun onResponse(call: Call, response: Response) {
                Log.d(TAG, "onResponse: ${response.body}")
                val jsonDate = response.body.toString()
                Log.d(TAG, "jsonDate: $jsonDate")
                try {
                    if (response.isSuccessful) {
                        val forecast = Forecast(jsonDate)
                        forecast.setCurrent()
                    }
                } catch (e: IOException) {
                    Log.e(TAG, "Exception caugth: $e")
                } catch (e: JSONException) {
                    Log.e(TAG, "Exception caugth: $e")
                }
            }
        })
    }
}