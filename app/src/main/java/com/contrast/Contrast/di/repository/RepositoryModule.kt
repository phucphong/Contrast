package com.contrast.Contrast.di.repository

import com.itechpro.data.api.AgencyAPI
import com.itechpro.data.repository.AgencyRepositoryImpl
import com.itechpro.domain.repository.AgencyRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

// app/di/RepositoryModule.kt
@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    fun provideAgencyRepository(api: AgencyAPI): AgencyRepository {
        return AgencyRepositoryImpl(api)
    }
}
