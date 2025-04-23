package com.itechpro.domain.usecase.product




import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import com.itechpro.domain.model.Category
import com.itechpro.domain.model.Evaluate
import com.itechpro.domain.model.NetworkResponse

import com.itechpro.domain.model.Product
import com.itechpro.domain.model.PromoUiData
import com.itechpro.domain.model.Rotation
import com.itechpro.domain.model.SliderHome
import com.itechpro.domain.model.cart.CartItem
import com.itechpro.domain.repository.HomeAffiliateRepository
import com.itechpro.domain.repository.ProductRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import java.time.Duration
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import javax.inject.Inject

class ProductUseCase @Inject constructor(
    private val repository: ProductRepository,

    ) {


    fun getInfoProduct(offline: Boolean, idParent: String,idUnit: String,authen: String): Flow<NetworkResponse<List<Product>>> {
        return flow {
            emit(NetworkResponse.Loading)

            val result = if (offline) {
                repository.getInfoProductOff(idParent, idUnit)
            } else {
                repository.getInfoProduct(idParent,idUnit, authen)
            }

            emit(result)
        }.flowOn(Dispatchers.IO)
    }

    fun getUnLike(type: String,idProduct: String,idUnit: String,authen: String): Flow<NetworkResponse<List<Product>>> {
        return flow {
            emit(NetworkResponse.Loading)
            val result = repository.getUnLike(type,idProduct,idUnit,authen)
            emit(result)
        }.flowOn(Dispatchers.IO)
    }
    fun getTypeReport(authen: String): Flow<NetworkResponse<List<Product>>> {
        return flow {
            emit(NetworkResponse.Loading)
            val result =  repository.getTypeReport(authen)
            emit(result)
        }.flowOn(Dispatchers.IO)
    }

    fun getEvaluates(offline: Boolean, idProduct: String,count: String,authen: String): Flow<NetworkResponse<Evaluate>> {
        return flow {
            emit(NetworkResponse.Loading)
            val result = if (offline) {
                repository.getEvaluatesOff(idProduct, count)
            } else {
                repository.getEvaluates(idProduct, count,authen)
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



    fun addEditLike(
        url: String,
        obj: Product,
        authen: String
    ): Flow<NetworkResponse<List<Product>>> {
        return flow {
            emit(NetworkResponse.Loading)
            try {
                val result = repository.addEditLike(url, obj, authen)
                emit(result)
            } catch (e: Exception) {
                emit(NetworkResponse.Error("Lỗi: ${e.localizedMessage ?: "Không xác định"}"))
            }
        }.flowOn(Dispatchers.IO)
    }



}
