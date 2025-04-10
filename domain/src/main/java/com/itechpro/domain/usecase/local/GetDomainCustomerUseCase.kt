package com.itechpro.domain.usecase.local

import com.itechpro.domain.preferences.PreferencesManager
import javax.inject.Inject

class GetDomainCustomerUseCase @Inject constructor(
    private val preferencesManager: PreferencesManager
) {
    operator fun invoke(): String = preferencesManager.getDomainCustomer()
}
