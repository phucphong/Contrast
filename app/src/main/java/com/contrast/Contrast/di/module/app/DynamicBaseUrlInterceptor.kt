package com.contrast.Contrast.di.module.app


import android.content.Context
import android.util.Log
import com.android.volley.BuildConfig
import com.itechpro.data.config.AppConfig


import okhttp3.HttpUrl.Companion.toHttpUrlOrNull
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject
import javax.inject.Named
import javax.inject.Singleton
//
@Singleton
class DynamicBaseUrlInterceptor @Inject constructor(
    private val appConfig: AppConfig
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        val newBaseUrl = appConfig.getDomain().toHttpUrlOrNull()
        Log.d("DynamicBaseUrl", "New domain: ${appConfig.getDomain()}")
        if (BuildConfig.DEBUG) {
            Log.d("DynamicBaseUrl", "🔁 Intercepting: ${originalRequest.url}")
        }
        val newRequest = newBaseUrl?.let { baseUrl ->
            val newUrl = originalRequest.url.newBuilder()
                .scheme(baseUrl.scheme)
                .host(baseUrl.host)
                .port(baseUrl.port)
                .build()

            originalRequest.newBuilder().url(newUrl).build()
        } ?: originalRequest

        return chain.proceed(newRequest)
    }
}
