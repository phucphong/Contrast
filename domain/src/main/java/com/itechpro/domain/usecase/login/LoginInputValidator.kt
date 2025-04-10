package com.itechpro.domain.usecase.login

import com.itechpro.domain.usecase.customer.ValidateCustomerUseCase




import com.itechpro.domain.model.ValidationResult

class LoginInputValidator(private val validate: ValidateLoginUseCase) {

    fun validateAll(

        account: String,
        password: String,

        ): ValidationResult {
        val steps = listOf(
            validate.validateAccount(account),
            validate.validatePassWord(password),

            )

        steps.forEach { if (!it.success) return it }



        return ValidationResult(true)
    }
}

