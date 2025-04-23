package com.itechpro.domain.usecase.home


import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import com.itechpro.domain.model.Category
import com.itechpro.domain.model.NetworkResponse

import com.itechpro.domain.model.Product
import com.itechpro.domain.model.PromoUiData
import com.itechpro.domain.model.Rotation
import com.itechpro.domain.model.SliderHome
import com.itechpro.domain.repository.HomeAffiliateRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import java.time.Duration
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import javax.inject.Inject

class HomeAffiliateUseCase @Inject constructor(
    private val repository: HomeAffiliateRepository,

    ) {


    fun getCategory(offline: Boolean, obj: String,mode: String,type: String,idParent: String,authen: String): Flow<NetworkResponse<List<Category>>> {
        return flow {
            emit(NetworkResponse.Loading)
            val startTime = System.currentTimeMillis()
            Log.d("Timing", "📤 Start getCategory at $startTime")
            val result = if (offline) {
                repository.getCategoryOff(obj, mode,type,idParent)
            } else {
                repository.getCategory(obj, mode,type,idParent,authen)
            }
            val endTime = System.currentTimeMillis()
            Log.d("Timing", "📦 API getCategory response in ${endTime - startTime}ms")
            emit(result)
        }.flowOn(Dispatchers.IO)
    }

    fun getSlideHome(offline: Boolean, obj: String,mode: String,authen: String): Flow<NetworkResponse<List<SliderHome>>> {
        return flow {
            emit(NetworkResponse.Loading)
            val startTime = System.currentTimeMillis()
            Log.d("Timing", "📤 Start getSlideHome at $startTime")
            val result = if (offline) {
                repository.getSlideHomeOff(obj, mode)
            } else {
                repository.getSlideHome(obj, mode,authen)
            }

            val endTime = System.currentTimeMillis()
            Log.d("Timing", "📦 API getSlideHome response in ${endTime - startTime}ms")
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
            val startTime = System.currentTimeMillis()
            Log.d("Timing", "📤 Start getProductsByIdParent at $startTime")
            val result = if (offline) {
                repository.getProductsByIdParentOff(type,idParent)
            } else {
                repository.getProductsByIdParent( type,idParent,authen)
            }
            val endTime = System.currentTimeMillis()
            Log.d("Timing", "📦 API getProductsByIdParent response in ${endTime - startTime}ms")
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
