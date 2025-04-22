package com.contrast.Contrast.presentation.base

import android.net.ConnectivityManager
import android.net.Network
import android.util.Log
import com.contrast.Contrast.di.module.app.OkHttpProvider

class NetworkChangeReceiver : ConnectivityManager.NetworkCallback() {
    override fun onAvailable(network: Network) {
        Log.d("NetworkChangeReceiver", "🌐 Network available → recreate OkHttpClient")
        OkHttpProvider.reset()
    }
}
