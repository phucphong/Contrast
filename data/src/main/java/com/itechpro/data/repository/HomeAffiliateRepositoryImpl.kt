package com.itechpro.data.repository



import com.itechpro.data.api.HomeAffiliateAPI
import com.itechpro.domain.model.category.Category

import com.itechpro.domain.model.network.NetworkResponse
import com.itechpro.domain.model.product.Product
import com.itechpro.domain.model.Rotation
import com.itechpro.domain.model.home.SliderHome

import com.itechpro.domain.repository.HomeAffiliateRepository


import javax.inject.Inject


class HomeAffiliateRepositoryImpl @Inject constructor(
    private val api: HomeAffiliateAPI
) : HomeAffiliateRepository {


    override suspend fun getSlideHome(obj: String,mode: String,authen: String): NetworkResponse<List<SliderHome>> {
        val response = api.getSlideHome(obj,mode,authen)
        return if (response.isSuccessful) {
            NetworkResponse.Success(response.body() ?: emptyList())
        } else {
            NetworkResponse.Error("Lỗi: ${response.message()}")
        }
    }


    override suspend fun getSlideHomeOff(obj: String,mode: String): NetworkResponse<List<SliderHome>> {
        val response = api.getSlideHomeOff(obj,mode)
        return if (response.isSuccessful) {
            NetworkResponse.Success(response.body() ?: emptyList())
        } else {
            NetworkResponse.Error("Lỗi: ${response.message()}")
        }
    }




    override suspend fun getCategory(obj: String,mode: String,type: String,idParent: String,authen: String): NetworkResponse<List<Category>> {
        val response = api.getCategory(obj,mode, type, idParent,authen)
        return if (response.isSuccessful) {
            NetworkResponse.Success(response.body() ?: emptyList())
        } else {
            NetworkResponse.Error("Lỗi: ${response.message()}")
        }
    }


    override suspend fun getCategoryOff(obj: String,mode: String,type: String,idParent: String): NetworkResponse<List<Category>> {
        val response = api.getCategoryOff(obj,mode,type,idParent)
        return if (response.isSuccessful) {
            NetworkResponse.Success(response.body() ?: emptyList())
        } else {
            NetworkResponse.Error("Lỗi: ${response.message()}")
        }
    }

    override suspend fun getFlashSaleOff(): NetworkResponse<List<Product>> {
        val response = api.getFlashSaleOff("tatcasp","modetatcaspkm")
        return if (response.isSuccessful) {
            NetworkResponse.Success(response.body() ?: emptyList())
        } else {
            NetworkResponse.Error("Lỗi: ${response.message()}")
        }
    }

    override suspend fun getFlashSale( authen: String): NetworkResponse<List<Product>> {
        val response = api.getFlashSale("tatcasp","modetatcaspkm",authen)
        return if (response.isSuccessful) {
            NetworkResponse.Success(response.body() ?: emptyList())
        } else {
            NetworkResponse.Error("Lỗi: ${response.message()}")
        }
    }
    override suspend fun getProductsByIdParent(type: String,idParent: String, authen: String): NetworkResponse<List<Product>> {
        val response = api.getProductsByIdParent("laysanphamtheonhom","laysanphamtheonhom","",type, idParent,"",authen)
        return if (response.isSuccessful) {
            NetworkResponse.Success(response.body() ?: emptyList())
        } else {
            NetworkResponse.Error("Lỗi: ${response.message()}")
        }

    }
    override suspend fun getProductsByIdParentOff(type: String,idParent: String): NetworkResponse<List<Product>> {
        val response = api.getProductsByIdParentOff("laysanphamtheonhom","laysanphamtheonhom","",type, idParent,"")
        return if (response.isSuccessful) {
            NetworkResponse.Success(response.body() ?: emptyList())
        } else {
            NetworkResponse.Error("Lỗi: ${response.message()}")
        }
    }

    override suspend fun getRotation(type: String,authen: String): NetworkResponse<List<Rotation>> {
        val response = api.getRotation("vongquaymayman","modelaygiaithuong",type,authen)
        return if (response.isSuccessful) {
            NetworkResponse.Success(response.body() ?: emptyList())
        } else {
            NetworkResponse.Error("Lỗi: ${response.message()}")
        }
    }




}
