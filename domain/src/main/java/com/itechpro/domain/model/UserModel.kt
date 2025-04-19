package com.itechpro.domain.model

import com.squareup.moshi.JsonClass
@JsonClass(generateAdapter = true)
data class UserModel(
    val id: String,
    val name: String,

)
