package com.mando.myweather.background

import android.content.Context
import android.util.Log
import com.mando.myweather.impl.ForecastUrlImpl
import com.mando.myweather.impl.ParseForecast
import com.mando.myweather.location.FusedLocationDataStore
import com.mando.myweather.location.LocationDataStore
import com.mando.myweather.utils.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import org.json.JSONException
import org.json.JSONObject

private const val TAG = "ForecastJsonTask"
class ForecastJson (private val context: Context) {

    private  val locationDataStore: LocationDataStore?
        get() = FusedLocationDataStore.getInstance(context)

     fun getForecastJson(): String? {
        var forecastJson: String? = null
        val location = locationDataStore?.location
            val forecastUrl = ForecastUrlImpl()
            val request: Request = Request.Builder().url(forecastUrl.darkSkyForecastUrl).build()
            try {
                val response: Response = OkHttpClient(context).getHTTPClient()!!.newCall(request).execute()
                forecastJson = JSONObject(response.body?.string()).toString()
                Log.d(TAG, "forecastJson $forecastJson")
            }catch (e: JSONException){
                Log.e(TAG, "JSONException ${e.message}")
            }

        return forecastJson
    }


}