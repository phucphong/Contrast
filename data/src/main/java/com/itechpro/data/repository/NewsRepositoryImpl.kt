package com.itechpro.data.repository




import com.itechpro.data.api.NewsAPI
import com.itechpro.domain.model.Category
import com.itechpro.domain.model.News
import com.itechpro.domain.model.NetworkResponse
import com.itechpro.domain.repository.NewsRepository
import javax.inject.Inject

class NewsRepositoryImpl @Inject constructor(
    private val api: NewsAPI
) : NewsRepository {



    override suspend fun getCategory( authen: String): NetworkResponse<List<Category>> {
        val response = api.getCategory("tintuc","modedschudetintuc", authen)
        return if (response.isSuccessful) {
            NetworkResponse.Success(response.body() ?: emptyList())
        } else {
            NetworkResponse.Error("Lỗi: ${response.message()}")
        }
    }

    override suspend fun getCategoryOff(): NetworkResponse<List<Category>> {
        val response = api.getCategoryOff("tintuc","modedschudetintuc")
        return if (response.isSuccessful) {
            NetworkResponse.Success(response.body() ?: emptyList())
        } else {
            NetworkResponse.Error("Lỗi: ${response.message()}")
        }
    }

    override suspend fun getNews(idCategory: String, authen: String): NetworkResponse<List<News>> {
        val response = api.getNews("tintuc","modedstintuc",idCategory, authen)
        return if (response.isSuccessful) {
            NetworkResponse.Success(response.body() ?: emptyList())
        } else {
            NetworkResponse.Error("Lỗi: ${response.message()}")
        }
    }

    override suspend fun getNewsOff(idCategory: String): NetworkResponse<List<News>> {
        val response = api.getNewsOff("tintuc","modedstintuc",idCategory)
        return if (response.isSuccessful) {
            NetworkResponse.Success(response.body() ?: emptyList())
        } else {
            NetworkResponse.Error("Lỗi: ${response.message()}")
        }
    }

}
