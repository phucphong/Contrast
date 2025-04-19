package com.itechpro.domain.model
import com.squareup.moshi.JsonClass
@JsonClass(generateAdapter = true)
data class News(
    val id: String,
    val ten: String,
    val loai: String,
    val noidung: String,
    val soluongdoc: Int,
    val filetxt: String,
    val cd: String,
    val tungay: String,
    val denngay: String,
    val nguoidang: String
)
