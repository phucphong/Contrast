package com.itechpro.data.repository





import com.itechpro.data.api.VideoAPI
import com.itechpro.domain.model.category.Category
import com.itechpro.domain.model.network.NetworkResponse
import com.itechpro.domain.model.video.Video
import com.itechpro.domain.repository.VideoRepository
import javax.inject.Inject

class VideoRepositoryImpl @Inject constructor(
    private val api: VideoAPI
) : VideoRepository {



    override suspend fun getCategory(obj: String,mode: String,authen: String): NetworkResponse<List<Category>> {
        val response = api.getCategory(obj,mode, authen)
        return if (response.isSuccessful) {
            NetworkResponse.Success(response.body() ?: emptyList())
        } else {
            NetworkResponse.Error("Lỗi: ${response.message()}")
        }
    }


    override suspend fun getCategoryOff(obj: String,mode: String): NetworkResponse<List<Category>> {
        val response = api.getCategoryOff(obj,mode)
        return if (response.isSuccessful) {
            NetworkResponse.Success(response.body() ?: emptyList())
        } else {
            NetworkResponse.Error("Lỗi: ${response.message()}")
        }
    }

    override suspend fun getVideos(idCategory: String, authen: String): NetworkResponse<List<Video>> {
        val response = api.getVideos("nhomvideo","tatcavideobyidnhom",idCategory, authen)
        return if (response.isSuccessful) {
            NetworkResponse.Success(response.body() ?: emptyList())
        } else {
            NetworkResponse.Error("Lỗi: ${response.message()}")
        }
    }

    override suspend fun getVideosOff(idCategory: String): NetworkResponse<List<Video>> {
        val response = api.getVideosOff("nhomvideo","tatcavideobyidnhom",idCategory)
        return if (response.isSuccessful) {
            NetworkResponse.Success(response.body() ?: emptyList())
        } else {
            NetworkResponse.Error("Lỗi: ${response.message()}")
        }
    }




}
