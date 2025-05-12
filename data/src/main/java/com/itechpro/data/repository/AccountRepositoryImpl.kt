package com.itechpro.data.repository

import com.itechpro.data.api.AccountAPI
import com.itechpro.domain.model.Account
import com.itechpro.domain.model.network.NetworkResponse
import com.itechpro.domain.repository.AccountRepository
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
