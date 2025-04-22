package com.itechpro.domain.model


import com.squareup.moshi.JsonClass
@JsonClass(generateAdapter = true)
data class SliderHome(
    val id: String?="",
    val filetxt: String?="",

    )
