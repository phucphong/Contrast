package com.contrast.Contrast.di.module.opportunity_project



import com.itechpro.data.api.opportunity_project.OpportunityProjectAPI

import com.itechpro.data.repository.opportunity.OpportunityProjectRepositoryImpl

import com.itechpro.domain.repository.opportunity_project.OpportunityProjectRepository

import com.itechpro.domain.usecase.opportunity_project.OpportunityProjectUserCase


import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class OpportunityProjectModule {

    @Binds
    @Singleton
    abstract fun bindOpportunityRepository(
        impl: OpportunityProjectRepositoryImpl
    ): OpportunityProjectRepository
}

@Module
@InstallIn(SingletonComponent::class)
object OpportunityNetworkModule {

    @Provides
    @Singleton
    fun provideOpportunityAPI(retrofit: Retrofit): OpportunityProjectAPI {
        return retrofit.create(OpportunityProjectAPI::class.java)
    }


    @Provides
    fun provideOpportunityUserCase(repository: OpportunityProjectRepository): OpportunityProjectUserCase {
        return OpportunityProjectUserCase(repository)
    }


}
