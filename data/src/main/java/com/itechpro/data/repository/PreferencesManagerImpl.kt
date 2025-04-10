package com.itechpro.data.repository

import android.content.Context
import com.itechpro.data.config.AppConfig
import com.itechpro.domain.preferences.PreferencesManager
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject


class PreferencesManagerImpl @Inject constructor(
    private val appConfig: AppConfig
) : PreferencesManager {

    override fun getEmployeeId(): String = appConfig.getIdEmployee()
    override fun getEmployeeName(): String = appConfig.getEmployeeName()
    override fun getToken(): String = appConfig.getToken()
    override fun getIdCompany(): String = appConfig.getIdCompany()
    override fun getIdCompanyParent(): String = appConfig.getIdCompanyParent()
    override fun getAppType(): String = appConfig.getAppType()
    override fun getEmail(): String = appConfig.getEmail()
    override fun getPhone(): String = appConfig.getPhone()
    override fun getSalesPointId(): String = appConfig.getSalesPointId()
    override fun getSalesPointName(): String = appConfig.getSalesPointName()
    override fun getFullName(): String = appConfig.getFullName()
    override fun getPermissionMobile(): String = appConfig.getPermissionMobile()
    override fun getDomain(): String = appConfig.getDomain()
    override fun getDomainCustomer(): String =appConfig.getDomainCustomer()
    override fun getDevice(): String = appConfig.getDevice()
    override fun getDiscountAgency(): String = appConfig.getDiscountAgency()
    override fun getPaySalary(): String = appConfig.getPaySalary()
    override fun getDisplayService(): String = appConfig.getDisplayService()
    override fun getDisplayProduct(): String = appConfig.getDisplayProduct()
    override fun getDisplayPriority(): String = appConfig.getDisplayPriority()
    override fun getCategory(): String = appConfig.getCategory()
    override fun getTypeAccount(): String = appConfig.getTypeAccount()
    override fun getShowPhoneKH(): Boolean = appConfig.getShowPhoneKH()
    override fun getShowEmailKH(): Boolean = appConfig.getShowEmailKH()
    override fun getShowAddressKH(): Boolean = appConfig.getShowAddressKH()
    override fun isOfflineMode(): Boolean = appConfig.isOfflineMode()
    override fun getAdmin(): String = appConfig.getAdmin()
    override fun getAdminRoot(): String = appConfig.getAdminRoot()
}
