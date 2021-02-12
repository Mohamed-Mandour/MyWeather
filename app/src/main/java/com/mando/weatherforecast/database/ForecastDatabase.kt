package com.mando.weatherforecast.database

import android.app.Application
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.mando.weatherforecast.model.CurrentResponse

@Database(entities = [CurrentResponse::class], version = 2)
abstract class ForecastDatabase: RoomDatabase() {

    abstract fun forecastDao(): ForecastDao

    companion object {
        private val lock = Any()
        private const val DB_NAME = "ForecastDatabase"
        private var INSTANCE: ForecastDatabase? = null
        val MIGRATION_V1_TO_V2 = getMigrationFromV1ToV2()

        private fun getMigrationFromV1ToV2(): Migration {
            return object : Migration(1, 2) {
                override fun migrate(database: SupportSQLiteDatabase) {
                }
            }
        }

        fun getInstance(application: Application): ForecastDatabase {
            synchronized(lock) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(application, ForecastDatabase::class.java, DB_NAME)
                        .fallbackToDestructiveMigration()
                            .build()
                }
            }
            return INSTANCE!!
        }
    }
}