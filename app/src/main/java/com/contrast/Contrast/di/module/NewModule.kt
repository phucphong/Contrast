package com.contrast.Contrast.di.module
import com.itechpro.data.api.NewsAPI
import com.itechpro.data.repository.NewsRepositoryImpl
import com.itechpro.domain.repository.NewsRepository

import com.itechpro.domain.usecase.news.NewsUseCase

import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class NewsModule {

    @Binds
    @Singleton
    abstract fun bindNewsRepository(
        impl: NewsRepositoryImpl
    ): NewsRepository
}

@Module
@InstallIn(SingletonComponent::class)
object NewsNetworkModule {

    @Provides
    @Singleton
    fun provideNewsAPI( retrofit: Retrofit): NewsAPI {
        return retrofit.create(NewsAPI::class.java)
    }

    @Provides
    fun provideNewsUseCase(repository: NewsRepository): NewsUseCase {
        return NewsUseCase(repository)
    }





}
