package com.itechpro.domain.usecase.register

import com.itechpro.domain.model.Account
import com.itechpro.domain.model.NetworkResponse
import com.itechpro.domain.repository.RegisterAccountRepository
import javax.inject.Inject

class RegisterAccountUseCase @Inject constructor(
    private val repository: RegisterAccountRepository
) {

    suspend operator fun invoke(url: String, account: Account): NetworkResponse<List<Account>> {
        return repository.registerAccount(url, account)
    }
}
