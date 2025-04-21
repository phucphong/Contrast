package com.itechpro.domain.model

import com.itechpro.domain.model.product.ProductSection


data class HomeAffiliateUiState(
    val slides: List<SliderHome> = emptyList(),
    val categorys: List<Category> = emptyList(),
    val flashSales: List<Product> = emptyList(),
    val tabs: List<Category> = emptyList(),
    val products: List<Product> = emptyList(),
    val promoUiDataMap: Map<String, PromoUiData> = emptyMap(),
    val domain: String = "",
    val displayProduct: String = "",
    val displayService: String = "",
    val displayPriority: String = "",
    val selectedTab: Int = 0,
    val isLoading: Boolean = false,
    val sections: List<ProductSection> = emptyList(),
    val type: String = ""
)
