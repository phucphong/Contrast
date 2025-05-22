package com.itechpro.domain.usecase.home


import android.util.Log
import com.itechpro.domain.model.category.Category
import com.itechpro.domain.model.network.NetworkResponse

import com.itechpro.domain.model.product.Product
import com.itechpro.domain.model.Rotation
import com.itechpro.domain.model.cart.CartItem
import com.itechpro.domain.model.home.SliderHome
import com.itechpro.domain.repository.HomeAffiliateRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class HomeAffiliateUseCase @Inject constructor(
    private val repository: HomeAffiliateRepository,

    ) {


    fun getCategory(
        obj: String, mode: String, type: String, idParent: String, authen: String
    ): Flow<NetworkResponse<List<Category>>> {
        return flow {
            emit(NetworkResponse.Loading)

            val result = if (authen.isEmpty()) {
                repository.getCategoryOff(obj, mode, type, idParent)
            } else {
                repository.getCategory(obj, mode, type, idParent, authen)
            }

            emit(result)
        }.flowOn(Dispatchers.IO)
    }

    fun getSlideHome(
        obj: String, mode: String, authen: String
    ): Flow<NetworkResponse<List<SliderHome>>> {
        return flow {
            emit(NetworkResponse.Loading)

            val result = if (authen.isEmpty()) {
                repository.getSlideHomeOff(obj, mode)
            } else {
                repository.getSlideHome(obj, mode, authen)
            }

            emit(result)
        }.flowOn(Dispatchers.IO)
    }

    fun getFlashSale(authen: String): Flow<NetworkResponse<List<Product>>> {
        return flow {
            emit(NetworkResponse.Loading)

            val result = if (authen.isEmpty()) {
                repository.getFlashSaleOff()
            } else {
                repository.getFlashSale(authen)
            }

            emit(result)
        }.flowOn(Dispatchers.IO)
    }


    fun getProductsByIdParent(
        type: String, idParent: String, authen: String
    ): Flow<NetworkResponse<List<Product>>> {
        return flow {
            emit(NetworkResponse.Loading)


            val result = if (authen.isEmpty()) {
                repository.getProductsByIdParentOff(type, idParent)
            } else {
                repository.getProductsByIdParent(type, idParent, authen)
            }

            emit(result)
        }.flowOn(Dispatchers.IO)
    }

    fun getRotation(type: String, authen: String): Flow<NetworkResponse<List<Rotation>>> {
        return flow {
            emit(NetworkResponse.Loading)
            val result = repository.getRotation(type, authen)

            emit(result)
        }.flowOn(Dispatchers.IO)
    }



}
