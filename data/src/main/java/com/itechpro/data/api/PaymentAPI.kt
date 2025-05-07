package com.itechpro.data.api



import com.itechpro.domain.model.Customer
import com.itechpro.domain.model.cart.CartItem
import com.itechpro.domain.model.cart.Cart
import com.itechpro.domain.model.cart.CheckProductActive
import com.itechpro.domain.model.payment.InfoPayment
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query
import retrofit2.http.Url

interface PaymentAPI {


    @Headers("Content-Type: application/json")
    @GET("/ex/apiaffiliate/getobj")
    suspend  fun getInfoPayment(
        @Query("obj") obj: String?,
        @Query("mode") mode: String?,
        @Query("sotien") money: String?,
        @Query("madonhang") oderKey: String?,
        @Header("Authorization") authen: String?
    ): Response<List<InfoPayment>>

    @Headers("Content-Type: application/json")
    @GET("/ex/api/getobj")
    suspend fun getUpdateOder(
        @Query("obj") obj: String?,
        @Query("mode") mode: String?,
        @Query("idkhachhang") idCustomer: String?,
        @Query("iddonhang") idOder: String?,
        @Query("ghichu") note: String?,
        @Header("Authorization") authen: String?
    ): Response<List<InfoPayment>>

    @Headers("Content-Type: application/json")
    @GET("/ex/api/getobj")
    suspend fun getInFoCustomerByPhone(
        @Query("obj") obj: String?,
        @Query("mode") mode: String?,
        @Query("dienthoai") phone: String?,

        @Header("Authorization") authen: String?
    ): Response<List<InfoPayment>>


    @Headers("Content-Type: application/json")
    @GET("/ex/api/getobj")
    suspend  fun checkEmail(
        @Query("obj") khachhang: String?,
        @Query("mode") mode: String?,
        @Query("email") email: String?,
        @Query("ido") ido: String?,
        @Header("Authorization") authen: String?
    ): Response<List<InfoPayment>>
}