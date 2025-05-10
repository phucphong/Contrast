package com.itechpro.domain.model.product


import com.itechpro.domain.model.Category
import com.itechpro.domain.model.cart.CartItem



data class ProductDetailUiState(




    val domain: String = "",
    val token: String = "",
    val typeAccount: String? = null,
    val device: String? = null,
    val displayProduct: String? = null,
    val displayService: String? = null,
    val displayPriority: String? = null,
    val pointAffiliate: String? = null,
    val customerId: String? = null,
    val employeeId: String ?= null,
    val validationError: String ?= null,
    val discount: Double = 0.0,
    val isOfflineMode: Boolean = false,

    val productInfo: ProductDetail? = null,
    val products: List<Product> = emptyList(),
    val productsCategory: List<Product> = emptyList(),
    val qACoachings: List<Product> = emptyList(),

    val oders: List<Product> = emptyList(),

    )
