package com.itechpro.domain.model

data class Video(
    val ten: String,
    val loai: String,
    val idvideo: String,
    val url: String,
    val showHeart: Boolean? = false,
    val showAdd: Boolean? = false,

)
