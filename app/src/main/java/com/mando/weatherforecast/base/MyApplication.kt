package com.mando.weatherforecast.base

import android.app.Application
import com.mando.weatherforecast.BuildConfig
import com.mando.weatherforecast.database.ForecastDatabase
import com.mando.weatherforecast.location.FusedLocationDataStore
import timber.log.Timber

lateinit var db: ForecastDatabase


class MyApplication : Application() {

    companion object {
        private lateinit var instance: MyApplication
    }
    override fun onCreate() {
        super.onCreate()

        instance = this
        db = ForecastDatabase.getInstance(this)
        val location = FusedLocationDataStore.getInstance(instance)
        location?.getLastLocation()
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }
}