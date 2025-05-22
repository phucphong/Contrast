package com.itechpro.domain.model.opportunity



import com.itechpro.domain.model.category.Category
import com.itechpro.domain.model.navigationEvent.NavEvent
import com.itechpro.domain.model.navigationEvent.ProductNavEvent
import com.squareup.moshi.JsonClass
@JsonClass(generateAdapter = true)
data class OpportunityUiState(


    val tabs: List<Category> = emptyList(),
    val orders: List<Order> = emptyList(),
    val pagedOrders: List<Order> = emptyList(),
    val order: Order? = null,
    val domain: String = "",
    val pointAffiliate: String = "",
    val token: String = "",
    val customerId: String = "",
    val employeeId: String = "",
    val device: String = "",
    val address: String = "",
    val personCreate: String = "",
    val categoryCode: String = "",
    val error: String? = null,
    val type: String = "",
    val order_note: String = "",
    val selectedTab: Int = 0,
    val total_price: Double = 0.0,
    val discount: Double = 0.0,
    val final_price: Double = 0.0,// tổng tiền

    val isLoading: Boolean = false,
    val navEvent: NavEvent = ProductNavEvent.None,
)
