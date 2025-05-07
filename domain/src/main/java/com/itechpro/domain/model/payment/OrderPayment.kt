package com.itechpro.domain.model.payment

import com.itechpro.domain.model.cart.CartItem

data class OrderPayment(



    val idkhachhang: String? = "",

    val iddiembanle: String? = "",
    val madh: String? = "",
    val ghichu: String? = "",
    val diachigiaohang: String? = "",
    val loaitk: String? = null,
    val ckpt: String ="0.0",
    val listsanpham_dichvu: List<CartItem>? = arrayListOf()
)