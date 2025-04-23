package com.itechpro.domain.model.product


import com.squareup.moshi.JsonClass

import android.os.Parcelable



@JsonClass(generateAdapter = true)
data class EvaluateDetail(

    var id: String? = null,
    var noidung: String? = null,
    var nguoidanhgia: String? = null,
    var thoigiandanhgia: String? = null,
    var anhdaidien: String? = null,
    var diem: String? = null,
    var lst_dinhkem: List<AttachFile> = arrayListOf(),

    )

