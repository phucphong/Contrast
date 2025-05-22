package com.itechpro.domain.repository


import com.itechpro.domain.model.income.Income
import com.itechpro.domain.model.network.NetworkResponse
import com.itechpro.domain.model.product.Product


interface ShareProductInComeRepository {






    suspend fun getProductsByIdParent(type: String,searchKey: String, searchText: String, authen: String): NetworkResponse<List<Product>>
    suspend fun getReportShareLink(mode: String,startDate: String,endDate: String,authen: String): NetworkResponse<List<Product>>
    suspend fun getActualCommissionByIdOder(mode: String,startDate: String,endDate: String,authen: String): NetworkResponse<List<Income>>




}
