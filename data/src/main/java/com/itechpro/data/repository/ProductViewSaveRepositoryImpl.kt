package com.itechpro.data.repository





import com.itechpro.data.api.ProductViewSaveAPI
import com.itechpro.domain.model.income.Income

import com.itechpro.domain.model.network.NetworkResponse
import com.itechpro.domain.model.product.Product

import com.itechpro.domain.repository.ProductViewSaveRepository


import javax.inject.Inject


class ProductViewSaveRepositoryImpl @Inject constructor(
    private val api: ProductViewSaveAPI
) : ProductViewSaveRepository {


    override suspend fun getLikeView(obj: String,mode: String,typeAccount: String,authen: String): NetworkResponse<List<Product>> {
        val response = api.getLikeView(obj,mode,typeAccount,authen)
        return if (response.isSuccessful) {
            NetworkResponse.Success(response.body() ?: emptyList())
        } else {
            NetworkResponse.Error("Lỗi: ${response.message()}")
        }
    }



}
