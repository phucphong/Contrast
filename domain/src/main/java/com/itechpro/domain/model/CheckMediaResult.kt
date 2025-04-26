package com.itechpro.domain.model

data class CheckMediaResult(
    val validateMessage: String,
    val imageSelect: Int,
    val videoSelect: Int,
)