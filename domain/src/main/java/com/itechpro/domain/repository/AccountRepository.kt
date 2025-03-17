package com.itechpro.domain.repository

import com.itechpro.domain.model.Account
import com.itechpro.domain.model.Column1
import com.itechpro.domain.model.NetworkResponse



interface AccountRepository {
    suspend fun updateAccount(account: Account?, authToken: String?): NetworkResponse<List<Account>>
    suspend fun updateAccountOffline(username: String?, password: String?, key: String?): NetworkResponse<List<Account>>


}