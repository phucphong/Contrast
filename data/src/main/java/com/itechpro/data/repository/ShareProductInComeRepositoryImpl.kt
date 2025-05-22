package com.itechpro.data.repository




import com.itechpro.data.api.ShareProductInComeAPI
import com.itechpro.domain.model.income.Income

import com.itechpro.domain.model.network.NetworkResponse
import com.itechpro.domain.model.product.Product

import com.itechpro.domain.repository.ShareProductInComeRepository


import javax.inject.Inject


class ShareProductInComeRepositoryImpl @Inject constructor(
    private val api: ShareProductInComeAPI
) : ShareProductInComeRepository {








    override suspend fun getProductsByIdParent(type: String,idParent: String,searchText: String, authen: String): NetworkResponse<List<Product>> {
        val response = api.getProductsByIdParent("laysanphamtheonhom","laysanphamtheonhom","",type, idParent,searchText,authen)
        return if (response.isSuccessful) {
            NetworkResponse.Success(response.body() ?: emptyList())
        } else {
            NetworkResponse.Error("Lỗi: ${response.message()}")
        }

    }



    override suspend fun getReportShareLink(mode: String,startDate: String,endDate: String,authen: String): NetworkResponse<List<Product>> {
        val response = api.getReportShareLink("hoahongaff",mode,startDate,endDate,authen)
        return if (response.isSuccessful) {
            NetworkResponse.Success(response.body() ?: emptyList())
        } else {
            NetworkResponse.Error("Lỗi: ${response.message()}")
        }
    }



    override suspend fun getActualCommissionByIdOder(mode: String,startDate: String,endDate: String,authen: String): NetworkResponse<List<Income>> {
        val response = api.getActualCommissionByIdOder("hoahongaff",mode,startDate,endDate,authen)
        return if (response.isSuccessful) {
            NetworkResponse.Success(response.body() ?: emptyList())
        } else {
            NetworkResponse.Error("Lỗi: ${response.message()}")
        }
    }




}
