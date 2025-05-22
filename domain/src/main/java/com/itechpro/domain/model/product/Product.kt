package com.itechpro.domain.model.product

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Product(
    val id: String? = "",
    var iddonvi: String? = "",
    var idsanpham: String? = "",
    val iddonvichuan: String? = "",
    val ten: String? = "",
    val ma: String? = "",
    val filetxt: String? = "",
    val tungay: String? = "",
    val denngay: String? = "",
    var loaitk: String? = "",
    var mamenu: String? = "",
    var os: String? = "",
    var device: String? = "",
    var hanhdong: String? = "",
    var trangthai: String = "",
    var loaisanpham: String = "",
    val sotien: Double? = 0.0,
    val sotiensaukm: Double? = 0.0,
    val khuyenmai: Double? = 0.0,
    val diem: Double? = 0.0,
    val sotienhoahong: Double? = 0.0,
    val tylehoahong: Double? = 0.0,
    val diemtamtinh: Double? = 0.0,
    val soluongdonhang: Double? = 0.0,
    val sotiendoanhthu: Double? = 0.0,
    val sotientamtinhhuong: Double? = 0.0,
    val sotienhuong: Double? = 0.0,
    val cothedatlich: Boolean? = false,


    @Json(ignore = true)
    val noidung: String? = ""
)
