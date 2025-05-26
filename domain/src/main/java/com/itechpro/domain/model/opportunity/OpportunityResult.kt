package com.itechpro.domain.model.opportunity_project

import com.itechpro.domain.model.category.Category
import com.squareup.moshi.JsonClass
@JsonClass(generateAdapter = true)
data class OpportunityProjectResult(
    val categories: List<Category>,
    val items: List<OpportunityProject>,
    val oderInfo: OpportunityProject?=null,
    val totalCount: Int?=0,



    )

