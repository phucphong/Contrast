package com.itechpro.domain.usecase.customer


import com.itechpro.domain.model.Account
import com.itechpro.domain.model.Customer
import com.itechpro.domain.model.NetworkResponse
import com.itechpro.domain.repository.CustomerRepository
import com.itechpro.domain.repository.RegisterAccountRepository

class CustomerUseCase(
    private val repository: CustomerRepository
) {

    suspend operator fun invoke(url: String,authen: String, account: Customer): NetworkResponse<List<Customer>> {
        return repository.addEditCustomer(url,authen, account,)
    }
}
