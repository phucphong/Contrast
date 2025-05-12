package com.itechpro.domain.repository

import com.itechpro.domain.model.category.Category
import com.itechpro.domain.model.network.NetworkResponse
import com.itechpro.domain.model.news.News

interface NewsRepository {


    suspend fun getNews(
        idCategory: String,
        authen: String
    ): NetworkResponse<List<News>>

    suspend fun getNewsOff(
        idCategory: String,
    ): NetworkResponse<List<News>>

    suspend fun getCategory(
        authen: String
    ): NetworkResponse<List<Category>>

    suspend fun getNewDetail(
        obj: String,
        mode: String,
        ido: String,
        authen: String
    ): NetworkResponse<List<News>>

    suspend fun getCategoryOff(

    ): NetworkResponse<List<Category>>



}
