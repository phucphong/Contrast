package com.itechpro.domain.usecase.oder







import com.itechpro.domain.model.network.NetworkResponse
import com.itechpro.domain.model.order.Order

import com.itechpro.domain.repository.oder.OderRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class OdersUserCase @Inject constructor(
    private val repository: OderRepository,

    ) {



    fun getOderByConfirm(
       startDate: String, endDate: String,confirm: String, typeAccount: String,authen: String
    ): Flow<NetworkResponse<List<Order>>> = flow {
        emit(NetworkResponse.Loading)

        val result = repository.getOderByConfirm("dailyaf","danhsachdonhang", startDate,  endDate,confirm,typeAccount,authen)

        val finalResult = when (result) {
            is NetworkResponse.Success -> {
                NetworkResponse.Success(result.data)
            }
            is NetworkResponse.Error -> NetworkResponse.Error(result.message)

            else -> result
        }

        emit(finalResult)
    }.flowOn(Dispatchers.IO)



    fun getOderByTypeAccount(
       startDate: String, endDate: String,customerId: String, typeAccount: String,authen: String
    ): Flow<NetworkResponse<List<Order>>> = flow {
        emit(NetworkResponse.Loading)

        val result = repository.getOderByTypeAccount("laydsdonbanhang","modelaydsdonhangcuakhachhang", startDate,  endDate,customerId,typeAccount,authen)

        val finalResult = when (result) {
            is NetworkResponse.Success -> {
                NetworkResponse.Success(result.data)
            }
            is NetworkResponse.Error -> NetworkResponse.Error(result.message)

            else -> result
        }

        emit(finalResult)
    }.flowOn(Dispatchers.IO)



}
