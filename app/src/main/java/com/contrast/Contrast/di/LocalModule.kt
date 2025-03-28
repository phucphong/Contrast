package com.contrast.Contrast.di

import android.content.Context
import com.contrast.Contrast.utils.AppConfig
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Named

@Module
@InstallIn(ViewModelComponent::class)
class LocalModule {

    @Provides
    @Named("domain")
    fun provideDomain(@ApplicationContext context: Context): String {
        return AppConfig.getDomain(context)
    }

    @Provides
    @Named("idCustomer")
    fun provideIdCustomer(@ApplicationContext context: Context): String {
        return AppConfig.getIdCustomer(context)
    }

    @Provides
    @Named("domainCustomer")
    fun provideDomainCustomer(@ApplicationContext context: Context): String {
        return AppConfig.getDomainCustomer(context)
    }

    @Provides
    @Named("authen")
    fun provideAuthen(@ApplicationContext context: Context): String {
        return AppConfig.getToken(context)
    }

    @Provides
    @Named("discountAgency")
    fun provideDiscountAgency(@ApplicationContext context: Context): String {
        return AppConfig.getDiscountAgency(context)
    }


    @Provides
    @Named("idEmployee")
    fun provideIdEmployee(@ApplicationContext context: Context): String {
        return AppConfig.getIdEmployee(context)
    }

    @Provides
    @Named("employeeName")
    fun provideEmployeeName(@ApplicationContext context: Context): String {
        return AppConfig.getEmployeeName(context)
    }

    @Provides
    @Named("adminRoot")
    fun provideAdminRoot(@ApplicationContext context: Context): String {
        return AppConfig.getAdminRoot(context)
    }

    @Provides
    @Named("admin")
    fun provideAdmin(@ApplicationContext context: Context): String {
        return AppConfig.getAdmin(context)
    }

    @Provides
    @Named("showPhoneKH")
    fun provideShowPhoneKH(@ApplicationContext context: Context): Boolean {
        return AppConfig.getShowPhoneKH(context)
    }

    @Provides
    @Named("showEmailKH")
    fun provideShowEmailKH(@ApplicationContext context: Context): Boolean {
        return AppConfig.getShowEmailKH(context)
    }

    @Provides
    @Named("showAddressKH")
    fun provideAddressKH(@ApplicationContext context: Context): Boolean {
        return AppConfig.getShowAddressKH(context)
    }


    @Provides
    @Named("permissionMobile")
    fun providePermissionMobile(@ApplicationContext context: Context): String {
        return AppConfig.getPermissionMobile(context)
    }

//    @Provides
//    @Named("grid")
//    fun provideGrid(@ApplicationContext context: Context): Boolean {
//        return AppConfig.getGrid(context)
//    }

    @Provides
    @Named("defaultColorApp")
    fun provideDefaultColorApp(@ApplicationContext context: Context): String {
        return AppConfig.getDefaultColorApp(context)
    }

    @Provides
    @Named("idCompany")
    fun provideIdCompany(@ApplicationContext context: Context): String {
        return AppConfig.getIdCompanny(context)
    }

    @Provides
    @Named("appType")
    fun provideAppType(@ApplicationContext context: Context): String {
        return AppConfig.getAppType(context)
    }

    @Provides
    @Named("idCompanyParent")
    fun provideIdCompanyParent(@ApplicationContext context: Context): String {
        return AppConfig.getIdCompannyParent(context)
    }

    @Provides
    @Named("fullName")
    fun provideFullName(@ApplicationContext context: Context): String {
        return AppConfig.getFullName(context)
    }

    @Provides
    @Named("phone")
    fun providePhone(@ApplicationContext context: Context): String {
        return AppConfig.getPhone(context)
    }

    @Provides
    @Named("email")
    fun provideEmail(@ApplicationContext context: Context): String {
        return AppConfig.getEmail(context)
    }

    @Provides
    @Named("idSalesPoint")
    fun provideSalesPoint(@ApplicationContext context: Context): String {
        return AppConfig.getIdSalesPoint(context)
    }

    @Provides
    @Named("salesPointName")
    fun provideSalesPointName(@ApplicationContext context: Context): String {
        return AppConfig.getSalesPointName(context)
    }

    @Provides
    @Named("typeAccount")
    fun provideTypeAccount(@ApplicationContext context: Context): String {
        return AppConfig.getTypeAccount(context)
    }

    @Provides
    @Named("category")
    fun provideCategory(@ApplicationContext context: Context): String {
        return AppConfig.getCategory(context)
    }

    @Provides
    @Named("displayService")
    fun provideDisplayService(@ApplicationContext context: Context): String {
        return AppConfig.getDisplayService(context)
    }

    @Provides
    @Named("displayProduct")
    fun provideDisplayProduct(@ApplicationContext context: Context): String {
        return AppConfig.getDisplayProduct(context)
    }

    @Provides
    @Named("displayPriority")
    fun provideDisplayPriority(@ApplicationContext context: Context): String {
        return AppConfig.getDisplayPriority(context)
    }

    @Provides
    @Named("isOffLine")
    fun provideIsLogin(@ApplicationContext context: Context): Boolean {
        return AppConfig.getOffLine(context)
    }


    @Provides
    @Named("paySalary")
    fun providePaySalary(@ApplicationContext context: Context): String {
        return AppConfig.getPaySalary(context)
    }
}