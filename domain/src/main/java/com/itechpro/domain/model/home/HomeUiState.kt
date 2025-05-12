package com.itechpro.domain.model.home

import com.itechpro.domain.model.category.Category
import com.itechpro.domain.model.product.Product
import com.itechpro.domain.model.Rotation
import com.itechpro.domain.model.navigationEvent.NavEvent
import com.itechpro.domain.model.navigationEvent.ProductNavEvent
import com.squareup.moshi.JsonClass
@JsonClass(generateAdapter = true)
data class HomeUiState(
    val slides: List<SliderHome> = emptyList(),

    val categorys: List<Category> = emptyList(),
    val flashSales: List<Product> = emptyList(),
    val tabs: List<Category> = emptyList(),
    val products: List<Product> = emptyList(),
    val pagedProducts: List<Product> = emptyList(),
    val rotations: List<Rotation> = emptyList(),
    val domain: String = "",
    val pointAffiliate: String = "",
    val token: String = "",
    val displayProduct: String = "",
    val displayService: String = "",
    val displayPriority: String = "",
    val validationError: String = "",
    val error: String? = null,
    val type: String = "",
    val selectedTab: Int = 0,
    val isLoading: Boolean = false,

    val navEvent: NavEvent = ProductNavEvent.None,
)
