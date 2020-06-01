package com.mando.myweather.utils

import android.content.Context
import android.util.Log
import com.mando.myweather.fragments.CurrentFragment
import okhttp3.Cache
import okhttp3.OkHttpClient
import timber.log.Timber
import java.io.File
import java.util.concurrent.TimeUnit

private const val TAG = "OkHttpClient"

class OkHttpClient(val context: Context) {

    private var sOkHttpClient: OkHttpClient? = null

    private fun initOkHttpClient() {
        val cacheSize = 10 * 1024 * 1024 // 10 MiB
        Timber.d("initOkHttpClient for OKHTTP")
        sOkHttpClient = OkHttpClient()
        sOkHttpClient = sOkHttpClient?.newBuilder()
            ?.readTimeout(30, TimeUnit.SECONDS)
            ?.connectTimeout(30, TimeUnit.SECONDS)
            ?.cache(
                Cache(
                    File(context.externalCacheDir, "myWeather"),
                    cacheSize.toLong()
                )
            )
            ?.build()
    }

    fun getHTTPClient(): OkHttpClient? {
        if (sOkHttpClient == null) {
            initOkHttpClient()
        }
        return sOkHttpClient
    }
}