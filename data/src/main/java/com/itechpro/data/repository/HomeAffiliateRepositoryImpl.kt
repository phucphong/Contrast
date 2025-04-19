package com.itechpro.data.repository



import android.util.Log
import com.itechpro.data.api.CategoryAffiliateAPI
import com.itechpro.data.api.HomeAffiliateAPI
import com.itechpro.domain.model.Category

import com.itechpro.domain.model.NetworkResponse
import com.itechpro.domain.model.Product
import com.itechpro.domain.model.Rotation
import com.itechpro.domain.model.SliderHome

import com.itechpro.domain.repository.CategoryAffiliateRepository
import com.itechpro.domain.repository.HomeAffiliateRepository
import com.squareup.moshi.KotlinJsonAdapterFactory
import com.squareup.moshi.Moshi


import javax.inject.Inject
import com.squareup.moshi.Types


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

            val rawJson = response.body()?.let {
                Moshi.Builder()
                    .add(KotlinJsonAdapterFactory())
                    .build()
                    .adapter(List::class.java)
                    .toJson(it)
            } ?: "[]"

            val moshi = Moshi.Builder()
                .add(KotlinJsonAdapterFactory())
                .build()

            val type = Types.newParameterizedType(List::class.java, Product::class.java)
            val adapter = moshi.adapter<List<Product>>(type)

            val start = System.currentTimeMillis()
            val parsedList = adapter.fromJson(rawJson).orEmpty()
            val duration = System.currentTimeMillis() - start

            Log.d("TimingMoshi", "✅ Moshi parse getProductsByIdParent took: ${duration}ms | items: ${parsedList.size}")
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
