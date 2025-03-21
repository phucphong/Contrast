package com.itechpro.domain.repository

import com.itechpro.domain.model.Account
import com.itechpro.domain.model.Column1
import com.itechpro.domain.model.NetworkResponse


interface RegisterAccountRepository {

    suspend fun registerAccount(url: String, account: Account): NetworkResponse<List<Account>>

    suspend fun checkPhone(
        obj: String?,
        mode: String?,
        phone: String?,
        idEmployee: String?,
        authen: String?
    ): NetworkResponse<List<Column1>>

    suspend fun checkEmail(
        obj: String?,
        mode: String?,
        email: String?,
        idnhanvien: String?,
        authen: String?
    ): NetworkResponse<List<Column1>>

    suspend fun checkPhoneOff(
        obj: String?,
        mode: String?,
        phone: String?,
        idEmployee: String?,
        key: String?
    ): NetworkResponse<List<Column1>>

    suspend fun checkEmailOff(
        obj: String?,
        mode: String?,
        email: String?,
        idEmployee: String?,
        key: String?
    ): NetworkResponse<List<Column1>>

}