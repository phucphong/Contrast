package com.itechpro.domain.usecase.customer
import com.itechpro.domain.model.NetworkResponse
import com.itechpro.domain.repository.CustomerRepository
import com.itechpro.domain.repository.RegisterAccountRepository



import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

class CheckEmailUseCase @Inject constructor(
    private val repository: CustomerRepository
) {
    fun execute(
        obj: String?,
        mode: String?,
        email: String?,
        ido: String?,
        authen: String?,
    ): Flow<NetworkResponse<Boolean>> = flow {
        emit(NetworkResponse.Loading)

        val response = repository.checkEmail(obj, mode, email, ido, authen)

        when (response) {
            is NetworkResponse.Success -> {
                val listData = response.data
                val column1 = listData.getOrNull(0)?.trangthai ?: "0"


                // Kiểm tra xem số điện thoại đã đăng ký hay chưa
                val isPhoneRegistered = column1.toInt() > 0
                emit(NetworkResponse.Success(isPhoneRegistered))
            }
            is NetworkResponse.Error -> emit(NetworkResponse.Error(response.message))
            is NetworkResponse.Loading -> {} // Không làm gì cả
        }
    }.flowOn(Dispatchers.IO)
}


