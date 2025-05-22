package com.contrast.Contrast.di.module


import com.itechpro.data.api.ShareProductInComeAPI
import com.itechpro.data.repository.ShareProductInComeRepositoryImpl
import com.itechpro.domain.repository.ShareProductInComeRepository

import com.itechpro.domain.usecase.share_product_income.ShareProductInComeUseCase


import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class ShareProductInComeModule {

    @Binds
    @Singleton
    abstract fun bindShareProductInComeRepository(
        impl: ShareProductInComeRepositoryImpl
    ): ShareProductInComeRepository
}

@Module
@InstallIn(SingletonComponent::class)
object ShareProductInComeNetworkModule {

    @Provides
    @Singleton
    fun provideShareProductInComeAPI( retrofit: Retrofit): ShareProductInComeAPI {
        return retrofit.create(ShareProductInComeAPI::class.java)
    }

    @Provides
    fun provideShareProductInComeUseCase(repository: ShareProductInComeRepository): ShareProductInComeUseCase {
        return ShareProductInComeUseCase(repository)
    }




}
