package com.itechpro.domain.model.order

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Order(
    val pos: Int? = 0,
    val id: String? = null,
    val ma: String? = null,
    val ten: String? = null,
    val tensanpham: String? = null,
    val hinhanhtxt: String? = null,
    val ngaytaotxt: String? = null,
    val ngaytao: String? = null,
    val diachigiaohang: String? = null,
    val tenkhachhang: String? = null,
    val ghichu: String? = null,
    val filetxt: String? = null,
    val cksotien: Double? = 0.0,
    val soluong: Double? = 0.0,

    val ck: Double? = 0.0,
    val dongia: Double? = 0.0,
    val tongtien: Double? = 0.0,
    val tongtienconno: Double? = 0.0,
    val tongtientruocchietkhau: Double? = 0.0,
    val tongtienchietkhau: Double? = 0.0,
    val tongtiensauchietkhau: Double? = 0.0,
    val tongtiendatt: Double? = 0.0
)