package com.contrast.Contrast.di.module


import com.itechpro.data.api.VideoAPI
import com.itechpro.data.repository.VideoRepositoryImpl
import com.itechpro.domain.repository.VideoRepository

import com.itechpro.domain.usecase.video.VideoUseCase

import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class VideoModule {

    @Binds
    @Singleton
    abstract fun bindVideoRepository(
        impl: VideoRepositoryImpl
    ): VideoRepository
}

@Module
@InstallIn(SingletonComponent::class)
object VideoNetworkModule {

    @Provides
    @Singleton
    fun provideVideoAPI( retrofit: Retrofit): VideoAPI {
        return retrofit.create(VideoAPI::class.java)
    }

    @Provides
    fun provideVideoUseCase(repository: VideoRepository): VideoUseCase {
        return VideoUseCase(repository)
    }





}
