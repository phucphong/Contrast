package com.contrast.Contrast.di.module.app

import android.util.Log
import okhttp3.ConnectionPool
import okhttp3.Dispatcher
import okhttp3.Dns
import okhttp3.OkHttpClient
import java.net.InetAddress

import java.util.concurrent.TimeUnit
object OkHttpProvider {
    @Volatile
    private var client: OkHttpClient = createClient()

    fun getClient(): OkHttpClient = client

    fun reset() {
        client = createClient()
        Log.d("OkHttpProvider", "🔁 OkHttpClient reset (new network?)")
    }

    private fun createClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .dns(object : Dns {
                override fun lookup(hostname: String): List<InetAddress> {
                    return InetAddress.getAllByName(hostname).toList()
                }
            }) // 🔥 DNS fresh lookup, no cache
            .connectTimeout(15, TimeUnit.SECONDS)
            .readTimeout(20, TimeUnit.SECONDS)
            .writeTimeout(20, TimeUnit.SECONDS)
            .retryOnConnectionFailure(true)
            .eventListenerFactory { MyEventListener() }
            .connectionPool(ConnectionPool(10, 5, TimeUnit.MINUTES))
            .dispatcher(Dispatcher().apply {
                maxRequests = 64
                maxRequestsPerHost = 10
            })
            .build()
    }
}
