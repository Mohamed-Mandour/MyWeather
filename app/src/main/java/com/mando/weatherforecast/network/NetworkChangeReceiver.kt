package com.mando.weatherforecast.network

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.os.Build
import com.mando.weatherforecast.utils.makeToast

class NetworkChangeReceiver: BroadcastReceiver() {
    private var networkStatusChecker: NetworkStatusChecker? = null

    companion object {
        var connectivityReceiverListener: ConnectivityReceiverListener? = null
    }

    override fun onReceive(context: Context?, intent: Intent?) {
        if (connectivityReceiverListener != null) {
            connectivityReceiverListener?.onNetworkConnectionChanged(checkNetworkConnectivity(context))
        }
    }

    private fun checkNetworkConnectivity(context: Context?): Boolean {
        return if (Build.VERSION.SDK_INT >= 23 && context != null) {
            val connectivityManager =
                context.getSystemService<ConnectivityManager>((ConnectivityManager::class.java))
            networkStatusChecker = NetworkStatusChecker(connectivityManager)
            networkStatusChecker != null && networkStatusChecker?.hasInternetConnection() == true
        } else false
    }

    interface ConnectivityReceiverListener {
        fun onNetworkConnectionChanged(isConnected: Boolean)
    }
}