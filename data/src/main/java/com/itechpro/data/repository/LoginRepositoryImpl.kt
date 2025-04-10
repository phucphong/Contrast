package com.itechpro.data.repository



import com.itechpro.data.api.LoginAPI
import com.itechpro.data.api.RegisterAccountAPI
import com.itechpro.domain.model.Account
import com.itechpro.domain.model.Column1
import com.itechpro.domain.model.Login
import com.itechpro.domain.model.NetworkResponse
import com.itechpro.domain.repository.LoginRepository
import com.itechpro.domain.repository.RegisterAccountRepository
import javax.inject.Inject

class LoginRepositoryImpl @Inject constructor(
    private val api: LoginAPI
) : LoginRepository {



    override suspend fun login( account: Login): NetworkResponse<List<Login>> {
        return try {
            val response = api.login( account)
            if (response.isSuccessful) {
                NetworkResponse.Success(response.body() ?: emptyList())
            } else {
                NetworkResponse.Error("Lỗi: ${response.message()}")
            }
        } catch (e: Exception) {
            NetworkResponse.Error("Exception: ${e.message}")
        }
    }


}
