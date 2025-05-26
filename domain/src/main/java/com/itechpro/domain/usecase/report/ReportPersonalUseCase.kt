package com.itechpro.domain.usecase.report


import android.util.Log
import com.itechpro.domain.model.category.Category
import com.itechpro.domain.model.network.NetworkResponse
import com.itechpro.domain.model.report.Report

import com.itechpro.domain.repository.report.ReportPersonalRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class ReportPersonalUseCase @Inject constructor(
    private val repository: ReportPersonalRepository,

    ) {


    fun getReportPersonalSales(
        obj: String,
        mode: String,
        startDate: String,
        endDate: String,
        authen: String
    ): Flow<NetworkResponse<Report?>> {
        return flow {
            emit(NetworkResponse.Loading)

            when (val result =
                repository.getReportPersonalSales(obj, mode, startDate, endDate, authen)) {
                is NetworkResponse.Success -> {
                    val opportunity = result.data.firstOrNull()
                    emit(NetworkResponse.Success(opportunity))
                }

                is NetworkResponse.Error -> {
                    emit(NetworkResponse.Error(result.message))
                }

                else -> {
                    emit(NetworkResponse.Error("Unknown error"))
                }
            }
        }.flowOn(Dispatchers.IO)
    }


    fun getReportPassiveCommission(
        monthYear: String,
        authen: String
    ): Flow<NetworkResponse<List<Report>>> {
        return flow {
            emit(NetworkResponse.Loading)

            when (val result = repository.getReportPassiveCommission(
                "dailyaf",
                "bchuonghoahongthudong",
                monthYear,
                authen
            )) {
                is NetworkResponse.Success -> {

                    emit(NetworkResponse.Success(result.data))
                }

                is NetworkResponse.Error -> {
                    emit(NetworkResponse.Error(result.message))
                }

                else -> {
                    emit(NetworkResponse.Error("Unknown error"))
                }
            }
        }.flowOn(Dispatchers.IO)
    }


    fun getReportUpToLevel(
        monthYear: String,
        ids: String,
        idsAgencyLevel: String,
        authen: String
    ): Flow<NetworkResponse<List<Report>>> {
        return flow {
            emit(NetworkResponse.Loading)
            Log.e("monthYear",monthYear)
            when (val result = repository.getReportUpToLevel(
                "dailyaf",
                "bcthuongthangcap",
                monthYear,
                ids,
                idsAgencyLevel,
                authen
            )) {
                is NetworkResponse.Success -> {

                    emit(NetworkResponse.Success(result.data))
                }

                is NetworkResponse.Error -> {
                    emit(NetworkResponse.Error(result.message))
                }

                else -> {
                    emit(NetworkResponse.Error("Unknown error"))
                }
            }
        }.flowOn(Dispatchers.IO)
    }


    fun getReportSalesReportByAgentByLevel(
        startDate: String, endDate: String, ids: String, idsAgencyLevel: String, authen: String
    ): Flow<NetworkResponse<List<Report>>> {
        return flow {
            emit(NetworkResponse.Loading)

            when (val result = repository.getReportSalesReportByAgentByLevel(
                "dailyaf",
                "bcdoanhsotheodailycapdo",
                startDate,
                endDate,
                ids,
                idsAgencyLevel,
                authen
            )) {
                is NetworkResponse.Success -> {

                    emit(NetworkResponse.Success(result.data))
                }

                is NetworkResponse.Error -> {
                    emit(NetworkResponse.Error(result.message))
                }

                else -> {
                    emit(NetworkResponse.Error("Unknown error"))
                }
            }
        }.flowOn(Dispatchers.IO)
    }
    fun generateCategory(

    ): List<Category> {

        return  listOf(Category(code = "original"), Category(code = "discount"))
    }










}
