package com.itechpro.domain.model.payment

import com.itechpro.domain.model.Category
import com.itechpro.domain.model.cart.CartItem


data class PaymentUiState(
    val qrCodes: List<InfoPayment> = emptyList(),
    val tabs: List<Category> = emptyList(),
    val domain: String = "",
    val device: String = "",

    val validationError: String = "",
    val statusMessage: String = "",
    val typeAccount: String = "",
    val employeeId: String = "",
    val customerId: String = "",

    val isAllSelected: Boolean = true,
    val isLoading: Boolean = false,
)
