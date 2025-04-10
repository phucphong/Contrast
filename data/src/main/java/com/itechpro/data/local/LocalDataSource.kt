package com.itechpro.data.local


import com.itechpro.domain.model.UserModel
import com.itechpro.domain.preferences.PreferencesManager
import javax.inject.Inject

class LocalDataSource @Inject constructor(
    private val preferencesManager: PreferencesManager
) {
    fun getCurrentUser(): UserModel {
        return UserModel(
            id = preferencesManager.getEmployeeId(),
            name = preferencesManager.getEmployeeName()
        )
    }

    fun getToken(): String = preferencesManager.getToken()

    fun getIdCompany(): String = preferencesManager.getIdCompany()
    fun getIdCompanyParent(): String = preferencesManager.getIdCompanyParent()
    fun getAppType(): String = preferencesManager.getAppType()
    fun getEmail(): String = preferencesManager.getEmail()
    fun getPhone(): String = preferencesManager.getPhone()
    fun getSalesPointId(): String = preferencesManager.getSalesPointId()
    fun getSalesPointName(): String = preferencesManager.getSalesPointName()
    fun getFullName(): String = preferencesManager.getFullName()
    fun getPermissionMobile(): String = preferencesManager.getPermissionMobile()
    fun getDomain(): String = preferencesManager.getDomain()
    fun getDevice(): String = preferencesManager.getDevice()
    fun getDiscountAgency(): String = preferencesManager.getDiscountAgency()
    fun getPaySalary(): String = preferencesManager.getPaySalary()
    fun getDisplayService(): String = preferencesManager.getDisplayService()
    fun getDisplayProduct(): String = preferencesManager.getDisplayProduct()
    fun getDisplayPriority(): String = preferencesManager.getDisplayPriority()
    fun getCategory(): String = preferencesManager.getCategory()
    fun getTypeAccount(): String = preferencesManager.getTypeAccount()
    fun getShowPhoneKH(): Boolean = preferencesManager.getShowPhoneKH()
    fun getShowEmailKH(): Boolean = preferencesManager.getShowEmailKH()
    fun getShowAddressKH(): Boolean = preferencesManager.getShowAddressKH()
    fun isOfflineMode(): Boolean = preferencesManager.isOfflineMode()
    fun getAdmin(): String = preferencesManager.getAdmin()
    fun getAdminRoot(): String = preferencesManager.getAdminRoot()
}