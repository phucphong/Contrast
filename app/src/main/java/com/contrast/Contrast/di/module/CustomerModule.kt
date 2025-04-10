package com.contrast.Contrast.di.module


import com.itechpro.data.api.CustomerAPI
import com.itechpro.data.repository.CustomerRepositoryImpl
import com.itechpro.domain.repository.CustomerRepository
import com.itechpro.domain.usecase.customer.CheckEmailUseCase
import com.itechpro.domain.usecase.customer.CheckPhoneUseCase

import com.itechpro.domain.usecase.customer.CustomerUseCase

import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class CustomerModule {

    @Binds
    @Singleton
    abstract fun bindCustomerRepository(
        impl: CustomerRepositoryImpl
    ): CustomerRepository
}

@Module
@InstallIn(SingletonComponent::class)
object CustomerNetworkModule {

    @Provides
    @Singleton
    fun provideCustomerAPI( retrofit: Retrofit): CustomerAPI {
        return retrofit.create(CustomerAPI::class.java)
    }

    @Provides
    fun provideCustomerUseCase(repository: CustomerRepository): CustomerUseCase {
        return CustomerUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideCheckPhoneUseCase(repository: CustomerRepository): CheckPhoneUseCase {
        return CheckPhoneUseCase(repository)
    }
    @Provides
    @Singleton
    fun provideCheckEmailUseCase(repository: CustomerRepository): CheckEmailUseCase {
        return CheckEmailUseCase(repository)
    }
}
