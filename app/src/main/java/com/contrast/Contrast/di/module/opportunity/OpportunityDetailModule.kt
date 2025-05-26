package com.contrast.Contrast.di.module.opportunity


import com.itechpro.data.api.opportunity.OpportunityDetailAPI

import com.itechpro.data.repository.opportunity.OpportunityDetailRepositoryImpl

import com.itechpro.domain.repository.opportunity.OpportunityDetailRepository
import com.itechpro.domain.usecase.opportunity.OpportunityDetailUseCase


import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class OpportunityDetailModule {
    @Binds
    @Singleton
    abstract fun bindOpportunityDetailRepository(
        impl: OpportunityDetailRepositoryImpl
    ): OpportunityDetailRepository
}

@Module
@InstallIn(SingletonComponent::class)
object OpportunityDetailNetworkModule {

    @Provides
    @Singleton
    fun provideOpportunityDetailAPI(retrofit: Retrofit): OpportunityDetailAPI {
        return retrofit.create(OpportunityDetailAPI::class.java)
    }


    @Provides
    fun provideOpportunityDetailUseCase(repository: OpportunityDetailRepository): OpportunityDetailUseCase {
        return OpportunityDetailUseCase(repository)
    }


}
