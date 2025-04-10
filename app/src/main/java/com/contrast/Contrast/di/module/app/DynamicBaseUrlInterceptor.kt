package com.contrast.Contrast.di.module.app


import android.content.Context
import com.itechpro.data.config.AppConfig


import okhttp3.HttpUrl.Companion.toHttpUrlOrNull
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject
import javax.inject.Named
import javax.inject.Singleton
//


@Named("CustomDomain")
@Singleton
class DynamicBaseUrlInterceptor @Inject constructor(
    private val context: Context,
    private val appConfig: AppConfig
) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        var request = chain.request()
        val newUrl = appConfig.getDomain() // ✅ Sửa ở đây


        val newHttpUrl = newUrl.toHttpUrlOrNull()
        newHttpUrl?.let { url ->
            val updatedHttpUrl = request.url.newBuilder()
                .scheme(url.scheme)
                .host(url.host)
                .port(url.port) // Preserve the port
                .build()
            request = request.newBuilder().url(updatedHttpUrl).build()
        }
        return chain.proceed(request)
    }



}