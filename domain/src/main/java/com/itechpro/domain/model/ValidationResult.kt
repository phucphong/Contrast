package com.itechpro.domain.model


data class ValidationResult(
    val success: Boolean,
    val message: String? = "" // ✅ Trả về mã lỗi thay vì chuỗi trực tiếp
)
