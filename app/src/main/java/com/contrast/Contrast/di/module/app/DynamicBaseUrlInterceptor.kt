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

//    override fun intercept(chain: Interceptor.Chain): Response {
//        val originalRequest = chain.request()
//        val newBaseUrl = appConfig.getDomain().toHttpUrlOrNull()
//        Log.d("DynamicBaseUrl", "New domain: ${appConfig.getDomain()}")
//        if (BuildConfig.DEBUG) {
//            Log.d("DynamicBaseUrl", "🔁 Intercepting: ${originalRequest.url}")
//        }
//        val newRequest = newBaseUrl?.let { baseUrl ->
//            val newUrl = originalRequest.url.newBuilder()
//                .scheme(baseUrl.scheme)
//                .host(baseUrl.host)
//                .port(baseUrl.port)
//                .build()
//
//            originalRequest.newBuilder().url(newUrl).build()
//        } ?: originalRequest
//
//        return chain.proceed(newRequest)
//    }


    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        val originalUrl = originalRequest.url

        val fixedDomain = "https://itp.ezmax.vn".toHttpUrlOrNull()
        val dynamicDomain = appConfig.getDomain().toHttpUrlOrNull()

        val newUrl = when {
            // Nếu API là getDomain → dùng domain cố định
            originalUrl.encodedPath.contains("/ex/apinew/getDomain") && fixedDomain != null -> {
                originalUrl.newBuilder()
                    .scheme(fixedDomain.scheme)
                    .host(fixedDomain.host)
                    .port(fixedDomain.port)
                    .build()
            }

            // Các API khác → dùng domain động từ AppConfig
            dynamicDomain != null -> {
                originalUrl.newBuilder()
                    .scheme(dynamicDomain.scheme)
                    .host(dynamicDomain.host)
                    .port(dynamicDomain.port)
                    .build()
            }

            else -> originalUrl // fallback không thay đổi
        }

        val newRequest = originalRequest.newBuilder().url(newUrl).build()
        return chain.proceed(newRequest)
    }

}
