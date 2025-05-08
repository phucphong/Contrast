package com.itechpro.domain.usecase.login

import com.itechpro.domain.model.Account
import com.itechpro.domain.model.Login
import com.itechpro.domain.model.NetworkResponse
import com.itechpro.domain.repository.LoginRepository
import com.itechpro.domain.repository.RegisterAccountRepository
import javax.inject.Inject



class LoginUseCase @Inject constructor(
    private val repository: LoginRepository
) {

    suspend operator fun invoke(account: Login): NetworkResponse<Login> {
        return when (val result = repository.login(account)) {
            is NetworkResponse.Success -> {
                NetworkResponse.Success(result.data)
            }
            is NetworkResponse.Error -> {
                NetworkResponse.Error(result.message)
            }
            else -> NetworkResponse.Error("Đã xảy ra lỗi không xác định")
        }
    }

}
