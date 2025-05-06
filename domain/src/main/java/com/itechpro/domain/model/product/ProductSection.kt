package com.itechpro.domain.model.product

import com.itechpro.domain.enumApp.ProductSectionType
import com.itechpro.domain.model.Category
import com.itechpro.domain.model.SliderHome
import com.squareup.moshi.JsonClass
@JsonClass(generateAdapter = true)
data class ProductSection(
    val domain: String = "",
    val id: String = "",
    val type: ProductSectionType,
    val title: String = "",
    val slides: List<SliderHome> = emptyList(),
    val categories: List<Category> = emptyList(),
    val products: List<Product> = emptyList(),
    val headerTab: Boolean = false
)