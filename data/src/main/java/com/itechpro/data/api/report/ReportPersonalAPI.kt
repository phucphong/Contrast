package com.itechpro.data.api.report




import com.itechpro.domain.model.report.Report
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.Query

interface ReportPersonalAPI {

    @Headers("Content-Type: application/json")
    @GET("/ex/apiaffiliate/getobj")
    suspend fun getReportPersonalSales(
        @Query("obj") dailyaf: String?,
        @Query("mode") bchuonghoahongthudong: String?,
        @Query("tungay") startDate: String?,
        @Query("denngay") endDate: String?,
        @Header("Authorization") authen: String?
    ): Response<List<Report>>



    @Headers("Content-Type: application/json")
    @GET("/ex/apiaffiliate/getobj")
    suspend fun getReportUpToLevel(
        @Query("obj") dailyaf: String?,
        @Query("mode") bchuonghoahongthudong: String?,
        @Query("thangnam") thangnam: String?,
        @Query("ids") idsAgency: String?,
        @Query("idscapdaily") idsAgencyLevel: String?,
        @Header("Authorization") authen: String?
    ): Response<List<Report>>

    @Headers("Content-Type: application/json")
    @GET("/ex/apiaffiliate/getobj")
    suspend fun getReportSalesReportByAgentByLevel(
        @Query("obj") dailyaf: String?,
        @Query("mode") bcdoanhsotheodailycapdo: String?,
        @Query("tungay") startDate: String?,
        @Query("denngay") enDate: String?,
        @Query("ids") idsAgency: String?,
        @Query("idscapdaily") idsAgencyLevel: String?,
        @Header("Authorization") authen: String?
    ): Response<List<Report>>



    @Headers("Content-Type: application/json")
    @GET("/ex/apiaffiliate/getobj")
    suspend fun getReportPassiveCommission(
        @Query("obj") dailyaf: String?,
        @Query("mode") bchuonghoahongthudong: String?,
        @Query("thangnam") thangnam: String?,

        @Header("Authorization") authen: String?
    ): Response<List<Report>>

}