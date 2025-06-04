package com.itechpro.domain.model.opportunity

import com.itechpro.domain.model.category.Category
import com.itechpro.domain.model.product.ProductOpoortutityProject
import com.squareup.moshi.JsonClass
@JsonClass(generateAdapter = true)
data class OpportunityResult(
    val categories: List<Category> = arrayListOf(),
    val items: List<Opportunity> = arrayListOf(),
    val products: List<ProductOpoortutityProject> = arrayListOf(),
    val oderInfo: Opportunity?=null,
    val totalCount: Int?=0,
    val totalMoney: Double?=0.0,



    )

