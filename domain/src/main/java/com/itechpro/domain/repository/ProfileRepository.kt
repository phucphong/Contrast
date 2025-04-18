package com.itechpro.domain.repository
import com.itechpro.domain.model.Account
import com.itechpro.domain.model.Category
import com.itechpro.domain.model.NetworkResponse
interface ProfileRepository {


    suspend fun getMenuApp(
        type: String,
        authen: String
    ): NetworkResponse<List<Category>>


    suspend fun getQrCodeEmployee(
        obj: String,
        mode: String,
        authen: String
    ): NetworkResponse<List<Account>>

    suspend fun getQrCodeCustomer(
        obj: String,
        mode: String,
        ido: String,
        authen: String
    ): NetworkResponse<List<Account>>




}
