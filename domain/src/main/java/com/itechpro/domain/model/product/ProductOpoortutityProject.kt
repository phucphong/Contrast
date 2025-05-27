package com.itechpro.domain.model.product

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass



@JsonClass(generateAdapter = true)
data class ProductOpoortutityProject(
    val id: String? = "",
    val idsanpham: String? = "",
    val iddonvi: String? = "",
    val loaigiamgia: String? = "",
    val cd: String? = "",
    val avata: String? = "",
    val masanpham: String? = "",
    val tensanpham: String? = "",
    val tendonvi: String? = "",

    val idloaitien: String? = "",
    val mota: String? = "",
    val soluong: Double? = 0.0,
    val dongia: Double? = 0.0,
    val thanhtien: Double? = 0.0,
    val phantramgiamgia: Double? = 0.0,
    val sotiengiamgia: Double? = 0.0,
    val phantramthue: Double? = 0.0,
    val thanhtientruocthue: Double? = 0.0,



)
