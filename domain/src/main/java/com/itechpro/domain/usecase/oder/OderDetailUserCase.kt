package com.itechpro.domain.usecase.oder








import com.itechpro.domain.model.cart.CartResult
import com.itechpro.domain.model.network.NetworkResponse
import com.itechpro.domain.model.oder.OrderResult

import com.itechpro.domain.repository.OderDetailRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class OderDetailUserCase @Inject constructor(
    private val repository: OderDetailRepository,

    ) {




    fun getOderById(
        obj: String,  mode: String, ido: String, authen: String
    ): Flow<NetworkResponse<OrderResult?>> {
        return flow {
            emit(NetworkResponse.Loading)
            when (val result = repository.getOderById(obj,mode, ido, authen)) {


                is NetworkResponse.Success -> {
                    val items = result.data.table
                    val listInfo = result.data.table1

                    emit(
                        NetworkResponse.Success(
                            OrderResult(items,
                                oderInfo = listInfo[0])
                        ))

                }

                is NetworkResponse.Error -> {
                    emit(NetworkResponse.Error(result.message))
                }

                else -> Unit
            }
        }.flowOn(Dispatchers.IO)
    }






}
