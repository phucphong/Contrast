package com.contrast.Contrast.di.module

import com.itechpro.data.api.LoginAPI
import com.itechpro.data.repository.LoginRepositoryImpl
import com.itechpro.domain.repository.LoginRepository

import com.itechpro.domain.usecase.login.LoginInputValidator
import com.itechpro.domain.usecase.login.LoginUseCase
import com.itechpro.domain.usecase.login.ValidateLoginUseCase

import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class LoginModule {

    @Binds
    @Singleton
    abstract fun bindLoginRepository(
        impl: LoginRepositoryImpl
    ): LoginRepository
}

@Module
@InstallIn(SingletonComponent::class)
object LoginNetworkModule {

    @Provides
    @Singleton
    fun provideLoginAPI( retrofit: Retrofit): LoginAPI {
        return retrofit.create(LoginAPI::class.java)
    }

    @Provides
    fun provideLoginUseCase(repository: LoginRepository): LoginUseCase {
        return LoginUseCase(repository)
    }

 

    // ---------------   đăng nhập tài khoản ------------------
    @Provides
    @Singleton
    fun provideValidateLoginUseCase(): ValidateLoginUseCase {
        return ValidateLoginUseCase()
    }



    @Provides
    @Singleton
    fun provideUserInputValidator(
        ValidateLoginUseCase: ValidateLoginUseCase
    ): LoginInputValidator {
        return LoginInputValidator(ValidateLoginUseCase)
    }
}
