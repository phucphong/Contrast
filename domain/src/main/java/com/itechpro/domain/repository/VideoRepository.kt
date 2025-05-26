package com.itechpro.domain.repository


import com.itechpro.domain.model.category.Category
import com.itechpro.domain.model.network.NetworkResponse
import com.itechpro.domain.model.video.Video

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
