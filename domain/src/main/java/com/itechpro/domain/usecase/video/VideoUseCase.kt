package com.itechpro.domain.usecase.video

import com.itechpro.domain.model.Category
import com.itechpro.domain.model.NetworkResponse
import com.itechpro.domain.model.News
import com.itechpro.domain.repository.NewsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class VideoUseCase @Inject constructor(
    private val repository: NewsRepository,

    ) {


    fun getNews(offline: Boolean, idCategory: String, authen: String): Flow<NetworkResponse<List<News>>> {
        return flow {
            emit(NetworkResponse.Loading)

            val result = if (offline) {
                repository.getNewsOff(idCategory)
            } else {
                repository.getNews(idCategory, authen)
            }

            emit(result)
        }.flowOn(Dispatchers.IO)
    }

    fun getCategory(offline: Boolean,  authen: String): Flow<NetworkResponse<List<Category>>> {
        return flow {
            emit(NetworkResponse.Loading)

            val result = if (offline) {
                repository.getCategoryOff()
            } else {
                repository.getCategory( authen)
            }

            emit(result)
        }.flowOn(Dispatchers.IO)
    }

}
