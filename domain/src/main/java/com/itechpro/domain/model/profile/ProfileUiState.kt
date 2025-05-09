package com.itechpro.domain.model.profile

import com.itechpro.domain.model.Category
import com.itechpro.domain.model.cart.CartItem



data class ProfileUiState(



    val qrCode: String = "",
    val fullName: String = "",
    val avartar: String = "",
    val phone: String = "",
    val agencyName: String = "",

    val address: String = "",
    val domain: String = "",
    val device: String = "",
    val isLoading: Boolean = false,
    val isLogin: Boolean = false,
    val validationError: String = "",
    val statusMessage: String = "",
    val typeAccount: String = "",
    val employeeId: String = "",
    val customerId: String = "",

    val discount: Double = 0.0,
    val categorys: List<Category> = emptyList(),
    val coachings: List<Category> = emptyList(),
    val qACoachings: List<Category> = emptyList(),

    val oders: List<Category> = emptyList(),

    )
