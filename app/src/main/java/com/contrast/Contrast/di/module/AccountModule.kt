package com.contrast.Contrast.di.module



import com.itechpro.data.api.AccountAPI
import com.itechpro.data.api.RegisterAccountAPI
import com.itechpro.data.repository.AccountRepositoryImpl
import com.itechpro.domain.repository.AccountRepository
import com.itechpro.domain.repository.RegisterAccountRepository
import com.itechpro.domain.usecase.account.AccountUseCase
import com.itechpro.domain.usecase.register.RegisterAccountUseCase
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
abstract class AccountModule {

    @Binds
    @Singleton
    abstract fun bindRegisterAccountRepository(
        impl: AccountRepositoryImpl
    ): AccountRepository
}

@Module
@InstallIn(SingletonComponent::class)
object AccountNetworkModule {

    @Provides
    @Singleton
    fun provideAccountAPI(@Named("CustomDomain") retrofit: Retrofit): AccountAPI {
        return retrofit.create(AccountAPI::class.java)
    }

    @Provides
    fun provideAccountUseCase(repository: AccountRepository): AccountUseCase {
        return AccountUseCase(repository)
    }
}
