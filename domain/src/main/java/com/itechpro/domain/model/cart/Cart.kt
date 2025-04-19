package com.itechpro.domain.model.cart

import com.squareup.moshi.JsonClass
@JsonClass(generateAdapter = true)
data class Cart(
    var Table: List<CartItem>,
    var Table1: List<CartItem>

    )

