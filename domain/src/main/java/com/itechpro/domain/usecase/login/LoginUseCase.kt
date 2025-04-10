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

    suspend operator fun invoke(account: Login): NetworkResponse<List<Login>> {
        return repository.login( account)
    }
}
