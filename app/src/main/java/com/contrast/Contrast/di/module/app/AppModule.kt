package com.contrast.Contrast.di.module.app

import android.app.Application
import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import com.contrast.Contrast.extensions.ColorAdapter
import com.contrast.Contrast.extensions.LocalDateTimeAdapter
import com.itechpro.data.config.AppConfig
import com.itechpro.data.repository.DownloadImageUseCaseImpl
import com.itechpro.data.repository.MediaRepositoryImpl
import com.itechpro.domain.repository.MediaRepository
import com.itechpro.domain.usecase.dowloadFile.DownloadImageUseCase
import com.itechpro.domain.usecase.product.PromoCountdownUseCase
import com.itechpro.domain.usecase.sell.SellConfigUseCase
import com.itechpro.domain.usecase.share.HandleShareIntentUseCase
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.ConnectionPool
import okhttp3.Dispatcher
import okhttp3.Dns
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.net.InetAddress
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
      appConfig: AppConfig
    ): OkHttpClient {


        return OkHttpClient.Builder()
            .connectTimeout(15, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)

            .dns(object : Dns {
                override fun lookup(hostname: String): List<InetAddress> {
                    return if (hostname == appConfig.getDomain().replace("https://","")) {
                        listOf(InetAddress.getByName("123.30.186.75")) // IP tĩnh backend của bạn
                    } else {
                        Dns.SYSTEM.lookup(hostname)
                    }
                }
            })

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
    private var currentBaseUrl: String = "https://spa.ezmax.vn"
    @Provides
    @Singleton
    fun provideRetrofitCustomDomain(
        okHttpClient: OkHttpClient,
        moshiConverterFactory: MoshiConverterFactory
    ): Retrofit {
        val start = System.currentTimeMillis()
        val retrofit = Retrofit.Builder()
            .baseUrl(currentBaseUrl) // dummy base URL overridden by interceptor
            .client(okHttpClient)
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
    @RequiresApi(Build.VERSION_CODES.O)
    @Provides
    fun providePromoCountdownUseCase(): PromoCountdownUseCase {
        return PromoCountdownUseCase()
    }
    @Provides
    fun provideDownloadImageUseCase(
        context: Context
    ): DownloadImageUseCase {
        return DownloadImageUseCaseImpl(context)
    }
    @Provides
    fun provideHandleShareIntentUseCase(): HandleShareIntentUseCase {
        return HandleShareIntentUseCase()
    }

}
