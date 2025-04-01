package com.itechpro.domain.usecase.register

import android.util.Log
import com.itechpro.domain.model.ValidationResult
import com.itechpro.domain.model.validation.ValidationErrorType

class ValidateRegisterUseCase {

    fun validateFullName(fullName: String): ValidationResult {
        return if (fullName.isBlank()) {
            ValidationResult(false, ValidationErrorType.EMPTY_NAME.name)
        } else {
            ValidationResult(true)
        }
    }

    fun validatePhone(phone: String): ValidationResult {
        return if (phone.isBlank()) {
            ValidationResult(false, ValidationErrorType.EMPTY_PHONE.name)
        } else {
            ValidationResult(true)
        }
    }

    fun validateNewPassWord(newPassWord: String): ValidationResult {
        return if (newPassWord.isBlank()) {
            ValidationResult(false, ValidationErrorType.EMPTY_PASSWORD.name)
        } else {
            ValidationResult(true)
        }
    }

    fun validateRePassWord(rePassWord: String): ValidationResult {
        return if (rePassWord.isBlank()) {
            ValidationResult(false, ValidationErrorType.EMPTY_RE_PASSWORD.name)
        } else {
            ValidationResult(true)
        }
    }

    fun validatePassword(password: String, confirmPassword: String): ValidationResult {
        if (password.length !in 8..15) {
            return ValidationResult(false, ValidationErrorType.INVALID_LENGTH.name)
        }
        if (!password.any { it.isDigit() }) {
            return ValidationResult(false, ValidationErrorType.MISSING_NUMBER.name)
        }
        if (!password.any { it.isUpperCase() }) {
            return ValidationResult(false, ValidationErrorType.MISSING_UPPERCASE.name)
        }
        if (!password.any { it.isLowerCase() }) {
            return ValidationResult(false, ValidationErrorType.MISSING_LOWERCASE.name)
        }
        if (!password.any { "!@#\$%^&*()_+[]{}|;:',.<>?/`~".contains(it) }) {
            return ValidationResult(false, ValidationErrorType.MISSING_SPECIAL_CHAR.name)
        }
        if (password != confirmPassword) {
            return ValidationResult(false, ValidationErrorType.PASSWORD_MISMATCH.name)
        }
        return ValidationResult(true)
    }

    fun validateDayOfBirth(date: String?): ValidationResult {
        return if (date.isNullOrBlank()) {
            ValidationResult(false, ValidationErrorType.EMPTY_DAY.name)
        } else {
            ValidationResult(true)
        }
    }

    fun validateMonthOfBirth(date: String?): ValidationResult {
        return if (date.isNullOrBlank()) {

            Log.e("ValidationErrorType", ValidationErrorType.EMPTY_MONTH.name)
            ValidationResult(false, ValidationErrorType.EMPTY_MONTH.name)
        } else {
            ValidationResult(true)
        }
    }

    fun validateYearOfBirth(date: String?): ValidationResult {
        return if (date.isNullOrBlank()) {
            ValidationResult(false, ValidationErrorType.EMPTY_YEAR.name)
        } else {
            ValidationResult(true)
        }
    }
}
