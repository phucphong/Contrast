package com.itechpro.data.config


import android.content.Context
import android.content.SharedPreferences
import java.util.Locale
import javax.inject.Inject

class AppConfig @Inject constructor(
    private val securePrefs: SecurePrefsHelper) {

    fun getEmployeeId(): String = securePrefs.getString(KEY_EMPLOYEE_ID, "0") 
    fun getCustomerId(): String = securePrefs.getString(KEY_ID_CUSTOMER, "0") 
    fun getIntroducerId(): String = securePrefs.getString(KEY_INTRODUCER_ID, "0") 
    fun getEmployeeName(): String = securePrefs.getString(KEY_EMPLOYEE_NAME, "") 
    fun getAccount(): String = securePrefs.getString(KEY_ACCOUNT, "admin@itechpro.vn")
    fun getPassword(): String = securePrefs.getString(KEY_PASSWORD, "123456")
    fun getDomain(): String = securePrefs.getString(KEY_DOMAIN, "")
    fun getToken(): String = securePrefs.getString(KEY_TOKEN,"")
    fun getRememberPassword(): Boolean = securePrefs.getBoolean(KEY_REMEMBER_PASSWORD,false)
    fun getIdCompany(): String = securePrefs.getString(KEY_ID_COMPANY, "") 
    fun getPointAffiliate(): String = securePrefs.getString(KEY_POINT_AFFILIATE, "0") 
    fun getIdCompanyParent(): String = securePrefs.getString(KEY_ID_COMPANY_PARENT, "") 
    fun getAppType(): String = securePrefs.getString(KEY_APP_TYPE, "") 
    fun getEmail(): String = securePrefs.getString(KEY_EMAIL, "") 
    fun getPhone(): String = securePrefs.getString(KEY_PHONE, "") 
    fun getSalesPointId(): String = securePrefs.getString(KEY_ID_SALE_POINT, "3") 
    fun getSalesPointName(): String = securePrefs.getString(KEY_SALE_POINT_NAME, "") 
    fun getFullName(): String = securePrefs.getString(KEY_ID_FULL_NAME, "") 
    fun getPermissionMobile(): String = securePrefs.getString(KEY_PERMISSION, "") 

    fun getDomainCustomer(): String = securePrefs.getString(KEY_DOMAIN_CUSTOMER, "dcheery") 
    fun getDevice(): String = securePrefs.getString(KEY_DEVICE, "") 
    fun getDiscountAgency(): String = securePrefs.getString(KEY_DISCOUNT_AGENCY, "") 
    fun getPaySalary(): String = securePrefs.getString(KEY_PAY_SALARY, "") 
    fun getDisplayService(): String = securePrefs.getString(KEY_DISPLAY_SERVICE, "1") 
    fun getDisplayProduct(): String = securePrefs.getString(KEY_DISPLAY_PRODUCT, "1") 
    fun getDisplayPriority(): String = securePrefs.getString(KEY_DISPLAY_PRIORITY, "1") 
    fun getCategory(): String = securePrefs.getString(KEY_CATEGORY, "") 
    fun getTypeAccount(): String = securePrefs.getString(KEY_TYPE_ACCOUNT, "khachhang") 
    fun getShowPhoneKH(): String = securePrefs.getString(KEY_SHOW_PHONE, "") 
    fun getShowEmailKH(): String = securePrefs.getString(KEY_SHOW_EMAIL, "") 
    fun getShowAddressKH(): String = securePrefs.getString(KEY_SHOW_ADDRESS, "") 
    fun getModuleOff(): String = securePrefs.getString(KEY_SHOW_MODULE_OFF, "") 

    fun getAdmin(): Boolean = securePrefs.getBoolean(KEY_ADMIN, false)
    fun getAdminRoot(): Boolean = securePrefs.getBoolean(KEY_ADMIN_ROOT, false)


    fun setIntroducerId(value: String) = securePrefs.putString(KEY_INTRODUCER_ID, value)
    fun setEmployeeId(value: String) = securePrefs.putString(KEY_EMPLOYEE_ID, value)
    fun setCustomerId(value: String) = securePrefs.putString(KEY_ID_CUSTOMER, value)
    fun setPointAffiliate(value: String) = securePrefs.putString(KEY_POINT_AFFILIATE, value)
    fun setEmployeeName(value: String) = securePrefs.putString(KEY_EMPLOYEE_NAME, value)
    fun setAccount(value: String) = securePrefs.putString(KEY_ACCOUNT, value)
    fun setPassword(value: String) = securePrefs.putString(KEY_PASSWORD, value)
    fun setToken(value: String) = securePrefs.putString(KEY_TOKEN, value)
    fun setRememberPassword(value: Boolean) = securePrefs.putBoolean(KEY_REMEMBER_PASSWORD, value)
    fun setIdCompany(value: String) = securePrefs.putString(KEY_ID_COMPANY, value)
    fun setIdCompanyParent(value: String) = securePrefs.putString(KEY_ID_COMPANY_PARENT, value)
    fun setAppType(value: String) = securePrefs.putString(KEY_APP_TYPE, value)
    fun setEmail(value: String) = securePrefs.putString(KEY_EMAIL, value)
    fun setPhone(value: String) = securePrefs.putString(KEY_PHONE, value)
    fun setSalesPointId(value: String) = securePrefs.putString(KEY_ID_SALE_POINT, value)
    fun setSalesPointName(value: String) = securePrefs.putString(KEY_SALE_POINT_NAME, value)
    fun setFullName(value: String) = securePrefs.putString(KEY_ID_FULL_NAME, value)
    fun setPermissionMobile(value: String) = securePrefs.putString(KEY_PERMISSION, value)
    fun setDomain(value: String) = securePrefs.putString(KEY_DOMAIN, value)
    fun setDomainCustomer(value: String) = securePrefs.putString(KEY_DOMAIN_CUSTOMER, value)
    fun setDevice(value: String) = securePrefs.putString(KEY_DEVICE, value)
    fun setDiscountAgency(value: String) = securePrefs.putString(KEY_DISCOUNT_AGENCY, value)
    fun setPaySalary(value: String) = securePrefs.putString(KEY_PAY_SALARY, value)
    fun setDisplayService(value: String) = securePrefs.putString(KEY_DISPLAY_SERVICE, value)
    fun setDisplayProduct(value: String) = securePrefs.putString(KEY_DISPLAY_PRODUCT, value)
    fun setDisplayPriority(value: String) = securePrefs.putString(KEY_DISPLAY_PRIORITY, value)
    fun setCategory(value: String) = securePrefs.putString(KEY_CATEGORY, value)
    fun setTypeAccount(value: String) = securePrefs.putString(KEY_TYPE_ACCOUNT, value)
    fun setShowPhoneKH(value: String) = securePrefs.putString(KEY_SHOW_PHONE, value)
    fun setShowEmailKH(value: String) = securePrefs.putString(KEY_SHOW_EMAIL, value)
    fun setShowAddressKH(value: String) = securePrefs.putString(KEY_SHOW_ADDRESS, value)
    fun setModuleOff(value: String) = securePrefs.putString(KEY_SHOW_MODULE_OFF, value)

    fun setAdmin(value: Boolean) = securePrefs.putBoolean(KEY_ADMIN, value)
    fun setAdminRoot(value: Boolean) = securePrefs.putBoolean(KEY_ADMIN_ROOT, value)


    companion object {
        private const val SHARED_PREFERENCES_NAME = "Phucphong"
        private const val KEY_EMPLOYEE_ID = "EmployeeId"
        private const val KEY_ID_CUSTOMER = "customerId"
        private const val KEY_INTRODUCER_ID = "introducerId"
        private const val KEY_EMPLOYEE_NAME = "employeeName"
        private const val KEY_ACCOUNT = "KEY_ACCOUNT"
        private const val KEY_PASSWORD = "KEY_PASSWORD"

        private const val KEY_TOKEN = "token"
        private const val KEY_REMEMBER_PASSWORD = "KEY_REMEMBER_PASSWORD"
        private const val KEY_ID_COMPANY = "IdCompany"
        private const val KEY_POINT_AFFILIATE = "pointAffiliate"
        private const val KEY_ID_COMPANY_PARENT = "idCompanyParent"
        private const val KEY_APP_TYPE = "appType"
        private const val KEY_EMAIL = "email"
        private const val KEY_PHONE = "phone"
        private const val KEY_ID_SALE_POINT = "idSalesPoint"
        private const val KEY_SALE_POINT_NAME = "salesPointName"
        private const val KEY_ID_FULL_NAME = "FullName"
        private const val KEY_PERMISSION = "PermissionMobile"
        private const val KEY_DOMAIN = "domain"
        private const val KEY_DOMAIN_CUSTOMER = "KEY_DOMAIN_CUSTOMER"
        private const val KEY_DEVICE = "device"
        private const val KEY_DISCOUNT_AGENCY = "discountAgency"
        private const val KEY_PAY_SALARY = "paySalary"
        private const val KEY_DISPLAY_SERVICE = "displayService"
        private const val KEY_DISPLAY_PRODUCT = "DisplayProduct"
        private const val KEY_DISPLAY_PRIORITY = "displayPriority"
        private const val KEY_CATEGORY = "category"
        private const val KEY_TYPE_ACCOUNT = "typeAccount"
        private const val KEY_SHOW_PHONE = "showPhoneKH"
        private const val KEY_SHOW_EMAIL = "showEmailKH"
        private const val KEY_SHOW_ADDRESS = "showAddressKH"
        private const val KEY_LOGIN = "login"
        private const val KEY_ADMIN = "admin"
        private const val KEY_ADMIN_ROOT = "adminRoot"
        private const val KEY_SHOW_MODULE_OFF = "KEY_SHOW_MODULE_OFF"
    }
}
