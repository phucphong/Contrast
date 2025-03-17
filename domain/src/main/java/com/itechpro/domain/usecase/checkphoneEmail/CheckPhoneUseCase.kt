package com.itechpro.domain.usecase.checkphoneEmail

import com.itechpro.domain.model.Account
import com.itechpro.domain.model.Column1
import com.itechpro.domain.model.NetworkResponse
import com.itechpro.domain.repository.AccountRepository
import com.itechpro.domain.repository.RegisterAccountRepository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.Dispatchers

class CheckPhoneUseCase(
    private val repository: RegisterAccountRepository
) {
    fun execute(
        obj: String?,
        mode: String?,
        phone: String?,
        idEmployee: String?,
        authen: String?,
        type: String?
    ): Flow<NetworkResponse<Boolean>> = flow {
        emit(NetworkResponse.Loading)

        val response = when (type) {
            "on" -> repository.checkPhone(obj, mode, phone, idEmployee, authen)
            "off" -> repository.checkPhoneOff(obj, mode, phone, idEmployee, authen)
            else -> NetworkResponse.Error("Loại kiểm tra không hợp lệ")
        }

        when (response) {
            is NetworkResponse.Success -> {
                val listData = response.data
                val column1 = listData.getOrNull(0)?.Column1 ?: "0"
                val column2 = listData.getOrNull(0)?.Column2 ?: "0"

                // Kiểm tra xem số điện thoại đã đăng ký hay chưa
                val isPhoneRegistered = column1.toInt() > 0 || column2.toInt() > 0
                emit(NetworkResponse.Success(isPhoneRegistered))
            }
            is NetworkResponse.Error -> emit(NetworkResponse.Error(response.message))
            is NetworkResponse.Loading -> {} // Không làm gì cả
        }
    }.flowOn(Dispatchers.IO)
}
