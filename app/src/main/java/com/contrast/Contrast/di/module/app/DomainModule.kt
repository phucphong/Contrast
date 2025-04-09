package com.contrast.Contrast.di.module.app
import com.itechpro.domain.usecase.account.ValidateAccountUseCase
import com.itechpro.domain.usecase.catogory.FilterCategoryUseCase
import com.itechpro.domain.usecase.customer.CustomerInputValidator
import com.itechpro.domain.usecase.customer.CustomerUseCase
import com.itechpro.domain.usecase.customer.ValidateCustomerUseCase
import com.itechpro.domain.usecase.register.ValidateRegisterUseCase
import com.itechpro.domain.usecase.register.UserInputValidator
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
    fun provideFilterCategoryUseCase(): FilterCategoryUseCase {
        return FilterCategoryUseCase()
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

    // ---------------   khách hàng ------------------
    @Provides
    @Singleton
    fun provideValidateCustomerUseCase(): ValidateCustomerUseCase {
        return ValidateCustomerUseCase()
    }


    @Provides
    @Singleton
    fun provideCustomerInputValidator(
        validateRegisterUseCase: ValidateCustomerUseCase
    ): CustomerInputValidator {
        return CustomerInputValidator(validateRegisterUseCase)
    }

    // ---------------   Liên hệ ------------------

}
