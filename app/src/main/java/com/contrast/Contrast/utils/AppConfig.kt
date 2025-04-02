package com.contrast.Contrast.utils

import android.content.Context
import android.content.SharedPreferences
import java.util.Locale

object AppConfig {

    private const val SHARED_PREFERENCES_NAME = "Phucphong"
    private const val KEY_DOMAIN_CUSTOMER = "domainkh"
    private const val KEY_DOMAIN = "domain"
    private const val KEY_DEVICE = "device"
    private const val KEY_TOKEN = "token"
    private const val KEY_DISCOUNT_AGENCY = "discountAgency"
    private const val KEY_ID_EMPLOYEE = "idEmployee"
    private const val KEY_EMPLOYEE_NAME = "idEmployee"
    private const val KEY_ADMIN_ROOT = "adminRoot"
    private const val KEY_ADMIN = "admin"
    private const val KEY_SHOW_PHONE = "showPhoneKH"
    private const val KEY_SHOW_EMAIL = "showEmailKH"
    private const val KEY_SHOW_ADDRESS = "showAddressKH"
    private const val KEY_PERMISSION = "PermissionMobile"
    private const val KEY_DEFAULT_COLOR_APP = "DefaultColorApp"
    private const val KEY_ID_COMPANNY = "IdCompanny"
    private const val KEY_APP_TYPE = "appType"
    private const val KEY_ID_COMPANNY_PARENT = "idCompannyParent"
    private const val KEY_ID_FULL_NAME = "FullName"
    private const val KEY_PHONE = "phone"
    private const val KEY_EMAIL = "email"
    private const val KEY_ID_SALE_POINT = "idSalesPoint"
    private const val KEY_SALE_POINT_NAME = "salesPointName"
    private const val KEY_TYPE_ACCOUNT = "typeAccount"
    private const val KEY_CATEGORY = "category"
    private const val KEY_DISPLAY_SERVICE = "displayService"
    private const val KEY_DISPLAY_PRODUCT = "DisplayProduct"
    private const val KEY_DISPLAY_PRIORITY = "displayPriority"
    private const val KEY_LOGIN = "login"
    private const val KEY_PAY_SALARY = "paySalary"
    private const val KEY_ID_CUSTOMER = "idCustomer"
    private fun getSharedPreferences(context: Context): SharedPreferences {
        return context.getSharedPreferences(SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE)
    }




    fun setIdCustomer(context: Context, value: String?) {
        getSharedPreferences(context).edit()
            .putString(KEY_ID_CUSTOMER, value)
            .apply()
    }

    fun getIdCustomer(context: Context): String {
        return getSharedPreferences(context)
            .getString(KEY_DISPLAY_PRIORITY, "") ?: ""
    }

    fun setDisplayPriority(context: Context, value: String?) {
        getSharedPreferences(context).edit()
            .putString(KEY_DISPLAY_PRIORITY, value)
            .apply()
    }

    fun getDisplayPriority(context: Context): String {
        return getSharedPreferences(context)
            .getString(KEY_DISPLAY_PRIORITY, "") ?: ""
    }


    fun setPaySalary(context: Context, value: String?) {
        getSharedPreferences(context).edit()
            .putString(KEY_PAY_SALARY, value)
            .apply()
    }

    fun getPaySalary(context: Context): String {
        return getSharedPreferences(context)
            .getString(KEY_PAY_SALARY, "") ?: ""
    }
    fun setDisplayProduct(context: Context, value: String?) {
        getSharedPreferences(context).edit()
            .putString(KEY_DISPLAY_PRODUCT, value)
            .apply()
    }

    fun getDisplayProduct(context: Context): String {
        return getSharedPreferences(context)
            .getString(KEY_DISPLAY_PRODUCT, "") ?: ""
    }

    fun setDisplayService(context: Context, value: String?) {
        getSharedPreferences(context).edit()
            .putString(KEY_DISPLAY_SERVICE, value)
            .apply()
    }

    fun getDisplayService(context: Context): String {
        return getSharedPreferences(context)
            .getString(KEY_DISPLAY_SERVICE, "") ?: ""
    }

    fun setCategory(context: Context, value: String?) {
        getSharedPreferences(context).edit()
            .putString(KEY_CATEGORY, value)
            .apply()
    }

    fun getCategory(context: Context): String {
        return getSharedPreferences(context)
            .getString(KEY_CATEGORY, "") ?: ""
    }


    fun setTypeAccount(context: Context, value: String?) {
        getSharedPreferences(context).edit()
            .putString(KEY_TYPE_ACCOUNT, value)
            .apply()
    }

    fun getTypeAccount(context: Context): String {
        return getSharedPreferences(context)
            .getString(KEY_TYPE_ACCOUNT, "") ?: ""
    }


    fun setIdSalesPoint(context: Context, value: String?) {
        getSharedPreferences(context).edit()
            .putString(KEY_SALE_POINT_NAME, value)
            .apply()
    }

    fun getIdSalesPoint(context: Context): String {
        return getSharedPreferences(context)
            .getString(KEY_SALE_POINT_NAME, "") ?: ""
    }

    fun setSalesPointName(context: Context, value: String?) {
        getSharedPreferences(context).edit()
            .putString(KEY_ID_SALE_POINT, value)
            .apply()
    }

    fun getSalesPointName(context: Context): String {
        return getSharedPreferences(context)
            .getString(KEY_ID_SALE_POINT, "") ?: ""
    }

    fun setEmail(context: Context, value: String?) {
        getSharedPreferences(context).edit()
            .putString(KEY_EMAIL, value)
            .apply()
    }

    fun getEmail(context: Context): String {
        return getSharedPreferences(context)
            .getString(KEY_EMAIL, "") ?: ""
    }


    fun setFullName(context: Context, value: String?) {
        getSharedPreferences(context).edit()
            .putString(KEY_ID_FULL_NAME, value)
            .apply()
    }

    fun getFullName(context: Context): String {
        return getSharedPreferences(context)
            .getString(KEY_ID_FULL_NAME, "") ?: ""
    }

    fun setPhone(context: Context, value: String?) {
        getSharedPreferences(context).edit()
            .putString(KEY_PHONE, value)
            .apply()
    }

    fun getPhone(context: Context): String {
        return getSharedPreferences(context)
            .getString(KEY_PHONE, "") ?: ""
    }

    fun setIdCompannyParent(context: Context, value: String?) {
        getSharedPreferences(context).edit()
            .putString(KEY_ID_COMPANNY_PARENT, value)
            .apply()
    }

    fun getIdCompannyParent(context: Context): String {
        return getSharedPreferences(context)
            .getString(KEY_ID_COMPANNY_PARENT, "") ?: ""
    }

    fun setAppType(context: Context, value: String?) {
        getSharedPreferences(context).edit()
            .putString(KEY_APP_TYPE, value)
            .apply()
    }

    fun getAppType(context: Context): String {
        return getSharedPreferences(context)
            .getString(KEY_APP_TYPE, "") ?: ""
    }


    fun setIdCompanny(context: Context, value: String?) {
        getSharedPreferences(context).edit()
            .putString(KEY_ID_COMPANNY, value)
            .apply()
    }

    fun getIdCompanny(context: Context): String {
        return getSharedPreferences(context)
            .getString(KEY_ID_COMPANNY, "") ?: ""
    }

    fun setDefaultColorApp(context: Context, value: String?) {
        getSharedPreferences(context).edit()
            .putString(KEY_DEFAULT_COLOR_APP, value)
            .apply()
    }

    fun getDefaultColorApp(context: Context): String {
        return getSharedPreferences(context)
            .getString(KEY_DEFAULT_COLOR_APP, "") ?: ""
    }

    fun setPermissionMobile(context: Context, value: String?) {
        getSharedPreferences(context).edit()
            .putString(KEY_PERMISSION, value)
            .apply()
    }

    fun getPermissionMobile(context: Context): String {
        return getSharedPreferences(context)
            .getString(KEY_PERMISSION, "") ?: ""
    }


    fun setShowAddressKH(context: Context, value: Boolean) {
        getSharedPreferences(context).edit()
            .putBoolean(KEY_SHOW_ADDRESS, value)
            .apply()
    }

    fun getShowAddressKH(context: Context): Boolean {
        return getSharedPreferences(context)
            .getBoolean(KEY_SHOW_ADDRESS, false) ?: false
    }

    fun setShowPhoneKH(context: Context, value: Boolean) {
        getSharedPreferences(context).edit()
            .putBoolean(KEY_SHOW_PHONE, value)
            .apply()
    }

    fun setOffLine(context: Context, value: Boolean) {
        getSharedPreferences(context).edit()
            .putBoolean(KEY_LOGIN, value)
            .apply()
    }

    fun getOffLine(context: Context): Boolean {
        return getSharedPreferences(context)
            .getBoolean(KEY_LOGIN, false) ?: false
    }

    fun getShowPhoneKH(context: Context): Boolean {
        return getSharedPreferences(context)
            .getBoolean(KEY_SHOW_PHONE, false) ?: false
    }


    fun setShowEmailKH(context: Context, value: Boolean) {
        getSharedPreferences(context).edit()
            .putBoolean(KEY_SHOW_EMAIL, value)
            .apply()
    }

    fun getShowEmailKH(context: Context): Boolean {
        return getSharedPreferences(context)
            .getBoolean(KEY_SHOW_EMAIL, false) ?: false
    }


    fun setAdmin(context: Context, value: String?) {
        getSharedPreferences(context).edit()
            .putString(KEY_ADMIN, value)
            .apply()
    }

    fun getAdmin(context: Context): String {
        return getSharedPreferences(context)
            .getString(KEY_ADMIN, "") ?: ""
    }


    fun setAdminRoot(context: Context, value: String?) {
        getSharedPreferences(context).edit()
            .putString(KEY_ADMIN_ROOT, value)
            .apply()
    }

    fun getAdminRoot(context: Context): String {
        return getSharedPreferences(context)
            .getString(KEY_ADMIN_ROOT, "") ?: ""
    }

    fun setEmployeeName(context: Context, value: String?) {
        getSharedPreferences(context).edit()
            .putString(KEY_EMPLOYEE_NAME, value)
            .apply()
    }

    fun getEmployeeName(context: Context): String {
        return getSharedPreferences(context)
            .getString(KEY_EMPLOYEE_NAME, "") ?: ""
    }


    fun setIdEmployee(context: Context, value: String?) {
        getSharedPreferences(context).edit()
            .putString(KEY_ID_EMPLOYEE, value)
            .apply()
    }

    fun getIdEmployee(context: Context): String {
        return getSharedPreferences(context)
            .getString(KEY_ID_EMPLOYEE, "0") ?: "0"
    }


    fun setDiscountAgency(context: Context, value: String?) {
        getSharedPreferences(context).edit()
            .putString(KEY_DISCOUNT_AGENCY, value)
            .apply()
    }

    fun getDiscountAgency(context: Context): String {
        return getSharedPreferences(context)
            .getString(KEY_DISCOUNT_AGENCY, "") ?: ""
    }


    fun setToken(context: Context, value: String?) {
        getSharedPreferences(context).edit()
            .putString(KEY_TOKEN, value)
            .apply()
    }

    fun getToken(context: Context): String {
        return getSharedPreferences(context)
            .getString(KEY_TOKEN, "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1bmlxdWVfbmFtZSI6IjA5NjQ5MzEyMjUiLCJ1c2VybmFtZSI6IjA5NjQ5MzEyMjUiLCJpZCI6IjE4MTUyOTEiLCJpc2FkbWluIjoiRmFsc2UiLCJhY3RpdmUiOiJUcnVlIiwiaWRjb25ndHkiOiIxIiwiaXNhZG1pbmNvc28iOiJGYWxzZSIsImlkZGllbWJhbmxlIjoiMyIsImlka2giOiI4OTEzNzc0NTMyODQ0MjQwMzExIiwibG9haWtoIjoiY2FuaGFuIiwiaWRsaCI6IjAiLCJsb2FpdGsiOiJraGFjaGhhbmciLCJob3RlbiI6IiIsImRpZW50aG9haSI6IiIsImVtYWlsIjoiIiwidGVua2hhY2hoYW5nIjoiSVRFQ0hQUk8iLCJtYWtoYWNoaGFuZyI6Iml0cCIsImlkY2hhdCI6IiIsInNlcnZlcmNoYXQiOiIiLCJuYmYiOjE3NDM1NjY5NjksImV4cCI6MTc1MjIwNjk2OSwiaWF0IjoxNzQzNTY2OTY5LCJpc3MiOiJodHRwOi8vMTkyLjE2OC4xLjExOTo5MTAiLCJhdWQiOiJodHRwOi8vMTkyLjE2OC4xLjExOTo5MTAifQ.GhZ5cQ2b3w4AYiJj_8_4XCQDp8QDY4LbNqlVNBy0qRw"
            ) ?: "" }

    fun setDomainCustomer(context: Context, value: String?) {
        getSharedPreferences(context).edit()
            .putString(KEY_DOMAIN_CUSTOMER, value)
            .apply()
    }

    fun getDomainCustomer(context: Context): String {
        return getSharedPreferences(context)
            .getString(KEY_DOMAIN_CUSTOMER, "") ?: ""
    }

    fun setDomain(context: Context, domain: String) {
        getSharedPreferences(context).edit()
            .putString(KEY_DOMAIN, domain.lowercase(Locale.getDefault()))
            .apply()
    }

    fun getDomain(context: Context?): String {
        if (context == null) return "http://192.168.1.119:910"
        return getSharedPreferences(context)
            .getString(KEY_DOMAIN, "") ?: ""
    }

    fun setDevice(context: Context, device: String) {
        getSharedPreferences(context).edit()
            .putString(KEY_DEVICE, device)
            .apply()
    }

    fun getDevice(context: Context?): String {
        if (context == null) return ""
        return getSharedPreferences(context)
            .getString(KEY_DEVICE, "") ?: ""
    }
}
