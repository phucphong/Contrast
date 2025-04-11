package com.itechpro.domain.repository



import com.itechpro.domain.model.Column1
import com.itechpro.domain.model.Customer
import com.itechpro.domain.model.NetworkResponse


interface CustomerRepository {



    suspend fun addEditCustomer(url: String, obj: Customer,authen: String): NetworkResponse<List<Customer>>



    suspend fun getCustomerDetail (
        obj: String,
        mode: String,
        ido: String,
        authen: String
    ): NetworkResponse<List<Customer>>

    suspend fun getTimelineData (
        obj: String,
        mode: String,
        ido: String,
        authen: String
    ): NetworkResponse<List<Customer>>


    suspend fun getExchangeData (
        obj: String,
        mode: String,
        ido: String,
        authen: String
    ): NetworkResponse<List<Customer>>

    suspend fun getCustomerCardInformation (
        obj: String,
        mode: String,
        idCustomer: String,
        idOder: String,
        dateOder: String,
        authen: String
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