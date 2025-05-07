package com.itechpro.domain.usecase.payment

import com.itechpro.domain.model.NetworkResponse
import com.itechpro.domain.model.cart.CartItem
import com.itechpro.domain.model.payment.InfoPayment
import com.itechpro.domain.repository.PaymentRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class PaymentUseCase @Inject constructor(
    private val repository: PaymentRepository,

    ) {



//money: String,oderKey: String,authen: String




    fun getInfoPayment(money: String,oderKey: String, authen: String):  Flow<NetworkResponse<List<InfoPayment>>>  {
        return flow {
            emit(NetworkResponse.Loading)
            when (val result = repository.getInfoPayment(money,oderKey, authen)) {
                is NetworkResponse.Success -> {
                    emit(NetworkResponse.Success(result.data))
                }
                is NetworkResponse.Error -> {
                    emit(NetworkResponse.Error(result.message))
                }

                else -> Unit
            }
        }.flowOn(Dispatchers.IO)
    }

    fun getUpdateOder(idCustomer: String,idOder: String, ghichu: String, authen: String):  Flow<NetworkResponse<List<InfoPayment>>>  {
        return flow {
            emit(NetworkResponse.Loading)
            when (val result = repository.getUpdateOder(idCustomer,idOder, ghichu,authen)) {
                is NetworkResponse.Success -> {
                    emit(NetworkResponse.Success(result.data))
                }
                is NetworkResponse.Error -> {
                    emit(NetworkResponse.Error(result.message))
                }

                else -> Unit
            }
        }.flowOn(Dispatchers.IO)
    }



    fun getInFoCustomerByPhone(phone: String,authen: String): Flow<NetworkResponse<String>> {
        return flow {
            emit(NetworkResponse.Loading)
            when (val result = repository.getInFoCustomerByPhone(phone ,authen)) {
                is NetworkResponse.Success -> {

                    val status = result.data.firstOrNull()?.idkhachhang ?: ""
                    emit(NetworkResponse.Success(status))
                }
                is NetworkResponse.Error -> {
                    emit(NetworkResponse.Error(result.message))
                }

                NetworkResponse.Loading -> {}
            }
        }.flowOn(Dispatchers.IO)
    }


    fun checkEmail(phone: String,authen: String): Flow<NetworkResponse<String>> {
        return flow {
            emit(NetworkResponse.Loading)
            when (val result = repository.checkEmail(phone ,authen)) {
                is NetworkResponse.Success -> {

                    val status = result.data.firstOrNull()?.trangthai ?: "0"
                    emit(NetworkResponse.Success(status))
                }
                is NetworkResponse.Error -> {
                    emit(NetworkResponse.Error(result.message))
                }

                NetworkResponse.Loading -> {}
            }
        }.flowOn(Dispatchers.IO)
    }









}
