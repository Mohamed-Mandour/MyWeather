package com.mando.myweather.impl

import android.content.Context
import android.net.ConnectivityManager
import com.mando.myweather.`interface`.Network

class NetworkImpl(val context: Context): Network {

    override val isNetworkAvailable: Boolean
        get() {
            val manager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val networkInfo = manager.activeNetworkInfo
            if (networkInfo != null && networkInfo.isConnected) {
                return true
            }
            return false
        }
}