package com.contrast.Contrast.di.module.app


import android.util.Log
import okhttp3.Call
import okhttp3.EventListener
import java.net.InetAddress
import java.net.InetSocketAddress
import java.net.Proxy
import java.util.concurrent.TimeUnit

class MyEventListener : EventListener() {
    private var callStartNanos: Long = 0

    override fun callStart(call: Call) {
        callStartNanos = System.nanoTime()
        Log.d("MyEvent", "➡️ Call started: ${call.request().url}")
    }

    override fun dnsStart(call: Call, domainName: String) {
        Log.d("MyEvent", "🔍 DNS lookup started: $domainName")
    }

    override fun connectStart(call: Call, inetSocketAddress: InetSocketAddress, proxy: Proxy) {
        Log.d("MyEvent", "🔌 Connect started: $inetSocketAddress via proxy $proxy")
    }

    override fun secureConnectStart(call: Call) {
        Log.d("MyEvent", "🔐 TLS handshake started")
    }

    override fun callEnd(call: Call) {
        val totalMillis = TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - callStartNanos)
        Log.d("MyEvent", "✅ Call completed in $totalMillis ms")
    }

    override fun callFailed(call: Call, ioe: java.io.IOException) {
        Log.e("MyEvent", "❌ Call failed: ${ioe.message}")
    }
}
