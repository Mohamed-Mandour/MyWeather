package com.mando.myweather.background

import android.content.Context
import android.os.AsyncTask
import android.util.Log
import com.mando.myweather.impl.ForecastUrlImpl
import com.mando.myweather.location.FusedLocationDataStore
import com.mando.myweather.location.LocationDataStore
import com.mando.myweather.utils.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import org.json.JSONException
import org.json.JSONObject
private const val TAG = "ForecastJsonTask"
class ForecastJsonTask (
    private val context: Context,
    private val onForecastJsonReady: OnForecastJsonReady): AsyncTask<String, String, String>() {

    private  val locationDataStore: LocationDataStore?
        get() = FusedLocationDataStore.getInstance(context)

    override fun doInBackground(vararg params: String?): String? {

        var forecastJson: String? = null
        val location = locationDataStore?.location
            val forecastUrl = ForecastUrlImpl()
            val request: Request = Request.Builder().url(forecastUrl.darkSkyForecastUrl).build()
            Log.d(TAG, "forecastJson $forecastJson")

            try {
                val response: Response = OkHttpClient(context).getHTTPClient()!!.newCall(request).execute()
                forecastJson = JSONObject(response.body?.string()).toString()
                Log.d(TAG, "forecastJson $forecastJson")

            }catch (e: JSONException){
                Log.e(TAG, "JSONException ${e.message}")
            }

        return forecastJson
    }

    override fun onPostExecute(forecastJson: String?) {
        super.onPostExecute(forecastJson)
        onForecastJsonReady.getForecastJson(forecastJson)
    }

    interface OnForecastJsonReady{

        fun getForecastJson(forecastJson: String?)
    }
}