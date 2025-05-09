package com.itechpro.domain.model
// ✅ BaseModel: Lớp cha với `os` mặc định là "Android"

import com.squareup.moshi.JsonClass
@JsonClass(generateAdapter = true)
data class Account(

    val os: String? = "android",

    var hoten: String? = null,
    var kq: String? = null,
    var id: String? = null,
    var ido: String? = null,
    var mamenu: String? = null,
    var anhdaidientxt: String? = null,
    var tencapdaily: String? = null,
    var thuongtrusonha: String? = null,

    var device: String? = null,
    var hanhdong: String? = null,
    var noidungchinh: String? = null,
    var maduthuong: String? = null,
    var tenchuongtrinh: String? = null,
    var thoigian: String? = null,

    var dienthoai: String? = null,
    var email: String? = null,
    var ngaysinh: String? = null,


    var username: String? = null,
    var diachi: String? = null,
    var password: String? = null,
    var idnguoigioithieu: String? = null,
    var phantramchietkhau: Double? = 0.0,

    var key: String? = null

)
