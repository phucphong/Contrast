package com.itechpro.domain.usecase.cart






import android.util.Log
import com.itechpro.domain.model.Customer
import com.itechpro.domain.model.cart.CartItem
import com.itechpro.domain.model.cart.Cart
import com.itechpro.domain.model.NetworkResponse
import com.itechpro.domain.model.cart.CartResult
import com.itechpro.domain.model.payment.InfoPayment
import com.itechpro.domain.model.payment.OrderPayment
import com.itechpro.domain.repository.CartRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class CartUseCase @Inject constructor(
    private val repository: CartRepository,

    ) {




    fun getCarts(isTotalOder:Boolean,typeAccount:String, authToken: String): Flow<NetworkResponse<CartResult>> {
        return flow {
            emit(NetworkResponse.Loading)
            when (val result = repository.getCarts(authToken)) {
                is NetworkResponse.Success -> {
                    val items = result.data.Table1

                    if(isTotalOder){
                        emit(NetworkResponse.Success( calculateCartTotal(items,true)))
                    }else{
                        emit(NetworkResponse.Success(CartResult(items, items.size,   totalValue = 0.0,
                            totalIntoMoney = 0.0,
                            amountMoneyDiscount = 0.0)))
                    }

                }
                is NetworkResponse.Error -> {
                    emit(NetworkResponse.Error(result.message))
                }

                NetworkResponse.Loading -> {}
            }
        }.flowOn(Dispatchers.IO)
    }

    fun calculateCartTotal(carts: List<CartItem>,  init:Boolean): CartResult {
        var totalValue = 0.0
        var moneyDiscountValue = 0.0

        carts.forEach { product ->

            val quantity = product.soluong ?: 1.0
            val pricePerItem = product.dongia ?: 0.0
            val discountPerItem = product.sotienkm1sp ?: 0.0
            if(init){
                product.isChecked=true
                totalValue += pricePerItem * quantity
                moneyDiscountValue += discountPerItem * quantity
            }else{
                if (product.isChecked == true) {
                    totalValue += pricePerItem * quantity
                    moneyDiscountValue += discountPerItem * quantity
                }
            }

        }

        val totalIntoMoney = totalValue - moneyDiscountValue

        return CartResult(
            totalValue = totalValue,
            totalIntoMoney = totalIntoMoney,
            amountMoneyDiscount = moneyDiscountValue,
            items = carts,
            totalCount =carts.size
        )
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

    fun getDisCountAgency( authen: String): Flow<NetworkResponse<Double>> {
        return flow {
            emit(NetworkResponse.Loading)
            when (val result = repository.getDisCountAgency( authen)) {
                is NetworkResponse.Success -> {
                    val obj: List<CartItem> = result.data
                    val status = obj.firstOrNull()?.phantram ?:0.0
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


    fun checkProductBeforePayment(ids: String,authen: String): Flow<NetworkResponse<String>> {
        return flow {
            emit(NetworkResponse.Loading)
            when (val result = repository.checkProductBeforePayment(ids ,authen)) {
                is NetworkResponse.Success -> {
                    val items = result.data.table
                    val status = items.firstOrNull()?.Column1 ?: ""
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
    fun addOrder(
        isOpportunity: Boolean,
        order: OrderPayment,
        authToken: String
    ): Flow<NetworkResponse<InfoPayment>> = flow {
        emit(NetworkResponse.Loading)
        try {
            val url = if (isOpportunity) {
                "/ex/api/adddonhang_cohoi"
            } else {
                "/ex/api/adddonhang_aff"
            }

            when (val result = repository.addOder(url, order, authToken)) {
                is NetworkResponse.Success -> {
                    val firstItem = result.data.firstOrNull()
                    if (firstItem != null) {
                        emit(NetworkResponse.Success(firstItem))
                    } else {
                        emit(NetworkResponse.Error("Danh sách trả về rỗng"))
                    }
                }

                is NetworkResponse.Error -> {
                    emit(NetworkResponse.Error(result.message))
                }

                else -> Unit
            }
        } catch (e: Exception) {
            emit(NetworkResponse.Error("Lỗi: ${e.localizedMessage ?: "Không xác định"}"))
        }
    }.flowOn(Dispatchers.IO)



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

    fun idsProductCheckActive(listModel: List<CartItem>): String {
        return listModel
            .mapNotNull { item ->
                val idProduct = item.idsp?.replace(".0", "") ?: return@mapNotNull null
                val idUnit = item.iddonvi ?: return@mapNotNull null
                "$idProduct-$idUnit"
            }
            .distinct()
            .joinToString(",")
    }





}
