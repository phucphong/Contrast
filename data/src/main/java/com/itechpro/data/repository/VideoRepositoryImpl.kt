package com.itechpro.data.repository





import com.itechpro.data.api.NewsAPI
import com.itechpro.data.api.VideoAPI
import com.itechpro.domain.model.Category
import com.itechpro.domain.model.News
import com.itechpro.domain.model.NetworkResponse
import com.itechpro.domain.model.Video
import com.itechpro.domain.repository.NewsRepository
import com.itechpro.domain.repository.VideoRepository
import javax.inject.Inject

class VideoRepositoryImpl @Inject constructor(
    private val api: VideoAPI
) : VideoRepository {



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

    override suspend fun getVideos(idCategory: String, authen: String): NetworkResponse<List<Video>> {
        val response = api.getVideos("tintuc","modedstintuc",idCategory, authen)
        return if (response.isSuccessful) {
            NetworkResponse.Success(response.body() ?: emptyList())
        } else {
            NetworkResponse.Error("Lỗi: ${response.message()}")
        }
    }

    override suspend fun getVideosOff(idCategory: String): NetworkResponse<List<Video>> {
        val response = api.getVideosOff("tintuc","modedstintuc",idCategory)
        return if (response.isSuccessful) {
            NetworkResponse.Success(response.body() ?: emptyList())
        } else {
            NetworkResponse.Error("Lỗi: ${response.message()}")
        }
    }




}
