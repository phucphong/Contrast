package com.itechpro.data.api



import com.itechpro.domain.model.Account
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

interface AccountAPI {

    @Headers("Content-Type: application/json")
    @POST("/ex/apikh/capnhattaikhoan")
    suspend fun getUpdateAccount(
        @Body body: Account?,
        @Header("Authorization") authen: String?
    ): Response<List<Account>>

    @Headers("Content-Type: application/json")
    @POST("/ex/apiaffiliate/doimatkhau_noAuth")
    suspend fun getUpdateAccountOff(
        @Query("username") username: String?,
        @Query("password") password: String?,
        @Query("key") key: String?,

        ): Response<List<Account>>



    @Headers("Content-Type: application/json")
    @POST("/ex/api/Nhan_vien")
    suspend  fun getInfoAccountEmployee(
        @Query("listparajson") listparajson: String?,
        @Header("Authorization") authen: String?
    ): Response<List<Account>>

    @Headers("Content-Type: application/json")
    @GET("/ex/spa/getobj")
    suspend  fun getInfoQRCode(
        @Query("obj") obj: String?,
        @Query("mode") mode: String?,
        @Query("noidung") content: String?,
        @Header("Authorization") authen: String?
    ): Response<List<Account>>

    @Multipart
//    @POST("/ex/api/uploadFile")
    @POST("/ex/apiaffiliate/uploadFile")
    suspend fun uploadAvatar(
        @Part file: MultipartBody.Part,
        @Query("description") android: String?,
        @Query("fileName") fileName: String?,
        @Query("loaidinhkem") type: String?,
        @Query("loaidoituong") anhdaidien_daily: String?,
        @Header("Authorization") authen: String?
    ): Response<ResponseBody>

}