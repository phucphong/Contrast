package com.contrast.Contrast.di.module


import android.content.Context
import com.itechpro.data.config.AppConfig
import com.itechpro.data.repository.PreferencesManagerImpl
import com.itechpro.data.repository.UserRepositoryImpl
import com.itechpro.domain.preferences.PreferencesManager
import com.itechpro.domain.repository.UserRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object PreferencesModule {



    @Provides
    @Singleton
    fun providePreferencesManager(
        impl: PreferencesManagerImpl
    ): PreferencesManager = impl


    @Provides
    @Singleton
    fun provideUserRepository(
        impl: UserRepositoryImpl
    ): UserRepository = impl

}
