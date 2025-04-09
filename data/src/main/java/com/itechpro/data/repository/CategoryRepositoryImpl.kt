package com.itechpro.data.repository





import com.itechpro.data.api.CategoryAPI
import com.itechpro.data.api.RegisterAccountAPI
import com.itechpro.domain.model.Account
import com.itechpro.domain.model.Category

import com.itechpro.domain.model.NetworkResponse
import com.itechpro.domain.repository.CategoryRepository

import javax.inject.Inject

class CategoryRepositoryImpl @Inject constructor(
    private val api: CategoryAPI
) : CategoryRepository {

    override suspend fun getCategory(
        endpoint: String?,
        obj: String?,
        mode: String?,
        key: String?,
        authen: String?
    ): NetworkResponse<List<Category>> {
        return try {
            val response = api.getCategory(endpoint,obj, mode,"0","", key, authen)
            if (response.isSuccessful) {
                response.body()?.let {
                    NetworkResponse.Success(it)
                } ?: NetworkResponse.Error("Dữ liệu rỗng")
            } else {
                NetworkResponse.Error("Lỗi: ${response.message()}")
            }
        } catch (e: Exception) {
            NetworkResponse.Error("Exception: ${e.message}")
        }
    }


}
