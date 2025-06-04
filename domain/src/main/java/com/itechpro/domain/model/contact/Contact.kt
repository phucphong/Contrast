package com.itechpro.domain.model.contact



data class Contact(

    val id: String? = "",
    var ids: String? = "",
    var idcohoikinhdoanh: String? = "",
    var idduan: String? = "",
    val ten: String? = "",
    val hoten: String? = "",
    val xungho: String? = "",
    val tenlienhe: String? = "",
    val ngaysinh: String? = "",
    val diachi: String? = "",

    val dienthoai: String? = "",
    val dienthoai1: String? = "",
    val dienthoainharieng: String? = "",
    val email: String? = "",
    val email1: String? = "",
    val tenkhachhang: String? = "",
    val loaikhachhang: String? = "",
    val idkhachhang: String? = "0",
    val makhachhang: String? = "",
    val hinhanhtxt: String? = "",
    var lienhechinh: Boolean? = false,
    var checked: Boolean? = false,
)