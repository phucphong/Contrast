package com.itechpro.domain.model.product

data class ProductDetailInput(
    val unitPrice: String,
    val quantity: String,
    val vat: String,
    val discount: String,
    val isPercent: Boolean
)
