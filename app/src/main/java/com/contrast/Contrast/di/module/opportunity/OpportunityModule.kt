package com.contrast.Contrast.di.module.OpportunityAddEdits
import com.itechpro.data.api.opportunity.OpportunityAddEditAPI
import com.itechpro.data.repository.opportunity.OpportunityAddEditRepositoryImpl

import com.itechpro.domain.repository.opportunity.OpportunityRepository
import com.itechpro.domain.usecase.opportunity.OpportunityAddEditUserCase


import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class OpportunityAddEditAddEditModule {

    @Binds
    @Singleton
    abstract fun bindOpportunityAddEditRepository(
        impl: OpportunityAddEditRepositoryImpl
    ): OpportunityRepository
}

@Module
@InstallIn(SingletonComponent::class)
object OpportunityAddEditNetworkModule {

    @Provides
    @Singleton
    fun provideOpportunityAddEditAPI(retrofit: Retrofit): OpportunityAddEditAPI {
        return retrofit.create(OpportunityAddEditAPI::class.java)
    }


    @Provides
    fun provideOpportunityAddEditUserCase(repository: OpportunityRepository): OpportunityAddEditUserCase {
        return OpportunityAddEditUserCase(repository)
    }


}
