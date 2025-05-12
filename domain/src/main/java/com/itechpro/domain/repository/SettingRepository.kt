package com.itechpro.domain.repository


import com.itechpro.domain.model.network.NetworkResponse
import com.itechpro.domain.model.Setting


interface SettingRepository {



    suspend fun getSettingViewOff(obj: String,mode: String
    ): NetworkResponse<Setting?>

    suspend fun getAppType(type: String
    ): NetworkResponse<List<Setting>>





}
