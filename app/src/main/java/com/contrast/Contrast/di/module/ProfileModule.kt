package com.contrast.Contrast.di.module



import com.itechpro.domain.usecase.Profile.ProfileUseCase

import com.itechpro.data.api.ProfileAPI
import com.itechpro.data.repository.ProfileRepositoryImpl
import com.itechpro.domain.repository.ProfileRepository
import com.itechpro.domain.usecase.profile.ProfileUseCase


import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class ProfileModule {

    @Binds
    @Singleton
    abstract fun bindProfileRepository(
        impl: ProfileRepositoryImpl
    ): ProfileRepository
}

@Module
@InstallIn(SingletonComponent::class)
object ProfileNetworkModule {

    @Provides
    @Singleton
    fun provideProfileAPI( retrofit: Retrofit): ProfileAPI {
        return retrofit.create(ProfileAPI::class.java)
    }

    @Provides
    fun provideProfileUseCase(repository: ProfileRepository): ProfileUseCase {
        return ProfileUseCase(repository)
    }





}
