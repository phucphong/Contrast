package com.itechpro.domain.repository


import com.itechpro.domain.model.Category
import com.itechpro.domain.model.NetworkResponse
import com.itechpro.domain.model.Product
import com.itechpro.domain.model.Rotation
import com.itechpro.domain.model.Setting
import com.itechpro.domain.model.SliderHome


interface SettingRepository {



    suspend fun getSettingViewOff(obj: String,mode: String
    ): NetworkResponse<Setting?>

    suspend fun getAppType(type: String
    ): NetworkResponse<List<Setting>>





}
