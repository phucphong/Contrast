package com.itechpro.domain.model.home

import com.itechpro.domain.model.Category
import com.itechpro.domain.model.product.Product
import com.itechpro.domain.model.Rotation
import com.squareup.moshi.JsonClass
@JsonClass(generateAdapter = true)
data class HomeUiState(
    val slides: List<SliderHome> = emptyList(),
    val categories: List<Category> = emptyList(),
    val flashSales: List<Product> = emptyList(),
    val tabs: List<Category> = emptyList(),
    val products: List<Product> = emptyList(),
    val rotations: List<Rotation> = emptyList(),
    val domain: String = "",
    val displayProduct: String = "",
    val displayService: String = "",
    val displayPriority: String = "",
    val type: String = "",
    val isLoading: Boolean = false,
    val error: String? = null
)
