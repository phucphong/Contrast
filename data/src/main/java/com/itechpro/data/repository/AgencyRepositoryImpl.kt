package com.itechpro.data.repository

import com.itechpro.data.api.AgencyAPI
import com.itechpro.domain.model.Agency
import com.itechpro.domain.repository.AgencyRepository
import javax.inject.Inject

// data/repository/AgencyRepositoryImpl.kt
class AgencyRepositoryImpl @Inject constructor(
    private val apiService: AgencyAPI
) : AgencyRepository {
    override suspend fun getAgencys(levelChild: String,authen: String): List<Agency> {
        val response = apiService.getAgencys("dailyaf", "laydanhsachdaily", levelChild, authen)
        return response.body() ?: emptyList()
    }



}