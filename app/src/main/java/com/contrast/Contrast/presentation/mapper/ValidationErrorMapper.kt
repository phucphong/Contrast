package com.contrast.Contrast.presentation.mapper


import com.contrast.Contrast.R
import com.itechpro.domain.enumApp.ValidationErrorType


object ValidationErrorMapper {
    fun toMessageResId(error: ValidationErrorType): Int {
        return when (error) {
            ValidationErrorType.EMPTY_PHONE -> R.string.error_empty_phone
            ValidationErrorType.INVALID_PHONE -> R.string.error_invalid_phone
            ValidationErrorType.EMPTY_NAME -> R.string.error_empty_fullname
            ValidationErrorType.EMPTY_PASSWORD -> R.string.error_empty_password
            ValidationErrorType.EMPTY_RE_PASSWORD -> R.string.error_empty_re_password
            ValidationErrorType.INVALID_LENGTH -> R.string.error_invalid_length
            ValidationErrorType.MISSING_NUMBER -> R.string.error_missing_number
            ValidationErrorType.MISSING_UPPERCASE -> R.string.error_missing_uppercase
            ValidationErrorType.MISSING_LOWERCASE -> R.string.error_missing_lowercase
            ValidationErrorType.MISSING_SPECIAL_CHAR -> R.string.error_missing_special_char
            ValidationErrorType.PASSWORD_MISMATCH -> R.string.error_password_mismatch
            ValidationErrorType.EMPTY_DAY -> R.string.error_empty_dob_day
            ValidationErrorType.EMPTY_MONTH -> R.string.error_empty_dob_month
            ValidationErrorType.EMPTY_YEAR -> R.string.error_empty_dob_year
            ValidationErrorType.UNKNOWN -> R.string.error_unknown
            ValidationErrorType.CONTACT_PERSON ->  R.string.error_empty_contact_person
            ValidationErrorType.INVALID_EMAIL -> R.string.error_email
        }
    }
}
