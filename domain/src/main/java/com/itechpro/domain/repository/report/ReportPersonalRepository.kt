package com.itechpro.domain.repository.report




import com.itechpro.domain.model.network.NetworkResponse
import com.itechpro.domain.model.opportunity_project.OpportunityProject
import com.itechpro.domain.model.report.Report
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.Query
import java.time.Year


interface ReportPersonalRepository {


    suspend fun getReportPersonalSales(obj: String,mode: String,startDate: String,endDate: String,authen: String): NetworkResponse<List<Report>>
    suspend fun getReportSalesReportByAgentByLevel(obj: String,mode: String,startDate: String,endDate: String,ids: String,idsAgencyLevel: String,authen: String): NetworkResponse<List<Report>>


    suspend fun getReportUpToLevel(obj: String,mode: String,monthYear: String,ids: String,idsAgencyLevel: String,authen: String): NetworkResponse<List<Report>>
    suspend fun getReportPassiveCommission(obj: String,mode: String,monthYear: String,authen: String): NetworkResponse<List<Report>>






}
