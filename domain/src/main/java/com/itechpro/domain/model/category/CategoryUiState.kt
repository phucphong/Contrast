package com.itechpro.domain.model.category

import com.itechpro.domain.model.navigationEvent.NavEvent
import com.itechpro.domain.model.navigationEvent.ProductNavEvent
import com.itechpro.domain.model.product.Product


// 🧩 State holder for CategoryAffiliateModel
data class CategoryUiState(
    val domain: String = "",
    val token: String = "",
    val pointAffiliate: String = "",
    val displayProduct: String = "",
    val displayService: String = "",
    val displayPriority: String = "",
    val validationError: String = "",
    val tabs: List<Category> = emptyList(),
    val category1: List<Category> = emptyList(),
    val category2: List<Category> = emptyList(),
    val category3: List<Category> = emptyList(),
    val products: List<Product> = emptyList(),
    val pagedProducts: List<Product> = emptyList(),
    val selectedTab: Int = 0,
    val selectedTab1: Int = 0,
    val selectedTab2: Int = 0,
    val selectedTab3: Int = 0,
    val idParent1: String = "",
    val idParent2: String = "",
    val idParent3: String = "",
    val objApi: String = "",
    val modeApi: String = "",
    val type: String = "",
    val isLoading: Boolean = false,
    val navEvent: NavEvent = ProductNavEvent.None
)