package com.itechpro.data.repository





import android.util.Log
import com.itechpro.data.api.CustomerAPI

import com.itechpro.domain.model.Account
import com.itechpro.domain.model.Column1
import com.itechpro.domain.model.Customer
import com.itechpro.domain.model.NetworkResponse
import com.itechpro.domain.repository.CustomerRepository

import javax.inject.Inject

class CustomerRepositoryImpl @Inject constructor(
    private val api: CustomerAPI
) : CustomerRepository {



    override suspend fun addEditCustomer(url: String, obj:Customer,authen: String): NetworkResponse<List<Customer>> {
        return try {
            val response = api.addEditCustomer(url, obj,authen)
            if (response.isSuccessful) {
                NetworkResponse.Success(response.body() ?: emptyList())
            } else {
                NetworkResponse.Error("Lỗi: ${response.message()}")
            }
        } catch (e: Exception) {
            NetworkResponse.Error("Exception: ${e.message}")


        }
    }

    override suspend fun customerDetail(
        obj: String?,
        mode: String?,
        ido: String?,
        authen: String?
    ): NetworkResponse<List<Customer>> {
        TODO("Not yet implemented")
    }
    override suspend fun loadTimelineData(
        obj: String?,
        mode: String?,
        ido: String?,
        authen: String?
    ): NetworkResponse<List<Customer>> {
        TODO("Not yet implemented")
    }
    override suspend fun loadExchangeData(
        obj: String?,
        mode: String?,
        ido: String?,
        authen: String?
    ): NetworkResponse<List<Customer>> {
        TODO("Not yet implemented")
    }

    override suspend fun checkPhone(
        obj: String?,
        mode: String?,
        phone: String?,
        ido: String?,
        authen: String?
    ): NetworkResponse<List<Column1>> {
        return try {
            val response = api.checkPhone(obj, mode, phone, ido, authen)
            if (response.isSuccessful) {
                response.body()?.let {
                    NetworkResponse.Success(it)
                } ?: NetworkResponse.Error("Dữ liệu rỗng")
            } else {
                NetworkResponse.Error("Lỗi: ${response.message()}")
            }
        } catch (e: Exception) {
            NetworkResponse.Error("Exception: ${e.message}")
        }
    }

    override suspend fun checkEmail(
        obj: String?,
        mode: String?,
        email: String?,
        idnhanvien: String?,
        authen: String?
    ): NetworkResponse<List<Column1>> {
        return try {
            val response = api.checkEmail(obj, mode, email, idnhanvien, authen)
            if (response.isSuccessful) {
                response.body()?.let {
                    NetworkResponse.Success(it)
                } ?: NetworkResponse.Error("Dữ liệu rỗng")
            } else {
                NetworkResponse.Error("Lỗi: ${response.message()}")
            }
        } catch (e: Exception) {
            NetworkResponse.Error("Exception: ${e.message}")
        }
    }


}
