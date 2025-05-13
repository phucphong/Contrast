package com.itechpro.domain.usecase.news

import com.itechpro.domain.model.Video
import com.itechpro.domain.model.category.Category
import com.itechpro.domain.model.network.NetworkResponse
import com.itechpro.domain.model.news.News
import com.itechpro.domain.repository.NewsRepository
import com.itechpro.domain.safeFlowCall
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class NewsUseCase @Inject constructor(
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

    fun searchLocal(textSearch: String, list: List<News>): Flow<List<News>> = flow {
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


    fun getNewDetail(
        offline: Boolean,
        ido: String,
        authen: String
    ): Flow<NetworkResponse<News>> = safeFlowCall {

        val response = if (offline) {
            repository.getNewDetailOff( ido)
        } else {
            repository.getNewDetail( ido, authen)
        }

        when (response) {
            is NetworkResponse.Success -> {
                val customer = response.data.firstOrNull()
                customer?.let { NetworkResponse.Success(it) }
                    ?: NetworkResponse.Error("Không tìm thấy dữ liệu")
            }

            is NetworkResponse.Error -> NetworkResponse.Error(response.message)
            is NetworkResponse.Loading -> NetworkResponse.Loading
        }
    }

}
