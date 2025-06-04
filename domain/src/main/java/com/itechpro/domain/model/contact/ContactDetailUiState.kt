package com.itechpro.domain.model.contact







import com.itechpro.domain.model.category.Category
import com.itechpro.domain.model.contact.Contact
import com.itechpro.domain.model.navigationEvent.NavEvent
import com.itechpro.domain.model.navigationEvent.ProductNavEvent
import com.itechpro.domain.model.product.AttachFile
import com.itechpro.domain.model.product.InfoDetail
import com.itechpro.domain.model.product.ProductOpoortutityProject
import com.squareup.moshi.JsonClass
@JsonClass(generateAdapter = true)
data class ContactDetailUiState(


    val tabs: List<Category> = emptyList(),
    val contactList: List<Contact> = emptyList(),

    val emailList: List<Contact> = emptyList(),
    val callList: List<Contact> = emptyList(),
    val smsList: List<Contact> = emptyList(),
    val objInfoList: List<InfoDetail> = emptyList(),
    val pagedContactList: List<Contact> = emptyList(),
    val contact: Contact? = null,
    val domain: String = "",
    val contactName: String = "",
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
    val totalCount : Int = 0,
    val selectedTab: Int = 0,
    val selectedTabIndex: Int = 0,

    val isLoading: Boolean = false,
    val isShowAddButton: Boolean = false,
    val showPhoneKH: Boolean = false,
    val showEmailKH: Boolean = false,
    val showAddressKH: Boolean = false,
    val admin: Boolean = false,
    val adminRoot: Boolean = false,
    val navEvent: NavEvent = ProductNavEvent.None,
)
