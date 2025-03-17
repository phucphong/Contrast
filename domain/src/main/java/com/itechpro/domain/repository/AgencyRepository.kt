package com.itechpro.domain.repository

import com.itechpro.domain.model.Agency

interface AgencyRepository {
    suspend fun getAgencys(levelChild: String,authen:String): List<Agency>
}