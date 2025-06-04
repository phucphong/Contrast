package com.contrast.Contrast.di.module.contact



import com.itechpro.data.api.contact.ContactDetailAPI

import com.itechpro.data.repository.contact.ContactDetailRepositoryImpl

import com.itechpro.domain.repository.contact.ContactDetailRepository
import com.itechpro.domain.usecase.contact.ContactDetailUseCase


import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class ContactDetailModule {
    @Binds
    @Singleton
    abstract fun bindContactDetailRepository(
        impl: ContactDetailRepositoryImpl
    ): ContactDetailRepository
}

@Module
@InstallIn(SingletonComponent::class)
object ContactDetailNetworkModule {

    @Provides
    @Singleton
    fun provideContactDetailAPI(retrofit: Retrofit): ContactDetailAPI {
        return retrofit.create(ContactDetailAPI::class.java)
    }


    @Provides
    fun provideContactDetailUseCase(repository: ContactDetailRepository): ContactDetailUseCase {
        return ContactDetailUseCase(repository)
    }


}
