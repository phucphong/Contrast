package com.itechpro.domain.repository




import com.itechpro.domain.model.evaluate.Evaluate
import com.itechpro.domain.model.NetworkResponse
import com.itechpro.domain.model.Product


interface EvaluateRepository {




    suspend fun getEvaluates(idProduct: String,count: String,authen: String
    ): NetworkResponse<Evaluate>

    suspend fun getEvaluatesOff(idProduct: String,count: String,
    ): NetworkResponse<Evaluate>



    suspend fun addEditLike(url: String, obj: Product, authen: String): NetworkResponse<List<Product>>



}
