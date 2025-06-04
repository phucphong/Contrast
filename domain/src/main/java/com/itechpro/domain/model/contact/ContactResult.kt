package com.itechpro.domain.model.contact

import com.itechpro.domain.model.opportunity.Opportunity


import com.itechpro.domain.model.category.Category
import com.itechpro.domain.model.product.ProductOpoortutityProject
import com.squareup.moshi.JsonClass
@JsonClass(generateAdapter = true)
data class ContactResult(
    val categories: List<Category> = arrayListOf(),
    val items: List<Contact> = arrayListOf(),

    val oderInfo: Contact?=null,
    val totalCount: Int?=0,
    val totalMoney: Double?=0.0,



    )

