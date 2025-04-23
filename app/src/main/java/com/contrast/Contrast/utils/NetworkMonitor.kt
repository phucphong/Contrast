package com.contrast.Contrast.utils



import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

object NetworkMonitor {

    private val _isOnline = MutableStateFlow(true)
    val isOnline: StateFlow<Boolean> get() = _isOnline

    fun register(context: Context) {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        val networkRequest = NetworkRequest.Builder()
            .addCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
            .build()

        connectivityManager.registerNetworkCallback(networkRequest, object : ConnectivityManager.NetworkCallback() {

            override fun onAvailable(network: Network) {
                _isOnline.value = true
            }

            override fun onLost(network: Network) {
                _isOnline.value = hasInternetConnection(context)
            }
        })

        // Kiểm tra lần đầu khi khởi động
        _isOnline.value = hasInternetConnection(context)
    }

    private fun hasInternetConnection(context: Context): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        val allNetworks = connectivityManager.allNetworks
        allNetworks.forEach { network ->
            val capabilities = connectivityManager.getNetworkCapabilities(network) ?: return@forEach
            if (
                capabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET) &&
                (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) ||
                        capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR))
            ) {
                return true
            }
        }
        return false
    }
}
