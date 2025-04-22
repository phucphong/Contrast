package com.itechpro.domain.model


import com.squareup.moshi.JsonClass
@JsonClass(generateAdapter = true)
data class Setting(

    var loaiquyen: String? = "",
    var trangthai: String? =  "",
    var bansanpham: String ?=  "",
    var bandichvu: String ?=  "",
    var uutienhienthisanpham: String ?=  ""

)
