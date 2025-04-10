package com.contrast.Contrast.di.module


import com.itechpro.data.api.LoginAPI
import com.itechpro.data.repository.LoginRepositoryImpl
import com.itechpro.domain.repository.LoginRepository
import com.itechpro.domain.usecase.account.ValidateAccountUseCase
import com.itechpro.domain.usecase.checkphoneEmail.CheckEmailUseCase
import com.itechpro.domain.usecase.checkphoneEmail.CheckPhoneUseCase
import com.itechpro.domain.usecase.login.LoginUseCase

import com.itechpro.domain.usecase.register.UserInputValidator
import com.itechpro.domain.usecase.register.ValidateRegisterUseCase

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

    @Provides
    @Singleton
    fun provideCheckPhoneUseCase(repository: LoginRepository): CheckPhoneUseCase {
        return CheckPhoneUseCase(repository)
    }
    @Provides
    @Singleton
    fun provideCheckEmailUseCase(repository: LoginRepository): CheckEmailUseCase {
        return CheckEmailUseCase(repository)
    }


    // ---------------   đăng ký tài khoản ------------------
    @Provides
    @Singleton
    fun provideValidateRegisterUseCase(): ValidateRegisterUseCase {
        return ValidateRegisterUseCase()
    }

    @Provides
    @Singleton
    fun provideValidateAccountUseCase(): ValidateAccountUseCase {
        return ValidateAccountUseCase()
    }


    @Provides
    @Singleton
    fun provideUserInputValidator(
        validateRegisterUseCase: ValidateRegisterUseCase
    ): UserInputValidator {
        return UserInputValidator(validateRegisterUseCase)
    }
}
