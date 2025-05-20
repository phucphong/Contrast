package com.contrast.Contrast.di.module.app

import com.google.android.datatransport.runtime.dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Named

@Provides
@Named("FixedRetrofit")
fun provideFixedRetrofit(): Retrofit {
    return Retrofit.Builder()
        .baseUrl("https://itp.ezmax.vn/") // domain cố định
        .client(
            OkHttpClient.Builder()
                .addInterceptor(HttpLoggingInterceptor().apply {
                    level = HttpLoggingInterceptor.Level.BODY
                })
                .build()
        )
        .addConverterFactory(GsonConverterFactory.create())
        .build()
}
