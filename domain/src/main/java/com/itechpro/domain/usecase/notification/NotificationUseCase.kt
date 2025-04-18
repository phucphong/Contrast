package com.itechpro.domain.usecase.notification

import com.itechpro.domain.model.NetworkResponse
import com.itechpro.domain.model.Notification
import com.itechpro.domain.model.notification.NotificationResult
import com.itechpro.domain.repository.NotificationRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class NotificationUseCase @Inject constructor(
    private val repository: NotificationRepository,

    ) {

//
//    fun getNotifications(startDate: String,endDate: String, authen: String): Flow<NetworkResponse<List<Notification>>> {
//        return flow {
//            emit(NetworkResponse.Loading)
//            val result = repository.getNotifications(startDate,endDate, authen)
//            emit(result)
//        }.flowOn(Dispatchers.IO)
//    }


    fun getNotifications(startDate: String,endDate: String, authen: String): Flow<NetworkResponse<NotificationResult>> {
        return flow {
            emit(NetworkResponse.Loading)
            when (val result = repository.getNotifications(startDate, endDate ,authen)) {
                is NetworkResponse.Success -> {
                    val items = result.data
                    emit(NetworkResponse.Success(NotificationResult(items, items.size)))
                }
                is NetworkResponse.Error -> {
                    emit(NetworkResponse.Error(result.message))
                }

                NetworkResponse.Loading -> {}
            }
        }.flowOn(Dispatchers.IO)
    }

    fun getNotificationDetail(ido: String, authen: String): Flow<NetworkResponse<Notification?>> {
        return flow {
            emit(NetworkResponse.Loading)
            when (val result = repository.getNotificationDetail("layctthongbao","modelayctthongbao",ido, authen)) {
                is NetworkResponse.Success -> {
                    val list: List<Notification> = result.data
                    val obj = list.firstOrNull()
                    emit(NetworkResponse.Success(obj))
                }

                is NetworkResponse.Error -> {
                    emit(NetworkResponse.Error(result.message))
                }

                else -> Unit
            }
        }.flowOn(Dispatchers.IO)
    }




}
