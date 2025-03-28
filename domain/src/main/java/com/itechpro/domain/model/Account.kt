package com.itechpro.domain.model
// ✅ BaseModel: Lớp cha với `os` mặc định là "Android"

// ✅ Account kế thừa BaseModel
data class Account(

    val os: String? = "android",

    var hoten: String? = null,
    var id: String? = null,
    var ido: String? = null,
    var mamenu: String? = null,

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

    var key: String? = null

)
