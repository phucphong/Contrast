package com.contrast.Contrast.di.module


import com.itechpro.data.api.SettingAPi
import com.itechpro.data.repository.SettingRepositoryImpl
import com.itechpro.domain.repository.SettingRepository

import com.itechpro.domain.usecase.sell.SellConfigUseCase
import com.itechpro.domain.usecase.setting.SettingUseCase


import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class SettingModule {

    @Binds
    @Singleton
    abstract fun bindSettingRepository(
        impl: SettingRepositoryImpl
    ): SettingRepository
}

@Module
@InstallIn(SingletonComponent::class)
object SettingNetworkModule {

    @Provides
    @Singleton
    fun provideSettingAPI( retrofit: Retrofit): SettingAPi {
        return retrofit.create(SettingAPi::class.java)
    }

    @Provides
    fun provideSettingUseCase(repository: SettingRepository): SettingUseCase {
        return SettingUseCase(repository)
    }






}
