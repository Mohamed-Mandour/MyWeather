package com.mando.myweather.`interface`

import android.content.Context

interface Network {

    fun isNetworkAvailable(context: Context): Boolean
}