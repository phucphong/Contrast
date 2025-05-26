package com.itechpro.domain.model.opportunity



import com.itechpro.domain.model.category.Category
import com.itechpro.domain.model.navigationEvent.NavEvent
import com.itechpro.domain.model.navigationEvent.ProductNavEvent
import com.squareup.moshi.JsonClass
@JsonClass(generateAdapter = true)
data class OpportunityUiState(


    val tabs: List<Category> = emptyList(),
    val categorys: List<Category> = emptyList(),
    val opportunitys: List<Opportunity> = emptyList(),
    val pagedOpportunitys: List<Opportunity> = emptyList(),
    val opportunityProject: Opportunity? = null,
    val domain: String = "",
    val pointAffiliate: String = "",
    val token: String = "",
    val customerId: String = "",
    val employeeId: String = "",
    val device: String = "",
    val address: String = "",
    val personCreate: String = "",
    val categoryCode: String = "all",
    val errorMessage: String? = null,
    val type: String = "",
    val selectedTab: Int = 0,
    val isLoading: Boolean = false,
    val navEvent: NavEvent = ProductNavEvent.None,
)
