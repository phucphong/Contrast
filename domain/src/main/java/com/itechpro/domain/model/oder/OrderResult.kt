package com.itechpro.domain.model.oder


import com.squareup.moshi.JsonClass
@JsonClass(generateAdapter = true)
data class OrderResult(
    val items: List<Order>,
    val oderInfo: Order?=null,
    val totalCount: Int?=0,



)

