package com.itechpro.domain.model.profile

import com.itechpro.domain.model.category.Category


import com.squareup.moshi.JsonClass
@JsonClass(generateAdapter = true)
data class ProfileResult(
    val categorys: List<Category> = emptyList(),
    val coachings: List<Category> = emptyList(),
    val qACoachings: List<Category> = emptyList(),

    val oders: List<Category> = emptyList(),

    )