package com.itechpro.domain.usecase.video

import android.util.Log
import com.itechpro.domain.model.category.Category
import com.itechpro.domain.model.network.NetworkResponse

import com.itechpro.domain.model.video.Video
import com.itechpro.domain.repository.VideoRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class VideoUseCase @Inject constructor(
    private val repository: VideoRepository,

    ) {

    fun searchLocal(textSearch: String, list: List<Video>): Flow<List<Video>> = flow {
        val keyword = textSearch.trim().lowercase()

        val filtered = if (keyword.isBlank()) {
            list
        } else {
            list.filter {
                (it.ten ?: "").lowercase().contains(keyword) ||
                        (it.loai ?: "").lowercase().contains(keyword)
            }
        }

        emit(filtered)
    }.flowOn(Dispatchers.Default)


    fun getVideos( idCategory: String, authen: String): Flow<NetworkResponse<List<Video>>> {
        return flow {
            emit(NetworkResponse.Loading)
            Log.e("authen",authen)

            val result = if (authen.isEmpty()) {
                repository.getVideosOff(idCategory)
            } else {
                repository.getVideos(idCategory, authen)
            }

            emit(result)
        }.flowOn(Dispatchers.IO)
    }

    fun getCategory(obj: String,mode: String,  authen: String): Flow<NetworkResponse<List<Category>>> {
        return flow {
            emit(NetworkResponse.Loading)
            val result = if (authen.isEmpty()) {
                repository.getCategoryOff(obj,mode)
            } else {
                repository.getCategory( obj,mode,authen)
            }

            emit(result)
        }.flowOn(Dispatchers.IO)
    }

}
