package com.itechpro.domain.model.report


import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ReportProduct(
    val id: String? = "",
    var ten: String? = "",
    var mota: String? = "",

)
