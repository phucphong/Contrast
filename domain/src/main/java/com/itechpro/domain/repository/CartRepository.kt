package com.itechpro.domain.repository






import com.itechpro.domain.model.Customer
import com.itechpro.domain.model.cart.CartItem
import com.itechpro.domain.model.cart.Cart
import com.itechpro.domain.model.NetworkResponse

interface CartRepository {



    suspend fun getCarts(authen: String
    ): NetworkResponse<Cart>


    suspend fun getCheckOder(ids: String,authen: String
    ): NetworkResponse<List<CartItem>>
    suspend fun getCheckProduct(ids: String,authen: String
    ): NetworkResponse<Cart>



    suspend fun getDeleteCart(ids: String,device: String,content: String, authen: String): NetworkResponse<List<CartItem>>



    suspend fun addEditCart(url: String, obj: CartItem, authen: String): NetworkResponse<List<CartItem>>


   suspend fun addOder(url: String, obj: CartItem, authen: String): NetworkResponse<List<CartItem>>





}
