package com.itechpro.domain.model.product

import com.squareup.moshi.JsonClass
@JsonClass(generateAdapter = true)
data class InfoDetail(
    val label: String,
    val value: String,
    val highlight: Boolean = false,
    val showCheckbox: Boolean = false,
    val isChecked: Boolean = false,
    val onCheckedChange: ((Boolean) -> Unit)? = null
)
