package com.contrast.Contrast.di.module.app

import android.app.Application
import android.content.Context
import com.contrast.Contrast.extensions.ColorAdapter
import com.contrast.Contrast.extensions.LocalDateTimeAdapter
import com.itechpro.data.config.AppConfig


import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit

import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideOKHttpClient(httpLoggingInterceptor: HttpLoggingInterceptor,dynamicBaseUrlInterceptor: DynamicBaseUrlInterceptor): OkHttpClient {
        val builder = OkHttpClient.Builder()
        builder.addInterceptor(dynamicBaseUrlInterceptor)
        builder.interceptors().add(httpLoggingInterceptor)
        return builder.build()
    }

    @Provides
    @Singleton
    fun provideHttpLoggingInterceptor(): HttpLoggingInterceptor {
        val httpLoggingInterceptor = HttpLoggingInterceptor()
        httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        return httpLoggingInterceptor
    }

    @Provides
    @Singleton
    fun provideMoshiConverterFactory(): MoshiConverterFactory {
//        val moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()

        val moshi = Moshi.Builder()
            .add(LocalDateTimeAdapter()) // 👈 Thêm custom adapter
            .add(ColorAdapter()) // 👈 Thêm custom adapter
            .addLast(KotlinJsonAdapterFactory())
            .build()

        return MoshiConverterFactory.create(moshi)
    }

    @Volatile
    private var currentBaseUrl: String = "https://spa.ezmax.vn"

    @Provides
    @Singleton
    fun provideRetrofitCustomDomain(
        okHttpClient: OkHttpClient,
        moshiConverterFactory: MoshiConverterFactory, @ApplicationContext context: Context
    ): Retrofit {
        return Retrofit.Builder().addConverterFactory(moshiConverterFactory)
            .baseUrl(currentBaseUrl)
            .client(okHttpClient)
            .build()
    }
    fun updateBaseUrl(newBaseUrl: String) {
        currentBaseUrl = newBaseUrl
    }

    @Provides
    @Singleton
    fun provideAppConfig(@ApplicationContext context: Context): AppConfig {
        return AppConfig(context)
    }


    @Provides
    fun provideContext(application: Application): Context {
        return application
    }







}