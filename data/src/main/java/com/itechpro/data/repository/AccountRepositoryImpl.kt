package com.itechpro.data.repository

import com.itechpro.data.api.AccountAPI
import com.itechpro.data.api.RegisterAccountAPI
import com.itechpro.domain.model.Account
import com.itechpro.domain.model.Column1
import com.itechpro.domain.model.NetworkResponse
import com.itechpro.domain.repository.AccountRepository
import com.itechpro.domain.repository.RegisterAccountRepository
import javax.inject.Inject



class AccountRepositoryImpl @Inject constructor(
    private val api: AccountAPI
) : AccountRepository {

    override suspend fun updateAccount(account: Account?, authToken: String?): NetworkResponse<List<Account>> {
        return try {
            val response = api.getUpdateAccount(account, authToken)
            if (response.isSuccessful) {
                NetworkResponse.Success(response.body() ?: emptyList())
            } else {
                NetworkResponse.Error("Lỗi: ${response.message()}")
            }
        } catch (e: Exception) {
            NetworkResponse.Error("Lỗi kết nối: ${e.message}")
        }
    }

    override suspend fun updateAccountOffline(username: String?, password: String?, key: String?): NetworkResponse<List<Account>> {
        return try {
            val response = api.getUpdateAccountOff(username, password, key)
            if (response.isSuccessful) {
                NetworkResponse.Success(response.body() ?: emptyList())
            } else {
                NetworkResponse.Error("Lỗi: ${response.message()}")
            }
        } catch (e: Exception) {
            NetworkResponse.Error("Lỗi kết nối: ${e.message}")
        }
    }


}
