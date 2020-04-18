package com.mando.myweather.`interface`

import android.content.Context

interface ForecastUrl {
    fun getForecastUrl(context: Context): String
}