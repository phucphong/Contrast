package com.contrast.Contrast.di.module

import com.itechpro.domain.usecase.notification.NotificationUseCase

import com.itechpro.data.api.NotificationAPI
import com.itechpro.data.repository.NotificationRepositoryImpl
import com.itechpro.domain.repository.NotificationRepository


import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class NotificationModule {

    @Binds
    @Singleton
    abstract fun bindNotificationRepository(
        impl: NotificationRepositoryImpl
    ): NotificationRepository
}

@Module
@InstallIn(SingletonComponent::class)
object NotificationNetworkModule {

    @Provides
    @Singleton
    fun provideNotificationAPI( retrofit: Retrofit): NotificationAPI {
        return retrofit.create(NotificationAPI::class.java)
    }

    @Provides
    fun provideNotificationUseCase(repository: NotificationRepository): NotificationUseCase {
        return NotificationUseCase(repository)
    }





}
