package com.itechpro.domain.model
import com.squareup.moshi.JsonClass
@JsonClass(generateAdapter = true)
data class Voucher(
    val title: String,
    val expiryDate: String,

    val type: String // New field to determine the type of voucher
)
