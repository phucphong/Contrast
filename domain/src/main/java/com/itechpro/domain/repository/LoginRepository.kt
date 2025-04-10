package com.itechpro.domain.repository



import com.itechpro.domain.model.Account
import com.itechpro.domain.model.Column1
import com.itechpro.domain.model.Login
import com.itechpro.domain.model.NetworkResponse


interface LoginRepository {

    suspend fun login(account: Login): NetworkResponse<List<Login>>



}