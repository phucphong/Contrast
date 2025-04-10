package com.itechpro.data.config


import android.content.Context
import android.content.SharedPreferences
import java.util.Locale

class AppConfig(context: Context) {

    private val prefs: SharedPreferences =
        context.getSharedPreferences(SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE)

    fun getIdEmployee(): String = prefs.getString(KEY_ID_EMPLOYEE, "1") ?: "1"
    fun getEmployeeName(): String = prefs.getString(KEY_EMPLOYEE_NAME, "ADMIN") ?: "ADMIN"
    fun getToken(): String = prefs.getString(KEY_TOKEN, "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1bmlxdWVfbmFtZSI6ImFkbWluQGl0ZWNocHJvLnZuIiwidXNlcm5hbWUiOiJhZG1pbkBpdGVjaHByby52biIsImlkIjoiMSIsImlzYWRtaW4iOiJUcnVlIiwiYWN0aXZlIjoiVHJ1ZSIsImlkY29uZ3R5IjoiMSIsImlzYWRtaW5jb3NvIjoiVHJ1ZSIsImlkZGllbWJhbmxlIjoiMyIsImlka2giOiIwIiwibG9haWtoIjoiIiwiaWRsaCI6IjAiLCJsb2FpdGsiOiJuaGFudmllbiIsImhvdGVuIjoiQURNSU4iLCJkaWVudGhvYWkiOiIwOTY5NjY5OTY2IiwiZW1haWwiOiJhZG1pbkBpdGVjaHByby52biIsInRlbmtoYWNoaGFuZyI6IklURUNIUFJPIiwibWFraGFjaGhhbmciOiJpdHAiLCJpZGNoYXQiOiJpdHBfMSIsInNlcnZlcmNoYXQiOiIiLCJuYmYiOjE3NDQyNTczODMsImV4cCI6MTc1Mjg5NzM4MywiaWF0IjoxNzQ0MjU3MzgzLCJpc3MiOiJodHRwOi8vMTkyLjE2OC4xLjExOTo5MTAiLCJhdWQiOiJodHRwOi8vMTkyLjE2OC4xLjExOTo5MTAifQ.95pOBdZuyTei2MvFBUU6qZsPrvVJlDMxK2xx_nfrwsc") ?: ""
    fun getIdCompany(): String = prefs.getString(KEY_ID_COMPANY, "") ?: ""
    fun getIdCompanyParent(): String = prefs.getString(KEY_ID_COMPANY_PARENT, "") ?: ""
    fun getAppType(): String = prefs.getString(KEY_APP_TYPE, "") ?: ""
    fun getEmail(): String = prefs.getString(KEY_EMAIL, "") ?: ""
    fun getPhone(): String = prefs.getString(KEY_PHONE, "") ?: ""
    fun getSalesPointId(): String = prefs.getString(KEY_ID_SALE_POINT, "") ?: ""
    fun getSalesPointName(): String = prefs.getString(KEY_SALE_POINT_NAME, "") ?: ""
    fun getFullName(): String = prefs.getString(KEY_ID_FULL_NAME, "") ?: ""
    fun getPermissionMobile(): String = prefs.getString(KEY_PERMISSION, "") ?: ""
    fun getDomain(): String = prefs.getString(KEY_DOMAIN, "") ?: "http://192.168.1.119:910"
    fun getDomainCustomer(): String = prefs.getString(KEY_DOMAIN_CUSTOMER, "dcheery") ?: ""
    fun getDevice(): String = prefs.getString(KEY_DEVICE, "") ?: ""
    fun getDiscountAgency(): String = prefs.getString(KEY_DISCOUNT_AGENCY, "") ?: ""
    fun getPaySalary(): String = prefs.getString(KEY_PAY_SALARY, "") ?: ""
    fun getDisplayService(): String = prefs.getString(KEY_DISPLAY_SERVICE, "") ?: ""
    fun getDisplayProduct(): String = prefs.getString(KEY_DISPLAY_PRODUCT, "") ?: ""
    fun getDisplayPriority(): String = prefs.getString(KEY_DISPLAY_PRIORITY, "") ?: ""
    fun getCategory(): String = prefs.getString(KEY_CATEGORY, "") ?: ""
    fun getTypeAccount(): String = prefs.getString(KEY_TYPE_ACCOUNT, "") ?: ""
    fun getShowPhoneKH(): Boolean = prefs.getBoolean(KEY_SHOW_PHONE, false)
    fun getShowEmailKH(): Boolean = prefs.getBoolean(KEY_SHOW_EMAIL, false)
    fun getShowAddressKH(): Boolean = prefs.getBoolean(KEY_SHOW_ADDRESS, false)
    fun isOfflineMode(): Boolean = prefs.getBoolean(KEY_LOGIN, false)
    fun getAdmin(): String = prefs.getString(KEY_ADMIN, "") ?: ""
    fun getAdminRoot(): String = prefs.getString(KEY_ADMIN_ROOT, "") ?: ""

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
