package com.contrast.Contrast.di.module

import com.itechpro.data.api.RegisterAccountAPI
import com.itechpro.data.repository.RegisterAccountRepositoryImpl
import com.itechpro.domain.repository.RegisterAccountRepository
import com.itechpro.domain.usecase.account.ValidateAccountUseCase
import com.itechpro.domain.usecase.checkphoneEmail.CheckEmailUseCase
import com.itechpro.domain.usecase.checkphoneEmail.CheckPhoneUseCase
import com.itechpro.domain.usecase.register.RegisterAccountUseCase
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
abstract class RegisterAccountModule {

    @Binds
    @Singleton
    abstract fun bindRegisterAccountRepository(
        impl: RegisterAccountRepositoryImpl
    ): RegisterAccountRepository
}

@Module
@InstallIn(SingletonComponent::class)
object RegisterAccountNetworkModule {

    @Provides
    @Singleton
    fun provideRegisterAccountAPI( retrofit: Retrofit): RegisterAccountAPI {
        return retrofit.create(RegisterAccountAPI::class.java)
    }

    @Provides
    fun provideRegisterAccountUseCase(repository: RegisterAccountRepository): RegisterAccountUseCase {
        return RegisterAccountUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideCheckPhoneUseCase(repository: RegisterAccountRepository): CheckPhoneUseCase {
        return CheckPhoneUseCase(repository)
    }
    @Provides
    @Singleton
    fun provideCheckEmailUseCase(repository: RegisterAccountRepository): CheckEmailUseCase {
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
