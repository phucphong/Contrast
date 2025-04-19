package com.itechpro.domain.model
import com.squareup.moshi.JsonClass
@JsonClass(generateAdapter = true)
data class LanguageEntity(
    val id: Int?,
    val name: String?,
    val code: String?
)
