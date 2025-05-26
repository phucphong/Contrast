package com.contrast.Contrast.di.module.opportunity



import com.itechpro.data.api.opportunity.OpportunityListAPI
import com.itechpro.data.repository.opportunity.OpportunityListRepositoryImpl


import com.itechpro.domain.repository.opportunity.OpportunityListRepository

import com.itechpro.domain.usecase.opportunity.OpportunityListUserCase


import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class OpportunityListModule {

    @Binds
    @Singleton
    abstract fun bindOpportunityListRepository(
        impl: OpportunityListRepositoryImpl
    ): OpportunityListRepository



}

@Module
@InstallIn(SingletonComponent::class)
object OpportunityListNetworkModule {

    @Provides
    @Singleton
    fun provideOpportunityListAPI(retrofit: Retrofit): OpportunityListAPI {
        return retrofit.create(OpportunityListAPI::class.java)
    }


    @Provides
    fun provideOpportunityListUserCase(repository: OpportunityListRepository): OpportunityListUserCase {
        return OpportunityListUserCase(repository)
    }


}
