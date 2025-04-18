package com.itechpro.domain.usecase.profile




import com.itechpro.domain.model.Account
import com.itechpro.domain.model.Category
import com.itechpro.domain.model.NetworkResponse

import com.itechpro.domain.repository.ProfileRepository
import com.itechpro.domain.safeFlowCall
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class ProfileUseCase @Inject constructor(
    private val repository: ProfileRepository,

    ) {


    fun getMenuApp( authen: String): Flow<NetworkResponse<List<Category>>> {
        return flow {
            emit(NetworkResponse.Loading)

            val result = repository.getMenuApp("khachhang", authen)

            emit(result)
        }.flowOn(Dispatchers.IO)
    }




    fun getQrCodeEmployee(
        ido: String,
        authen: String
    ): Flow<NetworkResponse<Account>> = safeFlowCall {
        val response = repository.getQrCodeEmployee("layqrcode", "modelayqrcodenhanvien", authen)
        when (response) {
            is NetworkResponse.Success -> {
                val customer = response.data.firstOrNull()
                customer?.let { NetworkResponse.Success(it) }
                    ?: NetworkResponse.Error("Không tìm thấy dữ liệu khách hàng")
            }

            is NetworkResponse.Error -> NetworkResponse.Error(response.message)
            is NetworkResponse.Loading -> NetworkResponse.Loading
        }
    }

}
