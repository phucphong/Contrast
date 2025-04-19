package com.itechpro.domain.model
import com.squareup.moshi.JsonClass
@JsonClass(generateAdapter = true)

data class FireBaseClient(
    val mamenu: String? = null,
    val id: String? = null,
    val filetxt: String? = null,
    var aosid: String? = null,
    var idnhanvien: String? = null
)
