package com.contrast.Contrast.di.module.app

import android.app.Application
import android.content.Context
import com.contrast.Contrast.extensions.ColorAdapter
import com.contrast.Contrast.extensions.LocalDateTimeAdapter
import com.itechpro.data.config.AppConfig
import com.itechpro.domain.usecase.sell.SellConfigUseCase
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.ConnectionPool
import okhttp3.Dispatcher
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun provideOKHttpClient(
        httpLoggingInterceptor: HttpLoggingInterceptor,
        dynamicBaseUrlInterceptor: DynamicBaseUrlInterceptor,

    ): OkHttpClient {
        return OkHttpClient.Builder()
            .connectTimeout(60, TimeUnit.SECONDS)
            .readTimeout(60, TimeUnit.SECONDS)
            .writeTimeout(60, TimeUnit.SECONDS)

            .addInterceptor(dynamicBaseUrlInterceptor)
            .addInterceptor(httpLoggingInterceptor)
            .eventListenerFactory { MyEventListener() } // ✅ để log call detail
            .connectionPool(ConnectionPool(10, 5, TimeUnit.MINUTES)) // ✅ giữ alive connection
            .dispatcher(
                Dispatcher().apply {
                    maxRequests = 64         // tăng số lượng request tổng
                    maxRequestsPerHost = 10  // tăng số lượng request tới 1 host
                }
            )
            .build()
    }


    @Provides
    @Singleton
    fun provideHttpLoggingInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
    }

    @Provides
    @Singleton
    fun provideMoshi(): Moshi {
        return Moshi.Builder()
            .add(LocalDateTimeAdapter())
            .add(ColorAdapter())

            .addLast(KotlinJsonAdapterFactory())
            .build()
    }

    @Provides
    @Singleton
    fun provideMoshiConverterFactory(moshi: Moshi): MoshiConverterFactory {
        return MoshiConverterFactory.create(moshi)
    }
    @Volatile
    private var currentBaseUrl: String = "https://calista.ezmax.vn"
    @Provides
    @Singleton
    fun provideRetrofitCustomDomain(
        okHttpClient: OkHttpClient,
        moshiConverterFactory: MoshiConverterFactory
    ): Retrofit {
        val start = System.currentTimeMillis()
        val retrofit = Retrofit.Builder()
            .baseUrl(currentBaseUrl) // dummy base URL overridden by interceptor
            .client(OkHttpProvider.getClient())
            .addConverterFactory(moshiConverterFactory)
            .build()


        android.util.Log.d("Timing", "🚀 Retrofit created in ${System.currentTimeMillis() - start}ms")
        return retrofit
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

    @Provides
    fun provideSellConfigUseCase(): SellConfigUseCase {
        return SellConfigUseCase()
    }

}
