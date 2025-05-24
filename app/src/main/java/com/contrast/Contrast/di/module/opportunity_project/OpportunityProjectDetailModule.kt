package com.contrast.Contrast.di.module.opportunity_project


import com.itechpro.data.api.opportunity_project.OpportunityProjectDetailAPI

import com.itechpro.data.repository.opportunity.OpportunityProjectDetailRepositoryImpl

import com.itechpro.domain.repository.opportunity_project.OpportunityProjectDetailRepository
import com.itechpro.domain.usecase.opportunity_project.OpportunityProjectDetailUseCase


import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class OpportunityProjectDetailModule {
    @Binds
    @Singleton
    abstract fun bindOderRepository(
        impl: OpportunityProjectDetailRepositoryImpl
    ): OpportunityProjectDetailRepository
}

@Module
@InstallIn(SingletonComponent::class)
object OpportunityDetailNetworkModule {

    @Provides
    @Singleton
    fun provideOpportunityDetailAPI(retrofit: Retrofit): OpportunityProjectDetailAPI {
        return retrofit.create(OpportunityProjectDetailAPI::class.java)
    }


    @Provides
    fun provideOpportunityDetailUseCase(repository: OpportunityProjectDetailRepository): OpportunityProjectDetailUseCase {
        return OpportunityProjectDetailUseCase(repository)
    }


}
