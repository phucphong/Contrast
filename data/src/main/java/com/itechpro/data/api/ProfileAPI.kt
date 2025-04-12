package com.itechpro.data.api




import com.itechpro.domain.model.Account
import com.itechpro.domain.model.Category
import com.itechpro.domain.model.FireBaseClient
import com.itechpro.domain.model.Login
import com.itechpro.domain.model.News
import com.itechpro.domain.model.Notification
import okhttp3.MultipartBody
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Query
import retrofit2.http.Url

interface ProfileAPI {


    @Headers("Content-Type: application/json")
    @GET("/ex/apiaffiliate/getobj")
    suspend  fun getInfoAccount(
        @Query("obj") obj: String?,
        @Query("mode") mode: String?,
        @Query("idkhachhang") idCustomer: String?,
        @Query("loaitk") typeAccount: String?,
        @Header("Authorization") authen: String?
    ): Response<List<Account>>
    //http://192.168.1.18:666/ex/api_Sanpham/getobj?obj=laythongtindangnhap&mode=laydsyeuthich&idkhachhang=nhanvien
    @Headers("Content-Type: application/json")
    @GET("/ex/apiaffiliate/getobj")
    suspend  fun getCoupon(
        @Query("obj") obj: String?,
        @Query("mode") mode: String?,
        @Query("idkhachhang") idCustomer: String?,

        @Header("Authorization") authen: String?
    ): Response<List<Account>>




    //http://192.168.1.5:224/ex/api/getobj?mode=layqrcodetheonoidung&obj=layqrcode&noidung=1794703-192.168.1.119:224-http
    @Headers("Content-Type: application/json")
    @GET("/ex/api/getobj")
    suspend  fun getQrCode(
        @Query("obj") layqrcode: String?,
        @Query("mode") layqrcodetheonoidung: String?,
        @Query("noidung") content: String?,
        @Header("Authorization") authen: String?
    ): Response<List<Account>>

    //["obj":"layqrcode",
    //                              "mode": "modelayqrcode",
    //                              "idkhachhang": idkhachhang]

    //http://192.168.1.5:224/ex/api/getobj?mode=layqrcodetheonoidung&obj=layqrcode&noidung=1794703-192.168.1.119:224-http
    @Headers("Content-Type: application/json")
    @GET("/ex/spa/getobj")
    suspend  fun getQrCodeCustomer(
        @Query("obj") obj: String?,
        @Query("mode") mode: String?,
        @Query("idkhachhang") idCustomer: String?,
        @Header("Authorization") authen: String?
    ): Response<List<Account>>

    @Headers("Content-Type: application/json")
    @GET("/ex/api/getobj")
    suspend  fun getQrCodeEmployee(
        @Query("obj") obj: String?,
        @Query("mode") mode: String?,
        @Header("Authorization") authen: String?
    ): Response<List<Account>>



    // lấy menu app
    @Headers("Content-Type: application/json")
    @GET("/ex/api/Lay_Menu_App")
    suspend fun getMenuApp(
        @Query("sodong") sodong: String?, @Query("sotrang") sotrang: String?, @Query("loai") loai: String?, @Header("Authorization") authen: String?
    ): Response<List<Category>>






}