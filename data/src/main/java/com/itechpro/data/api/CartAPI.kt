package com.itechpro.data.api

import com.itechpro.domain.model.cart.CartItem
import com.itechpro.domain.model.cart.Cart
import com.itechpro.domain.model.cart.CheckProductActive
import com.itechpro.domain.model.payment.InfoPayment
import com.itechpro.domain.model.payment.OrderPayment
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query
import retrofit2.http.Url

interface CartAPI {


    // danh sách đơn hàng

    @Headers("Content-Type: application/json")
    @GET("/ex/apiaffiliate/getobj")
    suspend fun getCarts(
        @Query("obj") giohang: String?,
        @Query("mode") laygiohang: String?,
        @Header("Authorization") authen: String?
    ): Response<Cart>




    @Headers("Content-Type: application/json")
    @GET("/ex/apiaffiliate/getobj")
    suspend   fun getDeleteCart(
        @Query("obj") obj: String?,
        @Query("mode") deletes: String?,
        @Query("ids") ids: String?,
        @Query("mamenu") mamenu: String?,
        @Query("os") os: String?,
        @Query("device") device: String?,
        @Query("noidungchinh") noidungchinh: String?,
        @Header("Authorization") authen: String?
    ): Response<List<CartItem>>

    @Headers("Content-Type: application/json")
    @GET("/ex/apiaffiliate/getobj")
    suspend fun checkProductBeforePayment(
        @Query("obj") giohang: String?,
        @Query("mode") checksphoatdong: String?,
        @Query("ids") ids: String?,
        @Header("Authorization") authen: String?
    ): Response<CheckProductActive>


    @Headers("Content-Type: application/json")
    @GET("/ex/api/getobj")
    suspend fun getCheckOder(

        @Query("obj") obj: String?,
        @Query("mode") mode: String?,
        @Query("ids") ids: String?,
        @Header("Authorization") authen: String?
    ): Response<List<CartItem>>

    @Headers("Content-Type: application/json")
    @GET("/ex/apiaffiliate/getobj")
    suspend fun getDisCountAgency(
        @Query("obj") dailyaf: String?,
        @Query("mode") layphantramckcanhan: String?,
        @Header("Authorization") authen: String?
    ): Response<List<CartItem>>


    @Headers("Content-Type: application/json")
    @POST
    suspend fun addEditCart(
        @Url url: String,
        @Body body: CartItem?,
        @Header("Authorization") authen: String?
    ): Response<List<CartItem>>



    @Headers("Content-Type: application/json")
    @POST("{endpoint}")
    suspend fun addOder(
        @Path("endpoint") endpoint: String,
        @Body body: OrderPayment,
        @Header("Authorization") authen: String?
    ): Response<List<InfoPayment>>


}