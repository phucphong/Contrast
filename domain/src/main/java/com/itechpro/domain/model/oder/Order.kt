package com.itechpro.domain.model.oder


data class Order(
    val pos: Int? = 0,
    val id: String? = null,
    val ma: String? = null,
    val ten: String? = null,
    val hinhanhtxt: String? = null,
    val ngaytaotxt: String? = null,
    val ngaytao: String? = null,
    val diachigiaohang: String? = null,
    val filetxt: String? = null,
    val tongtien: Double? = 0.0,
    val tongtienconno: Double? = 0.0,
    val tongtiendatt: Double? = 0.0
)
