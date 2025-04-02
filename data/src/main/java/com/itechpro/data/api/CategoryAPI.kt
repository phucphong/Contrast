package com.itechpro.data.api

import com.itechpro.domain.model.Category
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface CategoryAPI {




    @Headers("Content-Type: application/json")
    @GET("{endpoint}")
    suspend fun getCategory(
        @Path("endpoint") endpoint: String?,
        @Query("obj") nguoiphutrach: String?,
        @Query("mode") getallbyidcongty: String?,
        @Query("tukhoa") key: String?,
        @Header("Authorization") authen: String?
    ): Response<List<Category>>


    @Headers("Content-Type: application/json")
    @POST("{endpoint}")
    suspend fun postCategory(

        @Path("endpoint") endpoint: String,
        @Query("obj") nguoiphutrach: String?,
        @Query("mode") getallbyidcongty: String?,
        @Query("tukhoa") key: String?,
        @Header("Authorization") authen: String?
    ): Response<List<Category>>

    @Headers("Content-Type: application/json")
    @GET("/ex/api/getobj")
    suspend fun getOpportunitiesProject(
        @Query("obj") obj: String?,
        @Query("mode") mode: String?,
        @Query("sodong") sodong: String?,
        @Query("sotrang") sotrang: String?,
        @Query("tukhoa") tukhoa: String?,
        @Header("Authorization") authen: String?
    ): Response<List<Category>>

    //loaichiphi
    @Headers("Content-Type: application/json")
    @GET("/ex/api/getobj")
    suspend fun getCostType(
        @Query("obj") loaichiphi: String?,
        @Query("mode") getallbyidcongtybyloai: String?,
        @Query("loai") cohoikinhdoanh_duan_khac: String?,
        @Header("Authorization") authen: String?
    ): Response<List<Category>>


    @Headers("Content-Type: application/json")
    @GET("/ex/api/getobj")
    suspend  fun getWorkByIdOppor(
        @Query("obj") congvieccohoikinhdoanh: String?,
        @Query("mode") getallbyidcongtybyidcohoikinhdoanh: String?,
        @Query("idcohoikinhdoanh") idOppor: String?,
        @Header("Authorization") authen: String?
    ): Response<List<Category>>
    @Headers("Content-Type: application/json")
    @GET("/ex/api/getobj")
    suspend  fun getWorkByIdProject(
        @Query("obj") congviecduan: String?,
        @Query("mode") getallbyidcongtybyidduan: String?,
        @Query("idduan") idProject: String?,
        @Header("Authorization") authen: String?
    ): Response<List<Category>>

    @Headers("Content-Type: application/json")
    @GET("/ex/apiaffiliate/getobj")
    suspend fun getAgencys(
        @Query("obj") dailyaf: String?,
        @Query("mode") laydanhsachdaily: String?,
        @Query("capchau") levelChild: String?,
        @Header("Authorization") authen: String?
    ): Response<List<Category>>

//    @Headers("Content-Type: application/json")
//    @GET("/ex/api/getobj")
//    suspend fun getAllCustomer(
//        @Query("obj") khachhang: String?,
//        @Query("mode") getallbyidcongtykhongapquyen: String?,
//        @Query("sodong") sodong: String?,
//        @Query("sotrang") sotrang: String?,
//        @Query("tukhoa") tukhoa: String?,
//        @Query("tungay") tungay: String?,
//        @Query("denngay") denngay: String?,
//        @Header("Authorization") authen: String?
//    ): Response<List<Customer>>
//
//    @Headers("Content-Type: application/json")
//    @GET("/ex/spa/getobj")
//    suspend fun getAllCustomerCalendar(
//        @Query("obj") obj: String?,
//        @Query("mode") mode: String?,
//
//        @Query("tukhoa") tukhoa: String?,
//        @Header("Authorization") authen: String?
//    ): Response<List<Customer>>


    @Headers("Content-Type: application/json")
    @GET("/ex/api/getobj")
    suspend  fun getProductByIdGroups(
        @Query("obj") sanpham: String?,
        @Query("mode") laysanphamtheonhom: String?,
        @Query("idnhomsanpham") idnhomsanpham: String?,
        @Header("Authorization") authen: String?
    ): Response<List<Category>>


    //lien he
    @Headers("Content-Type: application/json")
    @GET("/ex/api/getobj")
    suspend   fun getContacts(
        @Query("obj") lienhe: String?,
        @Query("mode") getallbyidcongty: String?,
        @Query("sotrang") sotrang: String?,
        @Query("sodong") sodong: String?,
        @Query("tukhoa") tukhoa: String?,
        @Header("Authorization") authen: String?
    ): Response<List<Category>>


    @Headers("Content-Type: application/json")
    @GET("/ex/api/getobj")
    suspend  fun getContactsByIDCustomer(
        @Query("obj") lienhe: String?,
        @Query("mode") getallbyidcongtybyidkhachhang: String?,
        @Query("idkhachhang") idkhachhang: String?,
        @Header("Authorization") authen: String?
    ): Response<List<Category>>


    //mucdouutien
    @Headers("Content-Type: application/json")
    @GET("/ex/api/getobj")
    suspend   fun getPriorityLevel(
        @Query("obj") mucdouutien: String?,
        @Query("mode") getallbyidcongtybyloai: String?,
        @Query("loai") duan_cohoikinhdoanh_congviec: String?,
        @Header("Authorization") authen: String?
    ): Response<List<Category>>

    @Headers("Content-Type: application/json")
    @GET("/ex/api/getobj")
    suspend fun getCountry(
        @Query("obj") nguoiphutrach: String?,
        @Query("mode") getallbyidcongty: String?,
        @Query("tukhoa") key: String?,
        @Header("Authorization") authen: String?
    ): Response<List<Category>>


    @Headers("Content-Type: application/json")
    @GET("/ex/apiaffiliate/laydulieu_noAt")
    suspend fun getCountryOff(
        @Query("obj") nguoiphutrach: String?,
        @Query("mode") getallbyidcongty: String?,
        @Query("key") key: String?,

        ): Response<List<Category>>

    @Headers("Content-Type: application/json")
    @GET("/ex/api/getobj")
    suspend fun getProvince(
        @Query("obj") laytinhthanh: String?,
        @Query("mode") modelaytinhthanh: String?,
        @Query("idquocgia") idCountry: String?,
        @Query("tukhoa") key: String?,
        @Header("Authorization") authen: String?
    ): Response<List<Category>>

    @Headers("Content-Type: application/json")
    @GET("/ex/apiaffiliate/laydulieu_noAt")
    suspend fun getProvinceOff(
        @Query("obj") laytinhthanh: String?,
        @Query("mode") modelaytinhthanh: String?,
        @Query("idquocgia") idCountry: String?,
        @Query("key") key: String?,

        ): Response<List<Category>>

    // Tỉnh thành
    @Headers("Content-Type: application/json")
    @GET("/ex/api/getobj")
    suspend fun getDistrict(
        @Query("obj") layquanhuyen: String?,
        @Query("mode") modelayquanhuyen: String?,
        @Query("idtinhthanh") idProvince: String?,
        @Query("tukhoa") key: String?,
        @Header("Authorization") authen: String?
    ): Response<List<Category>>

    // quận huyện

    @Headers("Content-Type: application/json")
    @GET("/ex/apiaffiliate/laydulieu_noAt")
    suspend fun getDistrictOff(
        @Query("obj") layquanhuyen: String?,
        @Query("mode") modelayquanhuyen: String?,
        @Query("idtinhthanh") idProvince: String?,
        @Query("key") key: String?,

        ): Response<List<Category>>
    // phường xã
    @Headers("Content-Type: application/json")
    @GET("/ex/api/getobj")
    suspend fun getWards(
        @Query("obj") layphuongxa: String?,
        @Query("mode") modelayphuongxa: String?,
        @Query("idquanhuyen") idDistrict: String?,
        @Query("tukhoa") key: String?,
        @Header("Authorization") authen: String?
    ): Response<List<Category>>

    // phường xã
    @Headers("Content-Type: application/json")
    @GET("/ex/apiaffiliate/laydulieu_noAt")
    suspend fun getWardsOff(
        @Query("obj") layphuongxa: String?,
        @Query("mode") modelayphuongxa: String?,
        @Query("idquanhuyen") idDistrict: String?,
        @Query("key") key: String?,
        ): Response<List<Category>>

    @Headers("Content-Type: application/json")
    @GET("/ex/apiaffiliate/getobj")
    // phường xã

    suspend fun getStatus(
        @Query("obj") obj: String?,
        @Query("mode") mode: String?,

        @Header("Authorization") authen: String?
    ): Response<List<Category>>
}