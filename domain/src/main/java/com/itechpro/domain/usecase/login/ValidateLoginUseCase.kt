package com.itechpro.domain.usecase.login


import com.itechpro.domain.model.ValidationResult
import com.itechpro.domain.enumApp.ValidationErrorType

class ValidateLoginUseCase {



    fun validateAccount(account: String): ValidationResult {
        return if (account.isBlank()) {
            ValidationResult(false, ValidationErrorType.EMPTY_ACCOUNT.name)
        } else {
            ValidationResult(true)
        }
    }
    fun validatePassWord(newPassWord: String): ValidationResult {
        return if (newPassWord.isBlank()) {
            ValidationResult(false, ValidationErrorType.EMPTY_PASSWORD.name)
        } else {
            ValidationResult(true)
        }
    }




}
