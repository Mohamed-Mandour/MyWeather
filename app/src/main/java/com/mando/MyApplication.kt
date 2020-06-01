package com.mando

import android.app.Application
import com.mando.myweather.BuildConfig
import timber.log.Timber

class MyApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }
}