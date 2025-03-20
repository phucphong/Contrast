package com.itechpro.domain.model


import android.graphics.Color

// Data model
data class Voucher(
    val title: String,
    val expiryDate: String,

    val type: String // New field to determine the type of voucher
)
