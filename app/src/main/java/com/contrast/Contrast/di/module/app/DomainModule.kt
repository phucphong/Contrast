package com.contrast.Contrast.di.module.app
import com.itechpro.domain.usecase.category.FilterCategoryUseCase
import com.itechpro.domain.usecase.customer.CustomerInputValidator
import com.itechpro.domain.usecase.customer.ValidateCustomerUseCase
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
