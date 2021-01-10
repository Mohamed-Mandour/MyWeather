package com.mando.weatherforecast.database

import android.app.Application
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.mando.weatherforecast.model.CurrentResponse

@Database(entities = [CurrentResponse::class], version = 1)
abstract class ForecastDatabase: RoomDatabase() {

    abstract fun forecastDao(): ForecastDao

    companion object {
        private val lock = Any()
        private const val DB_NAME = "ForecastDatabase"
        private var INSTANCE: ForecastDatabase? = null

        fun getInstance(application: Application): ForecastDatabase {
            synchronized(lock) {
                if (INSTANCE == null) {
                    INSTANCE =
                        Room.databaseBuilder(application, ForecastDatabase::class.java, DB_NAME)
                            .build()
                }
            }
            return INSTANCE!!
        }
    }

}