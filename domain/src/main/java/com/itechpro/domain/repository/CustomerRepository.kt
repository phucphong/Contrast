package com.itechpro.domain.repository



import com.itechpro.domain.model.Account
import com.itechpro.domain.model.Column1
import com.itechpro.domain.model.Customer
import com.itechpro.domain.model.NetworkResponse


interface CustomerRepository {



    suspend fun addEditCustomer(url: String, obj: Customer,authen: String): NetworkResponse<List<Customer>>

    suspend fun customerDetail (
        obj: String?,
        mode: String?,
        ido: String?,
        authen: String?
    ): NetworkResponse<List<Customer>>

    suspend fun loadTimelineData (
        obj: String?,
        mode: String?,
        ido: String?,
        authen: String?
    ): NetworkResponse<List<Customer>>


    suspend fun loadExchangeData (
        obj: String?,
        mode: String?,
        ido: String?,
        authen: String?
    ): NetworkResponse<List<Customer>>


    suspend fun checkPhone(
        obj: String?,
        mode: String?,
        phone: String?,
        ido: String?,
        authen: String?
    ): NetworkResponse<List<Column1>>

    suspend fun checkEmail(
        obj: String?,
        mode: String?,
        email: String?,
        ido: String?,
        authen: String?
    ): NetworkResponse<List<Column1>>


}