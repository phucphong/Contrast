package com.itechpro.domain.model.opportunity



import com.squareup.moshi.JsonClass
@JsonClass(generateAdapter = true)
data class OpportunityResult(
    val items: List<Order>,
    val oderInfo: Order?=null,
    val totalCount: Int?=0,



    )

