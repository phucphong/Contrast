package com.contrast.Contrast.core

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkRequest
import com.contrast.Contrast.presentation.base.NetworkChangeReceiver
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp// thêm application
class MainApplication: Application() {
    override fun onCreate() {
        super.onCreate()

        val connectivityManager = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val request = NetworkRequest.Builder().build()

        connectivityManager.registerNetworkCallback(request, NetworkChangeReceiver())
    }
}