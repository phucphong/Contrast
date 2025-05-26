package com.itechpro.data.repository.report










import com.itechpro.data.api.report.ReportPersonalAPI

import com.itechpro.domain.model.network.NetworkResponse
import com.itechpro.domain.model.report.Report
import com.itechpro.domain.repository.report.ReportPersonalRepository


import javax.inject.Inject


class ReportPersonalRepositoryImpl @Inject constructor(
    private val api: ReportPersonalAPI
) : ReportPersonalRepository {





    override suspend fun getReportPersonalSales(
        obj: String,
        mode: String,
        startDate: String,
        endDate: String,
        authen: String
    ): NetworkResponse<List<Report>> {
        val response = api.getReportPersonalSales(obj,mode,startDate,endDate,authen)
        return if (response.isSuccessful) {
            NetworkResponse.Success(response.body() ?: emptyList())
        } else {
            NetworkResponse.Error("Lỗi: ${response.message()}")
        }
    }

    override suspend fun getReportSalesReportByAgentByLevel(
        obj: String,
        mode: String,
        startDate: String,
        endDate: String,
        ids: String,
        idsAgencyLevel: String,
        authen: String
    ): NetworkResponse<List<Report>> {
        val response = api.getReportSalesReportByAgentByLevel(obj,mode,startDate,endDate,ids,idsAgencyLevel,authen)
        return if (response.isSuccessful) {
            NetworkResponse.Success(response.body() ?: emptyList())
        } else {
            NetworkResponse.Error("Lỗi: ${response.message()}")
        }
    }

    override suspend fun getReportUpToLevel(
        obj: String,
        mode: String,
        monthYear: String,
        ids: String,
        idsAgencyLevel: String,
        authen: String
    ): NetworkResponse<List<Report>> {
        val response = api.getReportUpToLevel(obj,mode,monthYear,ids,idsAgencyLevel,authen)
        return if (response.isSuccessful) {
            NetworkResponse.Success(response.body() ?: emptyList())
        } else {
            NetworkResponse.Error("Lỗi: ${response.message()}")
        }
    }

    override suspend fun getReportPassiveCommission(
        obj: String,
        mode: String,
        monthYear: String,
        authen: String
    ): NetworkResponse<List<Report>> {
        val response = api.getReportPassiveCommission(obj,mode,monthYear,authen)
        return if (response.isSuccessful) {
            NetworkResponse.Success(response.body() ?: emptyList())
        } else {
            NetworkResponse.Error("Lỗi: ${response.message()}")
        }
    }


}
