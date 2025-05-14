package com.itechpro.data.repository

import com.itechpro.data.api.SettingAPi

import com.itechpro.domain.model.network.NetworkResponse
import com.itechpro.domain.model.Setting
import com.itechpro.domain.repository.SettingRepository


import javax.inject.Inject


class SettingRepositoryImpl @Inject constructor(
    private val api: SettingAPi
) : SettingRepository {


    override suspend fun getSettingViewOff(obj: String,mode: String): NetworkResponse<Setting?> {
        val response = api.getSettingViewOff(obj,mode)
        return if (response.isSuccessful) {
            NetworkResponse.Success(response.body() ?: null)
        } else {
            NetworkResponse.Error("Lỗi: ${response.message()}")
        }
    }


    override suspend fun getAppType(type: String): NetworkResponse<List<Setting>> {
        val response = api.getAppType(type)
        return if (response.isSuccessful) {
            NetworkResponse.Success(response.body() ?: emptyList())
        } else {
            NetworkResponse.Error("Lỗi: ${response.message()}")
        }
    }

    override suspend fun getVerificationCodes(): NetworkResponse<List<Setting>> {
        val response = api.getVerificationCodes()
        return if (response.isSuccessful) {
            NetworkResponse.Success(response.body() ?: emptyList())
        } else {
            NetworkResponse.Error("Lỗi: ${response.message()}")
        }
    }
  override suspend fun getVerificationCodesOnITP(key:String,code:String): NetworkResponse<List<Setting>> {
        val response = api.getVerificationCodesOnITP(key,code)
        return if (response.isSuccessful) {
            NetworkResponse.Success(response.body() ?: emptyList())
        } else {
            NetworkResponse.Error("Lỗi: ${response.message()}")
        }
    }








}
