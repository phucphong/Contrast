package com.itechpro.data.repository





import android.util.Log
import com.itechpro.data.api.CustomerAPI

import com.itechpro.domain.model.Account
import com.itechpro.domain.model.Column1
import com.itechpro.domain.model.Customer
import com.itechpro.domain.model.NetworkResponse
import com.itechpro.domain.model.News
import com.itechpro.domain.repository.CustomerRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

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

    override suspend fun getCustomerDetail(
        obj: String,
        mode: String,
        ido: String,
        authen: String
    ): NetworkResponse<List<Customer>> {
        val response = api.getCustomerDetail(obj,"getbyid",ido,authen)
        return if (response.isSuccessful) {

            NetworkResponse.Success(response.body() ?: emptyList())
        } else {
            NetworkResponse.Error("Lỗi: ${response.message()}")
        }
    }




      override suspend  fun getTimelineData(
          obj: String,
          mode: String,
        ido: String,
        authen: String
    ): NetworkResponse<List<Customer>> {
        val response = api.getTimelineData(obj,"gettimeline",ido,authen)
        return if (response.isSuccessful) {

            NetworkResponse.Success(response.body() ?: emptyList())
        } else {
            NetworkResponse.Error("Lỗi: ${response.message()}")
        }
    }



      override suspend  fun getExchangeData(
          obj: String,
          mode: String,
        ido: String,
        authen: String
    ): NetworkResponse<List<Customer>> {
        val response = api.getExchangeData(obj,"gettraodoi",ido,authen)
        return if (response.isSuccessful) {

            NetworkResponse.Success(response.body() ?: emptyList())
        } else {
            NetworkResponse.Error("Lỗi: ${response.message()}")
        }
    }


// hạng thẻ
      override suspend  fun getCustomerCardInformation(
          obj: String,
          mode: String,
          idCustomer: String,
          idOder: String,
          dateOder: String,
          authen: String
    ): NetworkResponse<List<Customer>> {
        val response = api.getCustomerCardInformation(obj,"laythongtinhangthekh",idCustomer,idOder,dateOder,authen)
        return if (response.isSuccessful) {

            NetworkResponse.Success(response.body() ?: emptyList())
        } else {
            NetworkResponse.Error("Lỗi: ${response.message()}")
        }
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
