package com.itechpro.domain.usecase.customer


import com.itechpro.domain.enumApp.CategoryType
import com.itechpro.domain.model.Category
import com.itechpro.domain.model.Customer
import com.itechpro.domain.model.NetworkResponse
import com.itechpro.domain.model.News
import com.itechpro.domain.repository.CustomerRepository
import com.itechpro.domain.safeFlowCall
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class CustomerUseCase @Inject constructor(
    private val repository: CustomerRepository
) {

    suspend operator fun invoke(
        url: String,
        account: Customer,
        authen: String
    ): NetworkResponse<List<Customer>> {
        return repository.addEditCustomer(url, account, authen)
    }

    fun getCustomerDetail(
        ido: String,
        authen: String
    ): Flow<NetworkResponse<Customer>> = safeFlowCall {
        val response = repository.getCustomerDetail("khachhang", "getbyid", ido, authen)
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

    fun getTimelineData(
        ido: String,
        authen: String
    ): Flow<NetworkResponse<List<Customer>>> = safeFlowCall {
        repository.getTimelineData("khachhang", "gettimeline", ido, authen)
    }

    fun getExchangeData(
        ido: String,
        authen: String
    ): Flow<NetworkResponse<List<Customer>>> = safeFlowCall {
        repository.getExchangeData("khachhang", "gettraodoi", ido, authen)
    }

    fun getCustomerCardInformation(
        idCustomer: String,
        idOder: String,
        dateOder: String,
        authen: String
    ): Flow<NetworkResponse<List<Customer>>> = safeFlowCall {
        repository.getCustomerCardInformation(
            obj = "khachhang",
            mode = "laythongtinhangthekh",
            idCustomer = idCustomer,
            idOder = idOder,
            dateOder = dateOder,
            authen = authen
        )
    }
}
