package com.contrast.Contrast.di.module


import com.itechpro.data.api.ReviewAPI
import com.itechpro.data.repository.ReviewRepositoryImpl
import com.itechpro.domain.repository.ReviewRepository
import com.itechpro.domain.usecase.review.ReviewUseCase
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class ReviewModule {

    @Binds
    @Singleton
    abstract fun bindReviewRepository(
        impl: ReviewRepositoryImpl
    ): ReviewRepository
}

@Module
@InstallIn(SingletonComponent::class)
object ReviewNetworkModule {

    @Provides
    @Singleton
    fun provideReviewAPI( retrofit: Retrofit): ReviewAPI {
        return retrofit.create(ReviewAPI::class.java)
    }

    @Provides
    fun provideReviewUseCase(repository: ReviewRepository): ReviewUseCase {
        return ReviewUseCase(repository)
    }




}
