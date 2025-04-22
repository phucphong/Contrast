package com.contrast.Contrast.di.module.app

import android.util.Log
import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException
import javax.inject.Inject

class FallbackInterceptor @Inject constructor() : Interceptor {

    // Danh sách IP fallback
    private val fallbackIps = listOf(
        "123.30.186.75",
        "103.226.249.138"
    )

    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        val originalHost = originalRequest.url.host

        var lastException: Exception? = null

        for (ip in fallbackIps) {
            try {
                val newUrl = originalRequest.url.newBuilder()
                    .host(ip)
                    .build()

                val newRequest = originalRequest.newBuilder()
                    .url(newUrl)
                    .header("Host", originalHost) // giữ nguyên host cho SSL
                    .build()

                Log.d("FallbackInterceptor", "🌐 Trying IP: $ip")

                return chain.proceed(newRequest) // nếu thành công thì return luôn
            } catch (e: Exception) {
                lastException = e
                Log.e("FallbackInterceptor", "❌ Failed with IP $ip: ${e.localizedMessage}")
            }
        }

        // Nếu tất cả IP đều lỗi → throw lỗi cuối cùng
        throw lastException ?: IOException("Unknown fallback error")
    }
}
