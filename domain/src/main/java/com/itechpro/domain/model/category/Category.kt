package com.itechpro.domain.model.category
import com.squareup.moshi.JsonClass
@JsonClass(generateAdapter = true)
data class Category(
    var id: String? =   "",
    var ma: String? =   "",
    var type: String? =   "",
    var idnhanvien: String? =  "",
    var name: String? =   "",
    val code: String? =   "",
    val description: String? =   "",
    val ten: String? =   "",
    val hoten: String? =   "",
    val filetxt: String? =   "",
    val idcohoi: String? =   "",
    var count: Int?=0,
    var icon: Int?=0,
    var hoatdong: Boolean?=false,
    val quantity: Int = 0
)
