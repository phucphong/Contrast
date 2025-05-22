package com.itechpro.domain.repository




import com.itechpro.domain.model.income.Income
import com.itechpro.domain.model.network.NetworkResponse
import com.itechpro.domain.model.product.Product


interface ProductViewSaveRepository {


    suspend fun getLikeView(obj: String,mode: String,typeAccount: String,authen: String): NetworkResponse<List<Product>>




}
