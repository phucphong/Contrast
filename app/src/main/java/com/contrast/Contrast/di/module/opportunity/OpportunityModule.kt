package com.contrast.Contrast.di.module.OpportunityAddEdits
import com.itechpro.data.api.opportunity.OpportunityAPI
import com.itechpro.data.repository.opportunity.OpportunityRepositoryImpl

import com.itechpro.domain.repository.opportunity.OpportunitysRepository
import com.itechpro.domain.usecase.opportunity.OpportunityUserCase


import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class OpportunityModule {

    @Binds
    @Singleton
    abstract fun bindOpportunityRepository(
        impl: OpportunityRepositoryImpl
    ): OpportunitysRepository
}

@Module
@InstallIn(SingletonComponent::class)
object OpportunityNetworkModule {

    @Provides
    @Singleton
    fun provideOpportunityAPI(retrofit: Retrofit): OpportunityAPI {
        return retrofit.create(OpportunityAPI::class.java)
    }


    @Provides
    fun provideOpportunityUserCase(repository: OpportunitysRepository): OpportunityUserCase {
        return OpportunityUserCase(repository)
    }


}
