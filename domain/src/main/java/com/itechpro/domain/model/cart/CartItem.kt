package com.itechpro.domain.model.cart

import com.squareup.moshi.JsonClass
@JsonClass(generateAdapter = true)
data class CartItem(
    val id: String?="",
    val ido: String?="",
    val idnguoigioithieu: String?="",
    val idsp: String?="",
    val iddonvi: String?="",
    val loaicapnhat: String?="add",
    val Column1: String?="",
    val trangthai: String?="",
    val mamenu: String?="giohang",
    val os: String?="android",
    val hanhdong: String?="",
    val device: String?="",
    val soluong: Double?=0.0,
    val dongia: Double?=0.0,
    val ck: Double?=0.0,

)

