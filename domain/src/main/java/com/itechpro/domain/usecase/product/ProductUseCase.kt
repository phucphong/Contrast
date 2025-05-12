package com.itechpro.domain.usecase.product




import com.itechpro.domain.model.LikeProductService
import com.itechpro.domain.model.network.NetworkResponse

import com.itechpro.domain.model.product.Product
import com.itechpro.domain.model.product.ProductDetail
import com.itechpro.domain.model.report.ReportProduct
import com.itechpro.domain.repository.ProductRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class ProductUseCase @Inject constructor(
    private val repository: ProductRepository,

    ) {

    fun getInfoProduct(
        offline: Boolean,
        idParent: String,
        idUnit: String,
        authen: String
    ): Flow<NetworkResponse<ProductDetail>> {
        return flow {
            emit(NetworkResponse.Loading)

            val result = if (offline) {
                repository.getInfoProductOff(idParent, idUnit)
            } else {
                repository.getInfoProduct(idParent, idUnit, authen)
            }

            // Map List<ProductDetail> → ProductDetail (chỉ lấy phần tử đầu tiên)
            when (result) {
                is NetworkResponse.Success -> {
                    val firstItem = result.data.firstOrNull()
                    if (firstItem != null) {
                        emit(NetworkResponse.Success(firstItem))
                    } else {
                        emit(NetworkResponse.Error("Danh sách rỗng"))
                    }
                }
                is NetworkResponse.Error -> emit(result)
                is NetworkResponse.Loading -> emit(result)
            }
        }.flowOn(Dispatchers.IO)
    }


    fun getUnLike(type: String,idProduct: String,idUnit: String,authen: String): Flow<NetworkResponse<List<Product>>> {
        return flow {
            emit(NetworkResponse.Loading)
            val result = repository.getUnLike(type,idProduct,idUnit,authen)
            emit(result)
        }.flowOn(Dispatchers.IO)
    }
    fun getTypeReport(authen: String): Flow<NetworkResponse<List<ReportProduct>>> {
        return flow {
            emit(NetworkResponse.Loading)
            val result =  repository.getTypeReport(authen)
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
    fun getProductsCategory(
        offline: Boolean,
        type: String,
        idParent: String,
        idProduct: String,
        authen: String
    ): Flow<NetworkResponse<List<Product>>> {
        return flow {
            emit(NetworkResponse.Loading)

            val result = if (offline) {
                repository.getProductsByIdParentOff(type, idParent)
            } else {
                repository.getProductsByIdParent(type, idParent, authen)
            }

            val filteredResult = when (result) {
                is NetworkResponse.Success -> {
                    val filteredList = result.data.filter { it.id != idProduct }
                    NetworkResponse.Success(filteredList)
                }
                else -> result // giữ nguyên nếu là Loading, Error...
            }

            emit(filteredResult)
        }.flowOn(Dispatchers.IO)
    }
    fun getProductsOther(
        offline: Boolean,
        type: String,
        idParent: String,
        idProduct: String,
        authen: String
    ): Flow<NetworkResponse<List<Product>>> {
        return flow {
            emit(NetworkResponse.Loading)

            val result = if (offline) {
                repository.getProductsByIdParentOff(type, idParent)
            } else {
                repository.getProductsByIdParent(type, idParent, authen)
            }

            val filteredResult = when (result) {
                is NetworkResponse.Success -> {
                    val filteredList = result.data.filter { it.id != idProduct }
                    NetworkResponse.Success(filteredList)
                }
                else -> result // giữ nguyên nếu là Loading, Error...
            }

            emit(filteredResult)
        }.flowOn(Dispatchers.IO)
    }



    fun addEditLikeReport(
        url: String,
        obj: LikeProductService,
        authen: String
    ): Flow<NetworkResponse<List<LikeProductService>>> {
        return flow {
            emit(NetworkResponse.Loading)
            try {
                val result = repository.addEditLikeReport(url, obj, authen)
                emit(result)
            } catch (e: Exception) {
                emit(NetworkResponse.Error("Lỗi: ${e.localizedMessage ?: "Không xác định"}"))
            }
        }.flowOn(Dispatchers.IO)
    }



}
