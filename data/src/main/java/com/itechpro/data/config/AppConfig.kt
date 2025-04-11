package com.itechpro.data.config


import android.content.Context
import android.content.SharedPreferences
import java.util.Locale

class AppConfig(context: Context) {

    private val prefs: SharedPreferences =
        context.getSharedPreferences(SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE)

    fun getIdEmployee(): String = prefs.getString(KEY_ID_EMPLOYEE, "1") ?: "1"
    fun getEmployeeName(): String = prefs.getString(KEY_EMPLOYEE_NAME, "ADMIN") ?: "ADMIN"
    fun getToken(): String = prefs.getString(KEY_TOKEN, "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1bmlxdWVfbmFtZSI6ImFkbWluQGl0ZWNocHJvLnZuIiwidXNlcm5hbWUiOiJhZG1pbkBpdGVjaHByby52biIsImlkIjoiMSIsImlzYWRtaW4iOiJUcnVlIiwiYWN0aXZlIjoiVHJ1ZSIsImlkY29uZ3R5IjoiMSIsImlzYWRtaW5jb3NvIjoiVHJ1ZSIsImlkZGllbWJhbmxlIjoiMyIsImlka2giOiIwIiwibG9haWtoIjoiIiwiaWRsaCI6IjAiLCJsb2FpdGsiOiJuaGFudmllbiIsImhvdGVuIjoiIEFkbWluIiwiZGllbnRob2FpIjoiIiwiZW1haWwiOiJhZG1pbkBpdGVjaHByby52biIsInRlbmtoYWNoaGFuZyI6IklURUNIUFJPIiwibWFraGFjaGhhbmciOiJpdHAiLCJpZGNoYXQiOiJpdHBfMSIsInNlcnZlcmNoYXQiOiJodHRwOi8vMTkyLjE2OC4xLjE0OjkxMSIsIm5iZiI6MTc0NDMzNDgwMSwiZXhwIjoxNzUyOTc0ODAxLCJpYXQiOjE3NDQzMzQ4MDEsImlzcyI6Imh0dHBzczovL3NwYS5lem1heC52biIsImF1ZCI6Imh0dHBzczovL3NwYS5lem1heC52biJ9.w2Pv_NVyk51GvGI1YJOSSjJQrcmz3bsvRsARfSStQlo") ?: ""
    fun getIdCompany(): String = prefs.getString(KEY_ID_COMPANY, "") ?: ""
    fun getIdCompanyParent(): String = prefs.getString(KEY_ID_COMPANY_PARENT, "") ?: ""
    fun getAppType(): String = prefs.getString(KEY_APP_TYPE, "") ?: ""
    fun getEmail(): String = prefs.getString(KEY_EMAIL, "") ?: ""
    fun getPhone(): String = prefs.getString(KEY_PHONE, "") ?: ""
    fun getSalesPointId(): String = prefs.getString(KEY_ID_SALE_POINT, "") ?: ""
    fun getSalesPointName(): String = prefs.getString(KEY_SALE_POINT_NAME, "") ?: ""
    fun getFullName(): String = prefs.getString(KEY_ID_FULL_NAME, "") ?: ""
    fun getPermissionMobile(): String = prefs.getString(KEY_PERMISSION, "") ?: ""
    fun getDomain(): String = prefs.getString(KEY_DOMAIN, "https://spa.ezmax.vn") ?: ""
    fun getDomainCustomer(): String = prefs.getString(KEY_DOMAIN_CUSTOMER, "dcheery") ?: ""
    fun getDevice(): String = prefs.getString(KEY_DEVICE, "") ?: ""
    fun getDiscountAgency(): String = prefs.getString(KEY_DISCOUNT_AGENCY, "") ?: ""
    fun getPaySalary(): String = prefs.getString(KEY_PAY_SALARY, "") ?: ""
    fun getDisplayService(): String = prefs.getString(KEY_DISPLAY_SERVICE, "") ?: ""
    fun getDisplayProduct(): String = prefs.getString(KEY_DISPLAY_PRODUCT, "") ?: ""
    fun getDisplayPriority(): String = prefs.getString(KEY_DISPLAY_PRIORITY, "") ?: ""
    fun getCategory(): String = prefs.getString(KEY_CATEGORY, "") ?: ""
    fun getTypeAccount(): String = prefs.getString(KEY_TYPE_ACCOUNT, "") ?: ""
    fun getShowPhoneKH(): String = prefs.getString(KEY_SHOW_PHONE, "") ?: ""
    fun getShowEmailKH(): String = prefs.getString(KEY_SHOW_EMAIL, "") ?: ""
    fun getShowAddressKH(): String = prefs.getString(KEY_SHOW_ADDRESS, "") ?: ""
    fun isOfflineMode(): Boolean = prefs.getBoolean(KEY_LOGIN, false)
    fun getAdmin(): String = prefs.getString(KEY_ADMIN, "") ?: ""
    fun getAdminRoot(): String = prefs.getString(KEY_ADMIN_ROOT, "") ?: ""


    fun setIdEmployee(value: String) = prefs.edit().putString(KEY_ID_EMPLOYEE, value).apply()
    fun setEmployeeName(value: String) = prefs.edit().putString(KEY_EMPLOYEE_NAME, value).apply()
    fun setToken(value: String) = prefs.edit().putString(KEY_TOKEN, value).apply()
    fun setIdCompany(value: String) = prefs.edit().putString(KEY_ID_COMPANY, value).apply()
    fun setIdCompanyParent(value: String) = prefs.edit().putString(KEY_ID_COMPANY_PARENT, value).apply()
    fun setAppType(value: String) = prefs.edit().putString(KEY_APP_TYPE, value).apply()
    fun setEmail(value: String) = prefs.edit().putString(KEY_EMAIL, value).apply()
    fun setPhone(value: String) = prefs.edit().putString(KEY_PHONE, value).apply()
    fun setSalesPointId(value: String) = prefs.edit().putString(KEY_ID_SALE_POINT, value).apply()
    fun setSalesPointName(value: String) = prefs.edit().putString(KEY_SALE_POINT_NAME, value).apply()
    fun setFullName(value: String) = prefs.edit().putString(KEY_ID_FULL_NAME, value).apply()
    fun setPermissionMobile(value: String) = prefs.edit().putString(KEY_PERMISSION, value).apply()
    fun setDomain(value: String) = prefs.edit().putString(KEY_DOMAIN, value).apply()
    fun setDomainCustomer(value: String) = prefs.edit().putString(KEY_DOMAIN_CUSTOMER, value).apply()
    fun setDevice(value: String) = prefs.edit().putString(KEY_DEVICE, value).apply()
    fun setDiscountAgency(value: String) = prefs.edit().putString(KEY_DISCOUNT_AGENCY, value).apply()
    fun setPaySalary(value: String) = prefs.edit().putString(KEY_PAY_SALARY, value).apply()
    fun setDisplayService(value: String) = prefs.edit().putString(KEY_DISPLAY_SERVICE, value).apply()
    fun setDisplayProduct(value: String) = prefs.edit().putString(KEY_DISPLAY_PRODUCT, value).apply()
    fun setDisplayPriority(value: String) = prefs.edit().putString(KEY_DISPLAY_PRIORITY, value).apply()
    fun setCategory(value: String) = prefs.edit().putString(KEY_CATEGORY, value).apply()
    fun setTypeAccount(value: String) = prefs.edit().putString(KEY_TYPE_ACCOUNT, value).apply()
    fun setShowPhoneKH(value: String) = prefs.edit().putString(KEY_SHOW_PHONE, value).apply()
    fun setShowEmailKH(value: String) = prefs.edit().putString(KEY_SHOW_EMAIL, value).apply()
    fun setShowAddressKH(value: String) = prefs.edit().putString(KEY_SHOW_ADDRESS, value).apply()
    fun setOfflineMode(value: Boolean) = prefs.edit().putBoolean(KEY_LOGIN, value).apply()
    fun setAdmin(value: Boolean) = prefs.edit().putBoolean(KEY_ADMIN, value).apply()
    fun setAdminRoot(value: Boolean) = prefs.edit().putBoolean(KEY_ADMIN_ROOT, value).apply()


    companion object {
        private const val SHARED_PREFERENCES_NAME = "Phucphong"
        private const val KEY_ID_EMPLOYEE = "idEmployee"
        private const val KEY_EMPLOYEE_NAME = "employeeName"
        private const val KEY_TOKEN = "token"
        private const val KEY_ID_COMPANY = "IdCompany"
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
    }
}
