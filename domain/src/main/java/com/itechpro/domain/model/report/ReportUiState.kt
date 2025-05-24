package com.itechpro.domain.model.report

import com.itechpro.domain.model.category.Category
import com.itechpro.domain.model.navigationEvent.NavEvent
import com.itechpro.domain.model.navigationEvent.ProductNavEvent
import com.squareup.moshi.JsonClass
@JsonClass(generateAdapter = true)
data class ReportUiState(
    val tabs: List<Category> = emptyList(),
    val reports: List<Report> = emptyList(),
    val pagedReports: List<Report> = emptyList(),
    val report: Report? = null,
    val domain: String = "",
    val token: String = "",
    val device: String = "",
    val employeeId: String = "",
    val customerId: String = "",
    val error: String? = null,

    val selectedTab: Int = 0,
    val isLoading: Boolean = false,
    val navEvent: NavEvent = ProductNavEvent.None,
)
