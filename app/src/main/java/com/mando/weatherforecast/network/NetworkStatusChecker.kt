package com.mando.weatherforecast.network

import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build

class NetworkStatusChecker(private val connectivityManager: ConnectivityManager?) {

    inline fun checkConnectionToInternet(action: () -> Unit) {
        if (hasInternetConnection()) {
            action()
        }
    }

    fun hasInternetConnection(): Boolean {
        if (Build.VERSION.SDK_INT >= 23) {
            val activeNetwork = connectivityManager?.activeNetwork ?: return false
            val networkCapabilities =
                connectivityManager.getNetworkCapabilities(activeNetwork) ?: return false
            return networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)
                    || networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)
                    || networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_VPN)

        } else {
            return false
        }
    }
}