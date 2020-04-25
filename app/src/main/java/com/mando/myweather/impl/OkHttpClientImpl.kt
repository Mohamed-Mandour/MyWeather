package com.mando.myweather.impl

import android.content.Context
import android.util.Log
import android.widget.Toast
import android.widget.Toast.LENGTH_LONG
import com.mando.myweather.model.Forecast
import okhttp3.*
import org.json.JSONException
import java.io.IOException

const val TAG = "OkHttpClientImpl"
class OkHttpClientImpl(
    private val context: Context,
    private val forecastUrlImpl: ForecastUrlImpl,
    private val isNetworkAvailable: Boolean
) {

    fun shouldRequest(){
        if (isNetworkAvailable) {
            Log.d(TAG, "isNetworkAvailable: $isNetworkAvailable")
            run()
        }else{
            Toast.makeText(context, "There is internet service please check later", LENGTH_LONG).show()
        }
    }

    private fun run() {
        val client = OkHttpClient()
        val request: Request = Request.Builder().url(forecastUrlImpl.getForecastUrl(context)).build()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                e.printStackTrace()
            }

            @Throws(IOException::class)
            override fun onResponse(call: Call, response: Response) {
                Log.d(TAG, "response: ${response.body}")
                try {
                    val forecastJson = response.body?.string()
                    val forecast = Forecast(forecastJson)
                    val current = forecast.setCurrent()
                    Log.d(TAG, "current: ${current.toString()}")
                    if (response.isSuccessful) {
                    } else {
                    }
                } catch (e: IOException) {
                    Log.e(TAG, "Exception caugth: $e")
                }
            }
        })
    }
}