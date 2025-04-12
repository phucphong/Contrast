package com.itechpro.domain.repository




import com.itechpro.domain.model.Category
import com.itechpro.domain.model.NetworkResponse
import com.itechpro.domain.model.News
import com.itechpro.domain.model.Product
import com.itechpro.domain.model.Video

interface CategoryAffiliateRepository {



    suspend fun getCategory(obj: String,mode: String,type: String,idParent: String,authen: String
    ): NetworkResponse<List<Category>>

    suspend fun getCategoryOff(obj: String,mode: String,type: String,idParent: String
    ): NetworkResponse<List<Category>>

    suspend fun getProductsByIdParent(type: String,searchKey: String, authen: String): NetworkResponse<List<Product>>

    suspend fun getProductsByIdParentOff(type: String,searchKey: String): NetworkResponse<List<Product>>




}
