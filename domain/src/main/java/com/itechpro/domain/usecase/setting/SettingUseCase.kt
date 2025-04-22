package com.itechpro.domain.usecase.setting




import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import com.itechpro.domain.model.Category
import com.itechpro.domain.model.NetworkResponse

import com.itechpro.domain.model.Product
import com.itechpro.domain.model.PromoUiData
import com.itechpro.domain.model.Rotation
import com.itechpro.domain.model.Setting
import com.itechpro.domain.model.SliderHome
import com.itechpro.domain.repository.HomeAffiliateRepository
import com.itechpro.domain.repository.SettingRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import java.time.Duration
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import javax.inject.Inject

class SettingUseCase @Inject constructor(
    private val repository: SettingRepository,

    ) {


    fun getAppType(type: String): Flow<NetworkResponse<List<Setting>>> {
        return flow {
            emit(NetworkResponse.Loading)
            val startTime = System.currentTimeMillis()
            val result = repository.getAppType(type)
            val endTime = System.currentTimeMillis()
            Log.d("Timing", "📦 API getCategory response in ${endTime - startTime}ms")
            emit(result)
        }.flowOn(Dispatchers.IO)
    }

    fun getSettingViewOff(obj: String,mode: String): Flow<NetworkResponse<Setting?>> {
        return flow {
            emit(NetworkResponse.Loading)
            val startTime = System.currentTimeMillis()
            val result = repository.getSettingViewOff(obj, mode)

            val endTime = System.currentTimeMillis()
            Log.d("Timing", "📦 API getSettingViewOff response in ${endTime - startTime}ms")
            emit(result)
        }.flowOn(Dispatchers.IO)
    }



}
