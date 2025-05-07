package com.itechpro.domain.repository
import com.itechpro.domain.model.Customer
import com.itechpro.domain.model.cart.CartItem
import com.itechpro.domain.model.cart.Cart
import com.itechpro.domain.model.NetworkResponse
import com.itechpro.domain.model.cart.CheckProductActive
import com.itechpro.domain.model.payment.InfoPayment

interface PaymentRepository {

    suspend fun getInfoPayment(money: String,oderKey: String,authen: String
    ): NetworkResponse<List<InfoPayment>>

    suspend fun getUpdateOder(idCustomer: String,idOder: String,ghichu: String,authen: String
    ): NetworkResponse<List<InfoPayment>>


    suspend fun getInFoCustomerByPhone(phone: String,authen: String
    ): NetworkResponse<List<InfoPayment>>

    suspend fun checkEmail(email: String,authen: String
    ): NetworkResponse<List<InfoPayment>>


}
