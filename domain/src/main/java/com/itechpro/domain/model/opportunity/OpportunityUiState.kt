package com.itechpro.domain.model.opportunity_project



import com.itechpro.domain.model.category.Category
import com.itechpro.domain.model.navigationEvent.NavEvent
import com.itechpro.domain.model.navigationEvent.ProductNavEvent
import com.squareup.moshi.JsonClass
@JsonClass(generateAdapter = true)
data class OpportunityProjectUiState(


    val tabs: List<Category> = emptyList(),
    val categorys: List<Category> = emptyList(),
    val opportunityProjects: List<OpportunityProject> = emptyList(),
    val pagedOpportunityProjects: List<OpportunityProject> = emptyList(),
    val opportunityProject: OpportunityProject? = null,
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
