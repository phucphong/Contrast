// ✅ Clean, tối ưu: Gộp gọi dữ liệu vào 1 model duy nhất, loại bỏ gọi lẻ

package com.itechpro.data.local

import com.itechpro.domain.model.CurrentUserInfo
import com.itechpro.domain.preferences.PreferencesManager
import javax.inject.Inject

class LocalDataSource @Inject constructor(
    private val preferencesManager: PreferencesManager
) {
    fun getCurrentUser(): CurrentUserInfo = with(preferencesManager) {
        CurrentUserInfo(
            employeeId = getEmployeeId(),
            employeeName = getEmployeeName(),
            fullName = getFullName(),
            email = getEmail(),
            phone = getPhone(),

            idCompany = getIdCompany(),
            idCompanyParent = getIdCompanyParent(),
            appType = getAppType(),
            salesPointId = getSalesPointId(),
            salesPointName = getSalesPointName(),
            category = getCategory(),
            typeAccount = getTypeAccount(),

            token = getToken(),
            permissionMobile = getPermissionMobile(),
            domain = getDomain(),
            domainCustomer = getDomainCustomer(),
            device = getDevice(),
            isOfflineMode = isOfflineMode(),

            displayService = getDisplayService(),
            displayProduct = getDisplayProduct(),
            displayPriority = getDisplayPriority(),

            discountAgency = getDiscountAgency(),
            paySalary = getPaySalary(),

            showPhoneKH = getShowPhoneKH(),
            showEmailKH = getShowEmailKH(),
            showAddressKH = getShowAddressKH(),

            admin = getAdmin(),
            adminRoot = getAdminRoot()
        )
    }
}
