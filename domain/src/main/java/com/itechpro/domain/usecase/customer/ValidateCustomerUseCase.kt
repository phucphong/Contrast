package com.itechpro.domain.usecase.customer



import com.itechpro.domain.model.ValidationResult
import com.itechpro.domain.enumApp.ValidationErrorType

class ValidateCustomerUseCase {

    fun validateFullName(fullName: String): ValidationResult {
        return if (fullName.isEmpty()||fullName=="null") {
            ValidationResult(false, ValidationErrorType.EMPTY_NAME.name)
        } else {
            ValidationResult(true)
        }
    }

    fun validateContactPerson(contactPersonId: String): ValidationResult {
        return if (contactPersonId.isEmpty()||contactPersonId=="null") {
            ValidationResult(false, ValidationErrorType.CONTACT_PERSON.name)
        } else {
            ValidationResult(true)
        }
    }




    fun validateEmail(email: String?): ValidationResult {
        return  if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            ValidationResult(false, ValidationErrorType.INVALID_EMAIL.name)
        } else {
            ValidationResult(true)
        }
    }



}
