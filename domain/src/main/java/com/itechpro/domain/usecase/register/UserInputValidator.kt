package com.itechpro.domain.usecase.register


import com.itechpro.domain.model.ValidationResult

class UserInputValidator(private val validate: ValidateRegisterUseCase) {

    fun validateAll(
        phone: String,
        fullName: String,
        password: String,
        confirmPassword: String,
        selectedDay: String?,
        selectedMonth: String?,
        selectedYear: String?
    ): ValidationResult {
        val steps = listOf(
            validate.validatePhone(phone),
            validate.validateFullName(fullName),
            validate.validateNewPassWord(password),
            validate.validateRePassWord(confirmPassword),
            validate.validatePassword(password, confirmPassword)
        )

        steps.forEach { if (!it.success) return it }

        // Nếu có nhập ngày sinh thì validate thêm
        if (!selectedDay.isNullOrBlank() || !selectedMonth.isNullOrBlank() || !selectedYear.isNullOrBlank()) {
            val dobValidations = listOf(
                validate.validateDayOfBirth(selectedDay),
                validate.validateMonthOfBirth(selectedMonth),
                validate.validateYearOfBirth(selectedYear)
            )
            dobValidations.forEach { if (!it.success) return it }
        }

        return ValidationResult(true)
    }
}

