package com.itechpro.domain.model


import com.squareup.moshi.JsonClass
@JsonClass(generateAdapter = true)
data class Login(
    val token: String?="",
    val idnhanvien: String?="",
    val isadmin: String?="",
    val isadmincoso: String?="",
    val iddiembanle: String?="",
    val tendiambanle: String?="",
    val idcongty: String?="",
    val permissionmobile: String?="",
    val idkh: String?="",
    val xemdtemkh: String?="",
    val idlh: String?="",
    val loaikh: String?="",
    val hoten: String?="",
    val idguid: String?="",
    var Username: String?="",
    var Password: String?="",
    val mamenu: String?="",
    val hanhdong: String?="",
    val noidungchinh: String?="",
    val device: String?="",

)
