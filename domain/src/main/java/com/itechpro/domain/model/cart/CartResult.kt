package com.itechpro.domain.model.cart
import com.squareup.moshi.JsonClass
@JsonClass(generateAdapter = true)
data class CartResult(
    val items: List<CartItem>,
    val totalCount: Int
)