package com.itechpro.domain.model.opportunity





import com.itechpro.domain.model.category.Category
import com.itechpro.domain.model.contact.Contact
import com.itechpro.domain.model.navigationEvent.NavEvent
import com.itechpro.domain.model.navigationEvent.ProductNavEvent
import com.itechpro.domain.model.product.AttachFile
import com.itechpro.domain.model.product.InfoDetail
import com.itechpro.domain.model.product.ProductOpoortutityProject
import com.squareup.moshi.JsonClass
@JsonClass(generateAdapter = true)
data class OpportunityDetailUiState(


    val tabs: List<Category> = emptyList(),
    val contactsList: List<Contact> = emptyList(),
    val productList: List<ProductOpoortutityProject> = emptyList(),
    val attachList: List<AttachFile> = emptyList(),
    val objInfoList: List<InfoDetail> = emptyList(),
    val pagedOpportunitys: List<Opportunity> = emptyList(),
    val opportunity: Opportunity? = null,
    val domain: String = "",
    val opportunityName: String = "",
    val pointAffiliate: String = "",
    val token: String = "",
    val customerId: String = "",
    val employeeId: String = "",
    val device: String = "",
    val address: String = "",
    val personCreate: String = "",
    val categoryCode: String = "all",
    val errorMessage: String= "",
    val searchText: String= "",
    val type: String = "",
    val permissionMobile: String = "",
    val totalMoney : Double = 0.0,
    var quantity : Double = 0.0,
    var unitPrice : Double = 0.0,
    var vat : Double = 0.0,
    var discount : Double = 0.0,


    val selectedTab: Int = 0,
    val selectedTabIndex: Int = 0,

    val isLoading: Boolean = false,
    val isShowAddButton: Boolean = false,
    val showPhoneKH: Boolean = false,
    val showEmailKH: Boolean = false,
    val showAddressKH: Boolean = false,
    val admin: Boolean = false,
    val adminRoot: Boolean = false,
    var isPercent: Boolean = false,
    val navEvent: NavEvent = ProductNavEvent.None,
)
