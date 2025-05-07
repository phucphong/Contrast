package com.itechpro.data.repository




import com.itechpro.data.api.PaymentAPI


import com.itechpro.domain.model.NetworkResponse

import com.itechpro.domain.model.payment.InfoPayment

import com.itechpro.domain.repository.PaymentRepository

import javax.inject.Inject

class PaymentRepositoryImpl @Inject constructor(
    private val api: PaymentAPI
) : PaymentRepository {










    //    suspend fun checkOder(ids:String) = apiService.checkOder("checkgiavakm", "checkgiavakm",ids, authen)
    override suspend fun getInfoPayment( money: String,oderKey: String,authen: String): NetworkResponse<List<InfoPayment>> {
        val response = api.getInfoPayment("thanhtoan","layqrcodethanhtoan",money.replace(".0",""),oderKey,authen)
        return if (response.isSuccessful) {
            NetworkResponse.Success(response.body() ?: emptyList())
        } else {
            NetworkResponse.Error("Lỗi: ${response.message()}")
        }
    }

    //    suspend fun checkOder(ids:String) = apiService.checkOder("checkgiavakm", "checkgiavakm",ids, authen)
    override suspend fun getUpdateOder( idCustomer: String,idOder: String,ghichu: String,authen: String): NetworkResponse<List<InfoPayment>> {
        val response = api.getUpdateOder("update_khachhang_dondathang","update",idCustomer,idOder,ghichu,authen)
        return if (response.isSuccessful) {
            NetworkResponse.Success(response.body() ?: emptyList())
        } else {
            NetworkResponse.Error("Lỗi: ${response.message()}")
        }
    }

    override suspend fun getInFoCustomerByPhone(phone: String, authen: String): NetworkResponse<List<InfoPayment>> {
        val response = api.getInFoCustomerByPhone("khachhang","laythongtinlhkhtheosdt",phone,authen)
        return if (response.isSuccessful) {
            NetworkResponse.Success(response.body() ?: emptyList())
        } else {
            NetworkResponse.Error("Lỗi: ${response.message()}")
        }
    }

    override suspend fun checkEmail(email: String, authen: String): NetworkResponse<List<InfoPayment>> {
        val response = api.checkEmail("khachhang","checkemail",email,"0",authen)
        return if (response.isSuccessful) {
            NetworkResponse.Success(response.body() ?: emptyList())
        } else {
            NetworkResponse.Error("Lỗi: ${response.message()}")
        }
    }





}
