package com.contrast.Contrast.presentation.base


import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.util.Log
import android.widget.Toast
import com.contrast.Contrast.utils.Util


class NetworkChangeReceiver(private val context: Context) : ConnectivityManager.NetworkCallback() {

    override fun onAvailable(network: Network) {
        if (!hasInternetConnection(context)) {
            Util.showDialog("Mất kết nối Internet", context)

        }
    }

    override fun onLost(network: Network) {

    }

    private fun hasInternetConnection(context: Context): Boolean {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val allNetworks = connectivityManager.allNetworks
        allNetworks.forEach { network ->
            val capabilities = connectivityManager.getNetworkCapabilities(network) ?: return@forEach

            val hasInternet = capabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
            val isWiFi = capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)
            val isMobile = capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)

            if (hasInternet && (isWiFi || isMobile)) {
                return true // Nếu có mạng nào còn Internet thì vẫn tính là còn mạng
            }
        }

        return false // Không mạng nào có Internet cả
    }
}

