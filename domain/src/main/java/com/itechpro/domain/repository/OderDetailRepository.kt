package com.itechpro.domain.repository

import com.itechpro.domain.model.network.NetworkResponse
import com.itechpro.domain.model.oder.OderTable


interface OderDetailRepository {


    suspend fun getOderById(obj: String,mode: String,ido: String,authen: String): NetworkResponse<OderTable>




}
