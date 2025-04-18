package com.itechpro.domain.model.cart

data class CartResult(
    val items: List<CartItem>,
    val totalCount: Int
)