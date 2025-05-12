package com.itechpro.domain.repository






import com.itechpro.domain.model.cart.CartItem
import com.itechpro.domain.model.cart.Cart
import com.itechpro.domain.model.network.NetworkResponse
import com.itechpro.domain.model.cart.CheckProductActive
import com.itechpro.domain.model.payment.InfoPayment
import com.itechpro.domain.model.payment.OrderPayment

interface CartRepository {



    suspend fun getCarts(authen: String
    ): NetworkResponse<Cart>


    suspend fun getCheckOder(ids: String,authen: String
    ): NetworkResponse<List<CartItem>>

    suspend fun getDisCountAgency(authen: String
    ): NetworkResponse<List<CartItem>>

    suspend fun checkProductBeforePayment(ids: String,authen: String
    ): NetworkResponse<CheckProductActive>



    suspend fun getDeleteCart(ids: String,device: String,content: String, authen: String): NetworkResponse<List<CartItem>>



    suspend fun addEditCart(url: String, obj: CartItem, authen: String): NetworkResponse<List<CartItem>>


   suspend fun addOder(url: String, obj: OrderPayment, authen: String): NetworkResponse<List<InfoPayment>>





}
