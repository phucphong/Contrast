package com.itechpro.domain.repository



import com.itechpro.domain.model.login.Login
import com.itechpro.domain.model.network.NetworkResponse


interface LoginRepository {

    suspend fun login(account: Login): NetworkResponse<Login>



}