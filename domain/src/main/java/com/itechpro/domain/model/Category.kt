package com.itechpro.domain.model
import com.squareup.moshi.JsonClass
@JsonClass(generateAdapter = true)
data class Category(
    var id: String? = "",
    var idnhanvien: String? = "",
    var name: String? = "",
    val code: String? = "",
    val description: String? = "",
    val ten: String? = "",
    val hoten: String? = "",
    val filetxt: String? = "",
    val idcohoi: String? = ""
)
