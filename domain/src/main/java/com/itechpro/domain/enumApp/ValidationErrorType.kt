package com.itechpro.domain.enumApp

// domain.model.validation
enum class ValidationErrorType {
    EMPTY_PHONE,
    INVALID_PHONE,
    EMPTY_NAME,
    EMPTY_PASSWORD,
    EMPTY_RE_PASSWORD,
    EMPTY_DAY,
    EMPTY_MONTH,
    EMPTY_YEAR,
    INVALID_LENGTH,
    MISSING_NUMBER,
    MISSING_UPPERCASE,
    MISSING_LOWERCASE,
    MISSING_SPECIAL_CHAR,
    PASSWORD_MISMATCH,
    UNKNOWN;

    companion object {
        fun fromCode(code: String?): ValidationErrorType {
            return entries.firstOrNull { it.name == code } ?: UNKNOWN
        }
    }
}
