package com.itechpro.domain.model
import android.graphics.drawable.Drawable
import com.squareup.moshi.JsonClass
@JsonClass(generateAdapter = true)
data class Category(
    var id: String? =   "",
    var ma: String? =   "",
    var idnhanvien: String? =  "",
    var name: String? =   "",
    val code: String? =   "",
    val description: String? =   "",
    val ten: String? =   "",
    val hoten: String? =   "",
    val filetxt: String? =   "",
    val idcohoi: String? =   "",
    var icon: Int?=0,
    var hoatdong: Boolean?=false
)
