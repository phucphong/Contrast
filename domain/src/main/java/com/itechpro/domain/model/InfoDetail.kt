package com.itechpro.domain.model


data class InfoDetail(
    val label: String,
    val value: String,
    val highlight: Boolean = false,
    val showCheckbox: Boolean = false,
    val isChecked: Boolean = false,
    val onCheckedChange: ((Boolean) -> Unit)? = null
)
