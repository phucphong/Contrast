package com.itechpro.data.repository







import com.itechpro.data.api.CategoryAffiliateAPI
import com.itechpro.data.api.NewsAPI
import com.itechpro.data.api.VideoAPI
import com.itechpro.domain.model.Category
import com.itechpro.domain.model.News
import com.itechpro.domain.model.NetworkResponse
import com.itechpro.domain.model.Product
import com.itechpro.domain.model.Video
import com.itechpro.domain.repository.CategoryAffiliateRepository
import com.itechpro.domain.repository.NewsRepository
import com.itechpro.domain.repository.VideoRepository
import javax.inject.Inject

class CategoryAffiliateRepositoryImpl @Inject constructor(
    private val api: CategoryAffiliateAPI
) : CategoryAffiliateRepository {



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

    override suspend fun getProductsByIdParent(type: String,searchKey: String, authen: String): NetworkResponse<List<Product>> {
        val response = api.getProductsByIdParent("laysanphamtheonhom","laysanphamtheonhom","",type, "0",searchKey,authen)
        return if (response.isSuccessful) {
            NetworkResponse.Success(response.body() ?: emptyList())
        } else {
            NetworkResponse.Error("Lỗi: ${response.message()}")
        }
    }

    override suspend fun getProductsByIdParentOff(type: String,searchKey: String): NetworkResponse<List<Product>> {
        val response = api.getProductsByIdParentOff("laysanphamtheonhom","laysanphamtheonhom","",type,"0",searchKey)
        return if (response.isSuccessful) {
            NetworkResponse.Success(response.body() ?: emptyList())
        } else {
            NetworkResponse.Error("Lỗi: ${response.message()}")
        }
    }




}
