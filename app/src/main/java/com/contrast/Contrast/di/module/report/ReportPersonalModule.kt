package com.contrast.Contrast.di.module.report




import com.itechpro.data.api.report.ReportPersonalAPI

import com.itechpro.data.repository.report.ReportPersonalRepositoryImpl

import com.itechpro.domain.repository.report.ReportPersonalRepository
import com.itechpro.domain.usecase.report.ReportPersonalUseCase


import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class ReportPersonalModule {
    @Binds
    @Singleton
    abstract fun bindReportPersonalRepository(
        impl: ReportPersonalRepositoryImpl
    ): ReportPersonalRepository
}

@Module
@InstallIn(SingletonComponent::class)
object ReportNetworkModule {

    @Provides
    @Singleton
    fun provideReportPersonalAPI(retrofit: Retrofit): ReportPersonalAPI {
        return retrofit.create(ReportPersonalAPI::class.java)
    }


    @Provides
    fun provideReportPersonalUseCase(repository: ReportPersonalRepository): ReportPersonalUseCase {
        return ReportPersonalUseCase(repository)
    }


}
