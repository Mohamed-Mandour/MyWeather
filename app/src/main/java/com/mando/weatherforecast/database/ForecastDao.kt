package com.mando.weatherforecast.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.mando.weatherforecast.model.CurrentResponse

@Dao
interface ForecastDao{

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertCurrentForecast(currentResponse: CurrentResponse)

    @Query("select * from currentResponse")
    fun getCurrentForecast(): LiveData<CurrentResponse>
}