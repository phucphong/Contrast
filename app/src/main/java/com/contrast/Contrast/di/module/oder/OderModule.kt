package com.contrast.Contrast.di.module.oder




import com.itechpro.data.api.oder.OderAPI
import com.itechpro.data.repository.OderRepositoryImpl
import com.itechpro.domain.repository.OderRepository
import com.itechpro.domain.usecase.oder.OdersUserCase


import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class OderModule {

    @Binds
    @Singleton
    abstract fun bindOderRepository(
        impl: OderRepositoryImpl
    ): OderRepository
}

@Module
@InstallIn(SingletonComponent::class)
object OderNetworkModule {

    @Provides
    @Singleton
    fun provideOderAPI( retrofit: Retrofit): OderAPI {
        return retrofit.create(OderAPI::class.java)
    }

    @Provides
    fun provideOdersUserCase(repository: OderRepository): OdersUserCase {
        return OdersUserCase(repository)
    }






}
