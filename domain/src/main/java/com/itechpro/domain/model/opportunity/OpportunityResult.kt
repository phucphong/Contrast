package com.itechpro.domain.model.opportunity

import com.itechpro.domain.model.category.Category
import com.squareup.moshi.JsonClass
@JsonClass(generateAdapter = true)
data class OpportunityResult(
    val categories: List<Category>,
    val items: List<Opportunity>,
    val oderInfo: Opportunity?=null,
    val totalCount: Int?=0,



    )

