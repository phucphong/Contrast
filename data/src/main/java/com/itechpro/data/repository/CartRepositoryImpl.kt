package com.itechpro.data.repository




import com.itechpro.data.api.CartAPI
import com.itechpro.domain.model.Customer
import com.itechpro.domain.model.cart.CartItem
import com.itechpro.domain.model.cart.Cart

import com.itechpro.domain.model.NetworkResponse
import com.itechpro.domain.model.cart.CheckProductActive
import com.itechpro.domain.model.payment.InfoPayment
import com.itechpro.domain.model.payment.OrderPayment

import com.itechpro.domain.repository.CartRepository

import javax.inject.Inject

class CartRepositoryImpl @Inject constructor(
    private val api: CartAPI
) : CartRepository {

    //    suspend fun getCarts() = apiService.getCarts("giohang", "laygiohang", authen)

    override suspend fun getCarts( authen: String): NetworkResponse<Cart> {
        val response = api.getCarts("giohang", "laygiohang", authen)
        return if (response.isSuccessful) {
            val body = response.body()
            if (body != null) {
                NetworkResponse.Success(body)
            } else {
                NetworkResponse.Error("Cart data is null")
            }
        } else {
            NetworkResponse.Error("Lỗi: ${response.message()}")
        }
    }



//    suspend fun checkOder(ids:String) = apiService.checkOder("checkgiavakm", "checkgiavakm",ids, authen)
    override suspend fun getCheckOder( ids: String,authen: String): NetworkResponse<List<CartItem>> {
        val response = api.getCheckOder("checkgiavakm","checkgiavakm",ids,authen)
    return if (response.isSuccessful) {
        NetworkResponse.Success(response.body() ?: emptyList())
    } else {
        NetworkResponse.Error("Lỗi: ${response.message()}")
    }
    }

//    suspend fun checkOder(ids:String) = apiService.checkOder("checkgiavakm", "checkgiavakm",ids, authen)
    override suspend fun getDisCountAgency( authen: String): NetworkResponse<List<CartItem>> {
        val response = api.getDisCountAgency("dailyaf","layphantramckcanhan",authen)
    return if (response.isSuccessful) {
        NetworkResponse.Success(response.body() ?: emptyList())
    } else {
        NetworkResponse.Error("Lỗi: ${response.message()}")
    }
    }

//    suspend fun checkOder(ids:String) = apiService.checkOder("checkgiavakm", "checkgiavakm",ids, authen)
    override suspend fun checkProductBeforePayment( ids: String,authen: String): NetworkResponse<CheckProductActive> {
        val response = api.checkProductBeforePayment("giohang","checksphoatdong",ids,authen)
    return if (response.isSuccessful) {
        val body = response.body()
        if (body != null) {
            NetworkResponse.Success(body)
        } else {
            NetworkResponse.Error("Cart data is null")
        }
    } else {
        NetworkResponse.Error("Lỗi: ${response.message()}")
    }
    }

// apiService.deleteCart("giohang", "xoagiohang", ids,menuName,"android",device,content, authen)
    override suspend fun getDeleteCart(ids: String,device: String,content: String, authen: String): NetworkResponse<List<CartItem>> {
        val response = api.getDeleteCart("giohang","xoagiohang",ids,"giohang", "android",device,content,authen)
        return if (response.isSuccessful) {
            NetworkResponse.Success(response.body() ?: emptyList())
        } else {
            NetworkResponse.Error("Lỗi: ${response.message()}")
        }
    }



    override suspend fun addEditCart(url: String, obj: CartItem, authen: String): NetworkResponse<List<CartItem>> {
        return try {
            val response = api.addEditCart(url, obj,authen)
            if (response.isSuccessful) {
                NetworkResponse.Success(response.body() ?: emptyList())
            } else {
                NetworkResponse.Error("Lỗi: ${response.message()}")
            }
        } catch (e: Exception) {
            NetworkResponse.Error("Exception: ${e.message}")


        }
    }



    override suspend fun addOder(url: String, obj: OrderPayment, authen: String): NetworkResponse<List<InfoPayment>> {
        return try {
            val response = api.addOder(url, obj,authen)
            if (response.isSuccessful) {
                NetworkResponse.Success(response.body() ?: emptyList())
            } else {
                NetworkResponse.Error("Lỗi: ${response.message()}")
            }
        } catch (e: Exception) {
            NetworkResponse.Error("Exception: ${e.message}")


        }
    }




}
