package com.contrast.Contrast.di.module



import com.itechpro.data.api.PaymentAPI
import com.itechpro.data.repository.PaymentRepositoryImpl
import com.itechpro.domain.repository.PaymentRepository

import com.itechpro.domain.usecase.payment.PaymentUseCase

import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class PaymentModule {

    @Binds
    @Singleton
    abstract fun bindPaymentRepository(
        impl: PaymentRepositoryImpl
    ): PaymentRepository
}

@Module
@InstallIn(SingletonComponent::class)
object PaymentNetworkModule {

    @Provides
    @Singleton
    fun providePaymentAPI( retrofit: Retrofit): PaymentAPI {
        return retrofit.create(PaymentAPI::class.java)
    }

    @Provides
    fun providePaymentUseCase(repository: PaymentRepository): PaymentUseCase {
        return PaymentUseCase(repository)
    }




}
