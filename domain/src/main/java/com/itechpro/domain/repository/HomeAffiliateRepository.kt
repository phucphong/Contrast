package com.itechpro.domain.repository





import com.itechpro.domain.model.Category
import com.itechpro.domain.model.NetworkResponse
import com.itechpro.domain.model.News
import com.itechpro.domain.model.Product
import com.itechpro.domain.model.Rotation
import com.itechpro.domain.model.SliderHome
import com.itechpro.domain.model.Video

interface HomeAffiliateRepository {



    suspend fun getSlideHome(obj: String,mode: String,authen: String
    ): NetworkResponse<List<SliderHome>>

    suspend fun getSlideHomeOff(obj: String,mode: String
    ): NetworkResponse<List<SliderHome>>


    suspend fun getFlashSale(authen: String
    ): NetworkResponse<List<Product>>

    suspend fun getFlashSaleOff(
    ): NetworkResponse<List<Product>>

    suspend fun getCategory(obj: String,mode: String,type: String,idParent: String,authen: String
    ): NetworkResponse<List<Category>>

    suspend fun getCategoryOff(obj: String,mode: String,type: String,idParent: String
    ): NetworkResponse<List<Category>>

    suspend fun getProductsByIdParent(type: String,searchKey: String, authen: String): NetworkResponse<List<Product>>

    suspend fun getProductsByIdParentOff(type: String,searchKey: String): NetworkResponse<List<Product>>
    suspend fun getRotation(type: String,authen:String): NetworkResponse<List<Rotation>>




}
