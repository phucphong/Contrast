package com.itechpro.data.repository





import com.itechpro.data.api.EvaluateAPI
import com.itechpro.data.api.ProductAPI
import com.itechpro.domain.model.evaluate.Evaluate

import com.itechpro.domain.model.NetworkResponse
import com.itechpro.domain.model.Product
import com.itechpro.domain.model.product.ProductDetail
import com.itechpro.domain.repository.EvaluateRepository

import com.itechpro.domain.repository.ProductRepository


import javax.inject.Inject


class EvaluateRepositoryImpl @Inject constructor(
    private val api: EvaluateAPI
) : EvaluateRepository {






    override suspend fun getEvaluatesOff(idProduct: String,count: String): NetworkResponse<Evaluate> {
        val response = api.getEvaluatesOff("laythongtin","laydsdanhgia",idProduct,count, )
        return if (response.isSuccessful) {
            val body = response.body()
            if (body != null) {
                NetworkResponse.Success(body)
            } else {
                NetworkResponse.Error("Cart data is null")
            }
        } else {
            NetworkResponse.Error("Lỗi: ${response.message()}")
        }
    }

    override suspend fun getEvaluates(idProduct: String,count: String, authen: String): NetworkResponse<Evaluate> {
        val response = api.getEvaluates("laythongtin","laydsdanhgia",idProduct,count, authen)
        return if (response.isSuccessful) {
            val body = response.body()
            if (body != null) {
                NetworkResponse.Success(body)
            } else {
                NetworkResponse.Error("Cart data is null")
            }
        } else {
            NetworkResponse.Error("Lỗi: ${response.message()}")
        }
    }



    override suspend fun addEditLike(url: String, obj: Product, authen: String): NetworkResponse<List<Product>> {
        return try {
            val response = api.addEditLike(url, obj,authen)
            if (response.isSuccessful) {
                NetworkResponse.Success(response.body() ?: emptyList())
            } else {
                NetworkResponse.Error("Lỗi: ${response.message()}")
            }
        } catch (e: Exception) {
            NetworkResponse.Error("Exception: ${e.message}")


        }
    }



}
