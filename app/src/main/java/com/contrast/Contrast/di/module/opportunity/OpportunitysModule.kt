package com.contrast.Contrast.di.module.opportunity



import com.itechpro.data.api.opportunity.OpportunitysAPI
import com.itechpro.data.repository.opportunity.OpportunitysRepositoryImpl


import com.itechpro.domain.repository.opportunity.OpportunitysRepository

import com.itechpro.domain.usecase.opportunity.OpportunitysUserCase


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
    abstract fun bindOpportunitysRepository(
        impl: OpportunitysRepositoryImpl
    ): OpportunitysRepository
}

@Module
@InstallIn(SingletonComponent::class)
object OpportunitysNetworkModule {

    @Provides
    @Singleton
    fun provideOpportunityAPI(retrofit: Retrofit): OpportunitysAPI {
        return retrofit.create(OpportunitysAPI::class.java)
    }


    @Provides
    fun provideOpportunitysUserCase(repository: OpportunitysRepository): OpportunitysUserCase {
        return OpportunitysUserCase(repository)
    }


}
