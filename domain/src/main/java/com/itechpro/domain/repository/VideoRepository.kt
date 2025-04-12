package com.itechpro.domain.repository



import com.itechpro.domain.model.Category
import com.itechpro.domain.model.NetworkResponse
import com.itechpro.domain.model.News
import com.itechpro.domain.model.Video

interface VideoRepository {


    suspend fun getVideos(
        idCategory: String,
        authen: String
    ): NetworkResponse<List<Video>>

    suspend fun getVideosOff(
        idCategory: String,
    ): NetworkResponse<List<Video>>

    suspend fun getCategory(obj: String,mode: String,
        authen: String
    ): NetworkResponse<List<Category>>



    suspend fun getCategoryOff(
        obj: String,mode: String,
    ): NetworkResponse<List<Category>>



}
