package com.itechpro.domain.usecase.oder

import com.itechpro.domain.model.network.NetworkResponse
import com.itechpro.domain.model.order.Order
import com.itechpro.domain.model.order.OrderResult
import com.itechpro.domain.model.order.OrderTable


import com.itechpro.domain.repository.oder.OderDetailRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class OderDetailUserCase @Inject constructor(
    private val repository: OderDetailRepository,

    ) {


    fun getOderDetail(
        obj: String,  mode: String, ido: String, authen: String
    ): Flow<NetworkResponse<OrderResult?>> {
        return flow {
            emit(NetworkResponse.Loading)
            when (val result = repository.getOderDetail(obj,mode, ido, authen)) {


                is NetworkResponse.Success -> {
                    val items = result.data.Table
                    val oderInfo = result.data.Table1.firstOrNull()

                    emit(NetworkResponse.Success(OrderResult(items = items, oderInfo =oderInfo  )))
                }

                is NetworkResponse.Error -> {
                    emit(NetworkResponse.Error(result.message))
                }

                else -> Unit
            }
        }.flowOn(Dispatchers.IO)
    }






}
