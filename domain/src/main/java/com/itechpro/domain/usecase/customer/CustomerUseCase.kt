package com.itechpro.domain.usecase.customer


import com.itechpro.domain.model.Account
import com.itechpro.domain.model.Customer
import com.itechpro.domain.model.NetworkResponse
import com.itechpro.domain.repository.CustomerRepository
import com.itechpro.domain.repository.RegisterAccountRepository
import javax.inject.Inject

class CustomerUseCase @Inject constructor(
    private val repository: CustomerRepository
) {

    suspend operator fun invoke(url: String, account: Customer,authen: String,): NetworkResponse<List<Customer>> {
        return repository.addEditCustomer(url, account,authen)
    }
}
