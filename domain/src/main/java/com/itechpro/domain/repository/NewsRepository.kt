package com.itechpro.domain.repository

import com.itechpro.domain.model.Category
import com.itechpro.domain.model.NetworkResponse
import com.itechpro.domain.model.News

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

    suspend fun getCategoryOff(

    ): NetworkResponse<List<Category>>



}
