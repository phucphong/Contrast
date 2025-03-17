package com.itechpro.domain.model
// ✅ BaseModel: Lớp cha với `os` mặc định là "Android"

// ✅ Account kế thừa BaseModel
data class Account(


    val noicap: String? = null,
    val id: String? = null,
    val ido: String? = null,
    val hoten: String? = null,
    val dienthoai: String? = null,
    val email: String? = null,

    val username: String? = null,
    val password: String? = null,
    val key: String? = null,
    val mamenu: String? = null,
    val hanhdong: String? = null,
    val noidungchinh: String? = null,
    val device: String? = null,
    val os: String? = "android",

)
