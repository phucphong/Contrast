package com.itechpro.domain.model.cart

import com.itechpro.domain.model.Column1
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
@JsonClass(generateAdapter = true)
data class CheckProductActive(
    @Json(name = "Table") var table: List<Column1>,

    )
