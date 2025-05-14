package com.itechpro.domain.usecase.setting




import android.util.Log
import com.itechpro.domain.model.network.NetworkResponse

import com.itechpro.domain.model.Setting
import com.itechpro.domain.repository.SettingRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class SettingUseCase @Inject constructor(
    private val repository: SettingRepository,

    ) {


    fun getAppType(type: String): Flow<NetworkResponse<Setting>> {
        return flow {
            emit(NetworkResponse.Loading)
            when (val result = repository.getAppType(type)) {
                is NetworkResponse.Success -> {
                    val setting = result.data.firstOrNull()
                    if (setting != null) {
                        emit(NetworkResponse.Success(setting))
                    } else {
                        emit(NetworkResponse.Error("Danh sách rỗng"))
                    }
                }

                is NetworkResponse.Error -> emit(NetworkResponse.Error(result.message))
                is NetworkResponse.Loading -> {} // đã emit ở trên rồi
            }
        }.flowOn(Dispatchers.IO)
    }
    fun getVerificationCodes(): Flow<NetworkResponse<Setting>> {
        return flow {
            emit(NetworkResponse.Loading)
            when (val result = repository.getVerificationCodes()) {
                is NetworkResponse.Success -> {
                    val setting = result.data.firstOrNull()
                    if (setting != null) {
                        emit(NetworkResponse.Success(setting))
                    } else {
                        emit(NetworkResponse.Error("Danh sách rỗng"))
                    }
                }

                is NetworkResponse.Error -> emit(NetworkResponse.Error(result.message))
                is NetworkResponse.Loading -> {} // đã emit ở trên rồi
            }
        }.flowOn(Dispatchers.IO)
    }
    fun getVerificationCodesOnITP(key:String,code:String): Flow<NetworkResponse<Setting>> {
        return flow {
            emit(NetworkResponse.Loading)
            when (val result = repository.getVerificationCodesOnITP(key,code)) {
                is NetworkResponse.Success -> {
                    val setting = result.data.firstOrNull()
                    if (setting != null) {
                        emit(NetworkResponse.Success(setting))
                    } else {
                        emit(NetworkResponse.Error("Danh sách rỗng"))
                    }
                }

                is NetworkResponse.Error -> emit(NetworkResponse.Error(result.message))
                is NetworkResponse.Loading -> {} // đã emit ở trên rồi
            }
        }.flowOn(Dispatchers.IO)
    }

    fun getSettingViewOff(obj: String,mode: String): Flow<NetworkResponse<Setting?>> {
        return flow {
            emit(NetworkResponse.Loading)
            val result = repository.getSettingViewOff(obj, mode)
            emit(result)
        }.flowOn(Dispatchers.IO)
    }



}
