package com.itechpro.domain.model.evaluate


import com.squareup.moshi.JsonClass

import com.itechpro.domain.model.product.AttachFile


@JsonClass(generateAdapter = true)
data class EvaluateDetail(

    var id: String? = null,
    var noidung: String? = null,
    var nguoidanhgia: String? = null,
    var thoigiandanhgia: String? = null,
    var anhdaidien: String? = null,
    var diem: Double? = 0.0,
    var lst_dinhkem: List<AttachFile> = arrayListOf(),

    )
