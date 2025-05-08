package com.itechpro.domain.model.cart

import com.squareup.moshi.JsonClass
@JsonClass(generateAdapter = true)
data class CartItem(
    var id: String?="",
    var ido: String?="",
    val tensanpham: String?="",
    val idnguoigioithieu: String?="",
    val idsp: String?="",
    val iddonvi: String?="",
    val filetxt: String?="",
    var loaicapnhat: String?="add",
    val Column1: String?="",
    val trangthai: String?="",
    var mamenu: String?="giohang",
    val os: String?="android",
    var hanhdong: String?="",
    var device: String?="",
    var soluong: Double?=0.0,
    val dongia: Double?=0.0,
    val sotiensaukm: Double?=0.0,
    val sotienkm: Double?=0.0,
    val sotienkm1sp: Double?=0.0,
    val ck: Double?=0.0,
    val phantram: Double?=0.0,
    var isChecked: Boolean?=false,

    )

