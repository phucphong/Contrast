package com.contrast.Contrast.di.module.OpportunityAddEdits
import com.itechpro.data.api.opportunity_project.OpportunityProjectAddEditAPI
import com.itechpro.data.repository.opportunity.OpportunityProjectAddEditRepositoryImpl
import com.itechpro.domain.repository.opportunity_project.OpportunityProjectAddEditRepository
import com.itechpro.domain.usecase.opportunity_project.OpportunityProjectAddEditUserCase


import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class OpportunityProjectAddEditAddEditModule {

    @Binds
    @Singleton
    abstract fun bindOpportunityAddEditRepository(
        impl: OpportunityProjectAddEditRepositoryImpl
    ): OpportunityProjectAddEditRepository
}

@Module
@InstallIn(SingletonComponent::class)
object OpportunityAddEditNetworkModule {

    @Provides
    @Singleton
    fun provideOpportunityAddEditAPI(retrofit: Retrofit): OpportunityProjectAddEditAPI {
        return retrofit.create(OpportunityProjectAddEditAPI::class.java)
    }


    @Provides
    fun provideOpportunityAddEditUserCase(repository: OpportunityProjectAddEditRepository): OpportunityProjectAddEditUserCase {
        return OpportunityProjectAddEditUserCase(repository)
    }


}
