package com.contrast.Contrast.di.module




import com.itechpro.data.api.HomeAffiliateAPI
import com.itechpro.data.repository.HomeAffiliateRepositoryImpl
import com.itechpro.domain.repository.HomeAffiliateRepository
import com.itechpro.domain.usecase.home.HomeAffiliateUseCase
import com.itechpro.domain.usecase.sell.SellConfigUseCase


import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class HomeAffiliateModule {

    @Binds
    @Singleton
    abstract fun bindHomeAffiliateRepository(
        impl: HomeAffiliateRepositoryImpl
    ): HomeAffiliateRepository
}

@Module
@InstallIn(SingletonComponent::class)
object HomeAffiliateNetworkModule {

    @Provides
    @Singleton
    fun provideHomeAffiliateAPI( retrofit: Retrofit): HomeAffiliateAPI {
        return retrofit.create(HomeAffiliateAPI::class.java)
    }

    @Provides
    fun provideHomeAffiliateUseCase(repository: HomeAffiliateRepository): HomeAffiliateUseCase {
        return HomeAffiliateUseCase(repository)
    }

    @Provides
    fun provideSellConfigUseCase(): SellConfigUseCase {
        return SellConfigUseCase()
    }





}
