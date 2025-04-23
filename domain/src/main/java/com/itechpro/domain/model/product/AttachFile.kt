package com.itechpro.domain.model.product
import com.squareup.moshi.JsonClass


@JsonClass(generateAdapter = true)
data class AttachFile(

    var id: String? = null,
    var tenfile: String? = null,
    var dinhkem: String? = null,
    var dinhdang: String? = null,

    )


