package com.itechpro.domain.preferences

interface PreferencesManager {
    fun getEmployeeId(): String
    fun getEmployeeName(): String
    fun getToken(): String
    fun getIdCompany(): String
    fun getIdCompanyParent(): String
    fun getAppType(): String
    fun getEmail(): String
    fun getPhone(): String
    fun getSalesPointId(): String
    fun getSalesPointName(): String
    fun getFullName(): String
    fun getPermissionMobile(): String
    fun getDomain(): String
    fun getDevice(): String
    fun getDiscountAgency(): String
    fun getPaySalary(): String
    fun getDisplayService(): String
    fun getDisplayProduct(): String
    fun getDisplayPriority(): String
    fun getCategory(): String
    fun getTypeAccount(): String
    fun getShowPhoneKH(): Boolean
    fun getShowEmailKH(): Boolean
    fun getShowAddressKH(): Boolean
    fun isOfflineMode(): Boolean
    fun getAdmin(): String
    fun getAdminRoot(): String
}