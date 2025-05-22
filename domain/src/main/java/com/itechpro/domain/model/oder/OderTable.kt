package com.itechpro.domain.model.oder

import com.itechpro.domain.model.Column1
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class OderTable(
    @Json(name = "Table") var table: List<Order>,
    @Json(name = "Table1") var table1: List<Order>,
)