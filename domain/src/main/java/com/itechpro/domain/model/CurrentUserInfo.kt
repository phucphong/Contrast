package com.itechpro.domain.model
import com.squareup.moshi.JsonClass
@JsonClass(generateAdapter = true)
data class CurrentUserInfo(
    val employeeId: String,

    val employeeName: String,
    val fullName: String,
    val email: String,
    val phone: String,

    val idCompany: String,
    val idCompanyParent: String,
    val appType: String,
    val salesPointId: String,
    val salesPointName: String,
    val category: String,
    val typeAccount: String,
    val pointAffiliate: String,

    val token: String,
    val permissionMobile: String,
    val domain: String,
    val domainCustomer: String,
    val device: String,
    val isOfflineMode: Boolean,

    val displayService: String,
    val displayProduct: String,
    val displayPriority: String,

    val discountAgency: String,
    val paySalary: String,

    val showPhoneKH: String,
    val showEmailKH: String,
    val showAddressKH: String,

    val admin: String,
    val adminRoot: String,
    val moduleOff: String
)
