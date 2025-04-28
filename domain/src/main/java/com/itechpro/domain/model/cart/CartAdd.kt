package com.itechpro.domain.model.cart



import com.squareup.moshi.JsonClass
@JsonClass(generateAdapter = true)
data class CartAdd(
    val idsp: String?="",
    val idnguoigioithieu: String?="",
    val iddonvi: String?="",
    val device: String?="",
    val hanhdong: String?="",
    val loaicapnhat: String?="",
    val mamenu: String?="",
    val soluong: Int?=0,
    val dongia: Double?=0.0,
    val ck: Double?=0.0,



    )

