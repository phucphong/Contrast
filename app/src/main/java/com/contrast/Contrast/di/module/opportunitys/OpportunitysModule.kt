package com.contrast.Contrast.di.module.opportunitys




import com.itechpro.data.api.Opportunity.OpportunityAPI
import com.itechpro.data.api.opportunity.OpportunityAPI
import com.itechpro.data.repository.OpportunityRepositoryImpl
import com.itechpro.data.repository.opportunity.OpportunityRepositoryImpl
import com.itechpro.domain.repository.OpportunityRepository
import com.itechpro.domain.repository.OpportunityRepository
import com.itechpro.domain.repository.opportunity.OpportunityRepository
import com.itechpro.domain.usecase.Opportunity.OpportunityUserCase
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
abstract class OpportunitysModule {

    @Binds
    @Singleton
    abstract fun bindOpportunityRepository(
        impl: OpportunityRepositoryImpl
    ): OpportunityRepository
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
    fun provideOpportunityUserCase(repository: OpportunityRepository): OpportunityUserCase {
        return OpportunityUserCase(repository)
    }


}
