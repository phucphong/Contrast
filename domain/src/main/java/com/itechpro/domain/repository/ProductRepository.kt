package com.itechpro.domain.repository


import com.itechpro.domain.model.NetworkResponse
import com.itechpro.domain.model.Product
import com.itechpro.domain.model.product.ProductDetail


interface ProductRepository {

    suspend fun getInfoProduct(idProduct: String,idUnit: String,authen: String
    ): NetworkResponse<List<ProductDetail>>
    suspend fun getInfoProductOff(idProduct: String,idUnit: String
    ): NetworkResponse<List<ProductDetail
            >>

    suspend fun getUnLike(type: String,idProduct: String,idUnit: String,authen: String
    ): NetworkResponse<List<Product>>






    suspend fun getTypeReport(authen: String
    ): NetworkResponse<List<Product>>

    suspend fun getProductsByIdParent(type: String,searchKey: String, authen: String): NetworkResponse<List<Product>>

    suspend fun getProductsByIdParentOff(type: String,searchKey: String): NetworkResponse<List<Product>>



    suspend fun addEditLike(url: String, obj: Product, authen: String): NetworkResponse<List<Product>>



}
