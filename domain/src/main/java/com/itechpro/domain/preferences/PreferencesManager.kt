package com.itechpro.domain.preferences

interface PreferencesManager {

    // 👤 User Info
    fun getEmployeeId(): String
    fun getEmployeeName(): String
    fun getFullName(): String
    fun getEmail(): String
    fun getPhone(): String

    // 🏢 Company Info
    fun getIdCompany(): String
    fun getIdCompanyParent(): String
    fun getAppType(): String
    fun getSalesPointId(): String
    fun getSalesPointName(): String
    fun getCategory(): String
    fun getTypeAccount(): String

    // 🌐 Domain & Auth
    fun getToken(): String
    fun getPermissionMobile(): String
    fun getDomain(): String
    fun getDomainCustomer(): String
    fun getDevice(): String
    fun isOfflineMode(): Boolean

    // ⚙️ UI Display Config
    fun getDisplayService(): String
    fun getDisplayProduct(): String
    fun getDisplayPriority(): String

    // 💰 Finance
    fun getDiscountAgency(): String
    fun getPaySalary(): String

    // 📞 Customer Display Options
    fun getShowPhoneKH(): Boolean
    fun getShowEmailKH(): Boolean
    fun getShowAddressKH(): Boolean

    // 🛡️ Admin Info
    fun getAdmin(): String
    fun getAdminRoot(): String
}
