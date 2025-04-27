package com.itechpro.domain.model


data class ShareOption(
    val label: String,
    val iconRes: Int,
    val packageName: String? = null // Package app để share, nếu cần
)