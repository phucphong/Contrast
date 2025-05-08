package com.itechpro.data.repository



import com.itechpro.data.api.LoginAPI
import com.itechpro.domain.model.Login
import com.itechpro.domain.model.NetworkResponse
import com.itechpro.domain.repository.LoginRepository
import javax.inject.Inject

class LoginRepositoryImpl @Inject constructor(
    private val api: LoginAPI
) : LoginRepository {



    override suspend fun login(account: Login): NetworkResponse<Login> {
        return try {
            val response = api.login(account)
            if (response.isSuccessful) {
                val body = response.body()
                if (body != null) {
                    NetworkResponse.Success(body)
                } else {
                    NetworkResponse.Error("Phản hồi rỗng từ server")
                }
            } else {
                NetworkResponse.Error("Lỗi: ${response.message()}")
            }
        } catch (e: Exception) {
            NetworkResponse.Error("Exception: ${e.message}")
        }
    }



}
