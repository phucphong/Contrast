package com.itechpro.domain.repository


import com.itechpro.domain.model.LikeProductService
import com.itechpro.domain.model.network.NetworkResponse
import com.itechpro.domain.model.product.Product
import com.itechpro.domain.model.product.ProductDetail
import com.itechpro.domain.model.report.ReportProduct


interface ProductRepository {

    suspend fun getInfoProduct(idProduct: String,idUnit: String,authen: String
    ): NetworkResponse<List<ProductDetail>>
    suspend fun getInfoProductOff(idProduct: String,idUnit: String
    ): NetworkResponse<List<ProductDetail
            >>

    suspend fun getUnLike(type: String,idProduct: String,idUnit: String,authen: String
    ): NetworkResponse<List<Product>>






    suspend fun getTypeReport(authen: String
    ): NetworkResponse<List<ReportProduct>>

    suspend fun getProductsByIdParent(type: String,searchKey: String, authen: String): NetworkResponse<List<Product>>

    suspend fun getProductsByIdParentOff(type: String,searchKey: String): NetworkResponse<List<Product>>



    suspend fun addEditLikeReport(url: String, obj: LikeProductService, authen: String): NetworkResponse<List<LikeProductService>>



}
