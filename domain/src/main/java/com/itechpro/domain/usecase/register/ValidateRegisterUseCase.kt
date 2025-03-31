package com.itechpro.domain.usecase.register


import com.itechpro.domain.model.ValidationResult




class ValidateRegisterUseCase {

    fun validateFullName(fullName: String): ValidationResult {
        return if (fullName.isBlank()) {
            ValidationResult(false, "EMPTY_NAME") // ✅ Trả về mã lỗi, không dùng trực tiếp chuỗi
        } else {
            ValidationResult(true)
        }
    }
    fun validatePhone(phone: String): ValidationResult {
        return if (phone.isBlank()) {
            ValidationResult(false, "EMPTY_PHONE") // ✅ Trả về mã lỗi, không dùng trực tiếp chuỗi
        } else {
            ValidationResult(true)
        }
    }


    fun validateNewPassWord(newPassWord: String): ValidationResult {
        return if (newPassWord.isBlank()) {
            ValidationResult(false, "EMPTY_PASSWORD") // ✅ Trả về mã lỗi, không dùng trực tiếp chuỗi
        } else {
            ValidationResult(true)
        }
    }
    fun validateRePassWord(rePassWord: String): ValidationResult {
        return if (rePassWord.isBlank()) {
            ValidationResult(false, "EMPTY_RE_PASSWORD") // ✅ Trả về mã lỗi, không dùng trực tiếp chuỗi
        } else {
            ValidationResult(true)
        }
    }

    fun validatePassword(password: String, confirmPassword: String): ValidationResult {
        if (password.length !in 8..15) {
            return ValidationResult(false, "INVALID_LENGTH") // ✅ Mật khẩu phải từ 8-15 ký tự
        }
        if (!password.any { it.isDigit() }) {
            return ValidationResult(false, "MISSING_NUMBER") // ✅ Chưa có chữ số
        }
        if (!password.any { it.isUpperCase() }) {
            return ValidationResult(false, "MISSING_UPPERCASE") // ✅ Chưa có chữ in hoa
        }
        if (!password.any { it.isLowerCase() }) {
            return ValidationResult(false, "MISSING_LOWERCASE") // ✅ Chưa có chữ thường
        }
        if (!password.any { "!@#$%^&*()_+[]{}|;:',.<>?/`~".contains(it) }) {
            return ValidationResult(false, "MISSING_SPECIAL_CHAR") // ✅ Chưa có ký tự đặc biệt
        }
        if (password != confirmPassword) {
            return ValidationResult(false, "PASSWORD_MISMATCH") // ✅ Mật khẩu nhập lại không khớp
        }
        return ValidationResult(true) // ✅ Hợp lệ
    }

    fun validateDateOfBirth(date: String?): ValidationResult {
        if (date.isNullOrBlank()) {
            return ValidationResult(false, "EMPTY_DATE")
        }

        val regex = Regex("""\d{2}/\d{2}/\d{4}""")
        if (!regex.matches(date)) {
            return ValidationResult(false, "INVALID_DATE_FORMAT")
        }

        return ValidationResult(true)
    }


}
