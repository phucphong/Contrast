package com.itechpro.domain.usecase.customer


import com.itechpro.domain.enumApp.CategoryType
import com.itechpro.domain.model.Account
import com.itechpro.domain.model.Category
import com.itechpro.domain.model.Customer
import com.itechpro.domain.model.NetworkResponse
import com.itechpro.domain.repository.CustomerRepository
import com.itechpro.domain.repository.RegisterAccountRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class CustomerUseCase @Inject constructor(
    private val repository: CustomerRepository
) {

    suspend operator fun invoke(url: String, account: Customer,authen: String,): NetworkResponse<List<Customer>> {
        return repository.addEditCustomer(url, account,authen)
    }


    fun customerDetail(
        ido: String?,
        authen: String?
    ): Flow<NetworkResponse<Customer>> = flow {
        emit(NetworkResponse.Loading)

        val response = repository.customerDetail("khachhang","getbyid",ido,authen)

        when (response) {
            is NetworkResponse.Success -> {
                val customer = response.data.firstOrNull()
                if (customer != null) {
                    emit(NetworkResponse.Success(customer))
                } else {
                    emit(NetworkResponse.Error("Không có dữ liệu chi tiết khách hàng"))
                }
            }

            is NetworkResponse.Error -> emit(NetworkResponse.Error(response.message))
            is NetworkResponse.Loading -> {} // No-op
        }

    }.flowOn(Dispatchers.IO)

    fun loadExchangeData(
        ido: String?,
        authen: String?
    ): Flow<NetworkResponse<List<Customer>>> = flow {
        emit(NetworkResponse.Loading)

        val response = repository.loadExchangeData("khachhang","gettraodoi",ido,authen)

        when (response) {
            is NetworkResponse.Success -> {
                val customer = response.data
                if (customer != null) {
                    emit(NetworkResponse.Success(customer))
                } else {
                    emit(NetworkResponse.Error("Không có dữ liệu chi tiết khách hàng"))
                }
            }

            is NetworkResponse.Error -> emit(NetworkResponse.Error(response.message))
            is NetworkResponse.Loading -> {} // No-op
        }

    }.flowOn(Dispatchers.IO)

    fun loadTimelineData(
        ido: String?,
        authen: String?
    ): Flow<NetworkResponse<List<Customer>>> = flow {
        emit(NetworkResponse.Loading)

        val response = repository.loadTimelineData("khachhang","gettimeline",ido,authen)

        when (response) {
            is NetworkResponse.Success -> {
                val customer = response.data
                if (customer != null) {
                    emit(NetworkResponse.Success(customer))
                } else {
                    emit(NetworkResponse.Error("Không có dữ liệu chi tiết khách hàng"))
                }
            }

            is NetworkResponse.Error -> emit(NetworkResponse.Error(response.message))
            is NetworkResponse.Loading -> {} // No-op
        }

    }.flowOn(Dispatchers.IO)




}
