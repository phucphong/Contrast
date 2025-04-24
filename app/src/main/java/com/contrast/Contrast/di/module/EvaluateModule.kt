package com.contrast.Contrast.di.module


import com.itechpro.data.api.EvaluateAPI
import com.itechpro.data.repository.EvaluateRepositoryImpl
import com.itechpro.domain.repository.EvaluateRepository
import com.itechpro.domain.usecase.evaluate.EvaluateUseCase
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class EvaluateModule {

    @Binds
    @Singleton
    abstract fun bindEvaluateRepository(
        impl: EvaluateRepositoryImpl
    ): EvaluateRepository
}

@Module
@InstallIn(SingletonComponent::class)
object EvaluateNetworkModule {

    @Provides
    @Singleton
    fun provideEvaluateAPI( retrofit: Retrofit): EvaluateAPI {
        return retrofit.create(EvaluateAPI::class.java)
    }

    @Provides
    fun provideEvaluateUseCase(repository: EvaluateRepository): EvaluateUseCase {
        return EvaluateUseCase(repository)
    }




}
