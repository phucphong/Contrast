package com.itechpro.domain.usecase.customer




import com.itechpro.domain.model.ValidationResult

class CustomerInputValidator(private val validate: ValidateCustomerUseCase) {

    fun validateAll(

        fullName: String,
        contactPerson: String,
        email: String?,

    ): ValidationResult {
        val steps = listOf(
            validate.validateFullName(fullName),
            validate.validateContactPerson(contactPerson),

        )

        steps.forEach { if (!it.success) return it }

        if (!email.isNullOrBlank()&&email!="null") {
            val dobValidations = listOf(
                validate.validateEmail(email),

            )
            dobValidations.forEach { if (!it.success) return it }
        }
        return ValidationResult(true)
    }
}

