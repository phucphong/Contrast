package com.itechpro.domain.repository.report




import com.itechpro.domain.model.network.NetworkResponse
import com.itechpro.domain.model.report.Report


interface ReportPersonalRepository {


    suspend fun getReportPersonalSales(obj: String,mode: String,startDate: String,endDate: String,authen: String): NetworkResponse<List<Report>>
    suspend fun getReportSalesReportByAgentByLevel(obj: String,mode: String,startDate: String,endDate: String,ids: String,idsAgencyLevel: String,authen: String): NetworkResponse<List<Report>>


    suspend fun getReportUpToLevel(obj: String,mode: String,monthYear: String,ids: String,idsAgencyLevel: String,authen: String): NetworkResponse<List<Report>>
    suspend fun getReportPassiveCommission(obj: String,mode: String,monthYear: String,authen: String): NetworkResponse<List<Report>>






}
