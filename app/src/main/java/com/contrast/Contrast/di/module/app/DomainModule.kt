package com.contrast.Contrast.di.module.app
import com.itechpro.domain.usecase.account.ValidateAccountUseCase
import com.itechpro.domain.usecase.checkphoneEmail.CheckEmailUseCase
import com.itechpro.domain.usecase.checkphoneEmail.CheckPhoneUseCase
import com.itechpro.domain.usecase.register.ValidateRegisterUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DomainModule {
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



}
