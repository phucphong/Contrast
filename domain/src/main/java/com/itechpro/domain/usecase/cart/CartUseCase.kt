package com.itechpro.domain.usecase.cart






import com.itechpro.domain.model.Customer
import com.itechpro.domain.model.cart.CartItem
import com.itechpro.domain.model.cart.Cart
import com.itechpro.domain.model.NetworkResponse
import com.itechpro.domain.model.cart.CartResult
import com.itechpro.domain.repository.CartRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class CartUseCase @Inject constructor(
    private val repository: CartRepository,

    ) {




    fun getCarts(authToken: String): Flow<NetworkResponse<CartResult>> {
        return flow {
            emit(NetworkResponse.Loading)
            when (val result = repository.getCarts(authToken)) {
                is NetworkResponse.Success -> {
                    val items = result.data.Table1
                    emit(NetworkResponse.Success(CartResult(items, items.size)))
                }
                is NetworkResponse.Error -> {
                    emit(NetworkResponse.Error(result.message))
                }

                NetworkResponse.Loading -> {}
            }
        }.flowOn(Dispatchers.IO)
    }

    fun getCheckOder(ids: String, authen: String): Flow<NetworkResponse<String>> {
        return flow {
            emit(NetworkResponse.Loading)
            when (val result = repository.getCheckOder(ids, authen)) {
                is NetworkResponse.Success -> {
                    val obj: List<CartItem> = result.data
                    val status = obj.firstOrNull()?.trangthai ?: ""
                    emit(NetworkResponse.Success(status))
                }

                is NetworkResponse.Error -> {
                    emit(NetworkResponse.Error(result.message))
                }

                else -> Unit
            }
        }.flowOn(Dispatchers.IO)
    }

    fun getDeleteCart(ids: String,device: String,content: String, authen: String): Flow<NetworkResponse<String>> {
        return flow {
            when (val result = repository.getDeleteCart(ids,device,content, authen)) {
                is NetworkResponse.Success -> {
                    val obj: List<CartItem> = result.data
                    val status = obj.firstOrNull()?.Column1 ?: ""
                    emit(NetworkResponse.Success(status))
                }

                is NetworkResponse.Error -> {
                    emit(NetworkResponse.Error(result.message))
                }

                else -> Unit
            }
        }.flowOn(Dispatchers.IO)
    }


    fun getCheckProduct(ids: String,authen: String): Flow<NetworkResponse<String>> {
        return flow {
            emit(NetworkResponse.Loading)
            when (val result = repository.getCheckProduct(ids ,authen)) {
                is NetworkResponse.Success -> {
                    val items = result.data.Table1
                    val status = items.firstOrNull()?.trangthai ?: ""
                    emit(NetworkResponse.Success(status))
                }
                is NetworkResponse.Error -> {
                    emit(NetworkResponse.Error(result.message))
                }

                NetworkResponse.Loading -> {}
            }
        }.flowOn(Dispatchers.IO)
    }

    fun addEditCart(
        url: String,
        obj: CartItem,
        authen: String
    ): Flow<NetworkResponse<List<CartItem>>> {
        return flow {
            emit(NetworkResponse.Loading)
            try {
                val result = repository.addEditCart(url, obj, authen)
                emit(result)
            } catch (e: Exception) {
                emit(NetworkResponse.Error("Lỗi: ${e.localizedMessage ?: "Không xác định"}"))
            }
        }.flowOn(Dispatchers.IO)
    }
    fun addOder(
        url: String,
        obj: CartItem,
        authen: String
    ): Flow<NetworkResponse<List<CartItem>>> {
        return flow {
            emit(NetworkResponse.Loading)
            try {
                val result = repository.addOder(url, obj, authen)
                emit(result)
            } catch (e: Exception) {
                emit(NetworkResponse.Error("Lỗi: ${e.localizedMessage ?: "Không xác định"}"))
            }
        }.flowOn(Dispatchers.IO)
    }



    fun idsDeleteCart(carts: List<CartItem>): String {
        val idsDelete = buildString {
            carts.forEach { item ->
                val idProduct = item.idsp.orEmpty()
                val idUnit = "-" + item.iddonvi.orEmpty()
                val ids = idProduct.replace(".0", "") + idUnit
                if (!this.contains(ids)) {
                    if (this.isNotEmpty()) append(",")
                    append(ids)
                }
            }
        }
        return idsDelete
    }

    fun onCheckedChangeAll(carts: List<CartItem>, isChecked: Boolean): List<CartItem> {
        return carts.map { item ->
            item.copy(isChecked = isChecked)
        }
    }







}
