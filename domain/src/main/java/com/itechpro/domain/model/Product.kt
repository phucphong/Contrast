package com.itechpro.domain.model

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Product(
    val id: String? = null,
    val iddonvi: String? = null,
    val iddonvichuan: String? = null,
    val ten: String? = null,
    val filetxt: String? = null,
    val tungay: String? = null,
    val denngay: String? = null,
    val sotien: Double? = null,
    val sotiensaukm: Double? = null,

    val noidung: String? = null // 👈 KHÔNG cần @Transient nếu không có trong JSON
)
