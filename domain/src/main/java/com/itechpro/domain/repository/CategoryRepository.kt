package com.itechpro.domain.repository


import com.itechpro.domain.model.Category
import com.itechpro.domain.model.NetworkResponse


interface CategoryRepository {


    suspend fun getCategory(
        endpoint: String?,
        obj: String?,
        mode: String?,
        key: String?,

        authen: String?
    ): NetworkResponse<List<Category>>



}