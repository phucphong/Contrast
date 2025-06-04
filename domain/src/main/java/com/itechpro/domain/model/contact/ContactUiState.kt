package com.itechpro.domain.model.contact


import com.itechpro.domain.model.navigationEvent.ContactNavEvent
import com.itechpro.domain.model.navigationEvent.NavEvent
import com.squareup.moshi.JsonClass
@JsonClass(generateAdapter = true)
data class ContactUiState(



    val contactList: List<Contact> = emptyList(),
    val pageContact: List<Contact> = emptyList(),
    val contact: Contact? = null,
    val domain: String = "",
    val token: String = "",
    val customerId: String = "",
    val employeeId: String = "",
    val device: String = "",
    val address: String = "",
    val personCreate: String = "",

    val categoryCode: String = "all",
    val errorMessage: String = "",
    val type: String = "",
    val permissionMobile: String = "",
    val selectedTab: Int = 0,
    val isLoading: Boolean = false,
    val admin: Boolean = false,
    val adminRoot: Boolean = false,
    val showPhoneKH: Boolean = false,
    val showEmailKH: Boolean = false,
    val showAddressKH: Boolean = false,

    val navEvent: NavEvent = ContactNavEvent.None,
)
