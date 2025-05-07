package com.contrast.Contrast.di.module

import com.itechpro.data.api.CartAPI
import com.itechpro.data.repository.CartRepositoryImpl
import com.itechpro.domain.repository.CartRepository
import com.itechpro.domain.usecase.cart.CartUseCase

import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class CartModule {

    @Binds
    @Singleton
    abstract fun bindCartRepository(
        impl: CartRepositoryImpl
    ): CartRepository
}

@Module
@InstallIn(SingletonComponent::class)
object CartNetworkModule {

    @Provides
    @Singleton
    fun provideCartAPI( retrofit: Retrofit): CartAPI {
        return retrofit.create(CartAPI::class.java)
    }

    @Provides
    fun provideCartUseCase(repository: CartRepository): CartUseCase {
        return CartUseCase(repository)
    }




}
