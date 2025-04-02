package com.itechpro.domain.usecase.category


import com.itechpro.domain.enumApp.CategoryType
import com.itechpro.domain.model.Account
import com.itechpro.domain.model.Category
import com.itechpro.domain.model.Column1
import com.itechpro.domain.model.NetworkResponse
import com.itechpro.domain.repository.AccountRepository
import com.itechpro.domain.repository.CategoryRepository
import com.itechpro.domain.repository.RegisterAccountRepository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.Dispatchers
class CategoryUseCase(
    private val repository: CategoryRepository
) {
    fun getCategory(
        type: CategoryType,
        key: String?,
        authen: String?
    ): Flow<NetworkResponse<List<Category>>> = flow {
        emit(NetworkResponse.Loading)

        val response = repository.getCategory(
            endpoint = type.endpoint,
            obj = type.obj,
            mode = type.mode,
            key = key,
            authen = authen
        )

        when (response) {
            is NetworkResponse.Success -> {
                val listData = response.data.map { raw ->
                    Category(
                        id = type.extractId(raw) ?: "0",
                        name = type.extractName(raw) ?: "",

                    )
                }
                emit(NetworkResponse.Success(listData))
            }

            is NetworkResponse.Error -> emit(NetworkResponse.Error(response.message))
            is NetworkResponse.Loading -> {} // No-op
        }
    }.flowOn(Dispatchers.IO)
}
