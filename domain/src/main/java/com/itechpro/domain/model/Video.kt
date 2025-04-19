package com.itechpro.domain.model
import com.squareup.moshi.JsonClass
@JsonClass(generateAdapter = true)
data class Video(
    val ten: String,
    val loai: String,
    val idvideo: String,
    val url: String,
    val showHeart: Boolean? = false,
    val showAdd: Boolean? = false,

)
