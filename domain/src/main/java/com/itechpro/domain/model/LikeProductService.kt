package com.itechpro.domain.model


import com.squareup.moshi.JsonClass



@JsonClass(generateAdapter = true)
data class LikeProductService(
    var mamenu: String? = "",
    var hanhdong: String? = "",
    var device: String? = "",
    var os: String? = "android",
    var noidungchinh: String? = "",
    var id: String? = "",
    var idsanpham: String? = "",
    var iddonvi: String? = "",
    var idlydobaocao: String? = "",
    var noidung: String? = "",
    var loaitk: String? = "",

    )
