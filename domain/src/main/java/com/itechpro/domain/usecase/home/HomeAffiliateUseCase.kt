package com.itechpro.domain.usecase.home





import com.itechpro.domain.R
import com.itechpro.domain.model.Category
import com.itechpro.domain.model.NetworkResponse
import com.itechpro.domain.model.News
import com.itechpro.domain.model.Product
import com.itechpro.domain.model.Rotation
import com.itechpro.domain.model.SliderHome
import com.itechpro.domain.model.Video
import com.itechpro.domain.repository.CategoryAffiliateRepository
import com.itechpro.domain.repository.HomeAffiliateRepository
import com.itechpro.domain.repository.NewsRepository
import com.itechpro.domain.repository.VideoRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class HomeAffiliateUseCase @Inject constructor(
    private val repository: HomeAffiliateRepository,

    ) {


    fun getCategory(offline: Boolean, obj: String,mode: String,type: String,idParent: String,authen: String): Flow<NetworkResponse<List<Category>>> {
        return flow {
            emit(NetworkResponse.Loading)

            val result = if (offline) {
                repository.getCategoryOff(obj, mode,type,idParent)
            } else {
                repository.getCategory(obj, mode,type,idParent,authen)
            }

            emit(result)
        }.flowOn(Dispatchers.IO)
    }

    fun getSlideHome(offline: Boolean, obj: String,mode: String,authen: String): Flow<NetworkResponse<List<SliderHome>>> {
        return flow {
            emit(NetworkResponse.Loading)

            val result = if (offline) {
                repository.getSlideHomeOff(obj, mode)
            } else {
                repository.getSlideHome(obj, mode,authen)
            }

            emit(result)
        }.flowOn(Dispatchers.IO)
    }
    fun getFlashSale(offline: Boolean, authen: String): Flow<NetworkResponse<List<Product>>> {
        return flow {
            emit(NetworkResponse.Loading)

            val result = if (offline) {
                repository.getFlashSaleOff()
            } else {
                repository.getFlashSale(authen)
            }

            emit(result)
        }.flowOn(Dispatchers.IO)
    }


    fun getProductsByIdParent(offline: Boolean,type: String,idParent: String,authen: String): Flow<NetworkResponse<List<Product>>> {
        return flow {
            emit(NetworkResponse.Loading)
            val result = if (offline) {
                repository.getProductsByIdParentOff(type,idParent)
            } else {
                repository.getProductsByIdParent( type,idParent,authen)
            }

            emit(result)
        }.flowOn(Dispatchers.IO)
    }
    fun getRotation(type: String,authen: String): Flow<NetworkResponse<List<Rotation>>> {
        return flow {
            emit(NetworkResponse.Loading)
            val result =  repository.getRotation(type,authen)

            emit(result)
        }.flowOn(Dispatchers.IO)
    }






}
