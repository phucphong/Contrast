package com.itechpro.domain.model.cart

import com.itechpro.domain.model.navigationEvent.CartNavEvent
import com.itechpro.domain.model.navigationEvent.NavEvent
import com.itechpro.domain.model.navigationEvent.ProductNavEvent

data class CartUiState(
    val carts: List<CartItem> = emptyList(),
    val totalCartItems: Int = 0,
    val totalValue: Double = 0.0,
    val totalIntoMoney: Double = 0.0,
    val amountMoneyDiscount: Double = 0.0,
    val discount: Double = 0.0,
    val domain: String = "",
    val device: String = "",
    val isLoading: Boolean = false,
    val validationError: String = "",
    val statusMessage: String = "",
    val typeAccount: String = "",
    val employeeId: String = "",
    val customerId: String = "",
    val isAllSelected: Boolean = true,
    val navEvent: NavEvent = CartNavEvent.None,
)
