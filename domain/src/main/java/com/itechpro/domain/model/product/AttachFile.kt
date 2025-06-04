package com.itechpro.domain.model.product
import com.squareup.moshi.JsonClass


@JsonClass(generateAdapter = true)
data class AttachFile(

    var id: String? = "",
    var tenfile: String? = "",
    var tenfilehienthi: String? = "",
    var dinhkem: String? = "",
    var dinhdang: String? = "",
    var dungluong: String? = "",
    var nguoidang: String? = "",
    var lud: String? = "",

    )


