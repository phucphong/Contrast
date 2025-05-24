package com.contrast.Contrast.di.module.oder


import com.itechpro.data.api.oder.OderDetailAPI
import com.itechpro.data.repository.oder.OderDetailRepositoryImpl
import com.itechpro.domain.repository.oder.OderDetailRepository
import com.itechpro.domain.usecase.oder.OderDetailUserCase


import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class OderDetailModule {

    @Binds
    @Singleton
    abstract fun bindOderRepository(
        impl: OderDetailRepositoryImpl
    ): OderDetailRepository
}

@Module
@InstallIn(SingletonComponent::class)
object OderDetailNetworkModule {

    @Provides
    @Singleton
    fun provideOderDetailAPI(retrofit: Retrofit): OderDetailAPI {
        return retrofit.create(OderDetailAPI::class.java)
    }


    @Provides
    fun provideOderDetailUserCase(repository: OderDetailRepository): OderDetailUserCase {
        return OderDetailUserCase(repository)
    }


}
