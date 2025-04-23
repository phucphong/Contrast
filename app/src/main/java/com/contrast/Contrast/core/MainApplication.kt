package com.contrast.Contrast.core

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkRequest
import com.contrast.Contrast.presentation.base.NetworkChangeReceiver
import com.contrast.Contrast.utils.NetworkMonitor
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp// thêm application
class MainApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        NetworkMonitor.register(this)
    }
}