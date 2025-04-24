package com.itechpro.domain.model.product



import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ProductDetail(
    val id: String? = null,
    val iddonvi: String? = null,
    val iddonvichuan: String? = null,
    val ten: String? = null,
    val filetxt: String? = null,
    val tungay: String? = null,
    val denngay: String? = null,
    val noidung: String? = null,
    val loaichitiet: String? = null,
    val khuyenmai: Double? = 0.0,
    val sotien: Double? = 0.0,
    val sotienkm: Double? = 0.0,
    val sotiensaukm: Double? = 0.0,
    val sotienhoahong: Double? = 0.0,
    val yeuthich: Int? = 0,
    val cothedatlich: Boolean? = false,
    val hoatdongtmdt: Boolean? = false,

)
