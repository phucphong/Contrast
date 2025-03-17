package com.itechpro.domain.usecase.account



import com.itechpro.domain.model.Account
import com.itechpro.domain.model.NetworkResponse
import com.itechpro.domain.repository.AccountRepository

class AccountUseCase(
    private val repository: AccountRepository
) {
    suspend fun execute(account: Account, authToken: String?, type: String): NetworkResponse<List<Account>> {
        return if (type == "off") {
            if (account.username.isNullOrEmpty() || account.password.isNullOrEmpty()) {
                return NetworkResponse.Error("Username hoặc Password không hợp lệ")
            }
            repository.updateAccountOffline(account.username, account.password, account.key)
        } else {
            repository.updateAccount(account, authToken)
        }
    }
}
