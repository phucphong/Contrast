package com.itechpro.domain.usecase.notification



import com.itechpro.domain.model.Category
import com.itechpro.domain.model.NetworkResponse
import com.itechpro.domain.model.News
import com.itechpro.domain.model.Notification
import com.itechpro.domain.repository.NewsRepository
import com.itechpro.domain.repository.NotificationRepository
import com.itechpro.domain.safeFlowCall
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class NotificationUseCase @Inject constructor(
    private val repository: NotificationRepository,

    ) {


    fun getNotifications(startDate: String,endDate: String, authen: String): Flow<NetworkResponse<List<Notification>>> {
        return flow {
            emit(NetworkResponse.Loading)

            val result = repository.getNotifications(startDate,endDate, authen)

            emit(result)
        }.flowOn(Dispatchers.IO)
    }

  


    fun getNotificationDetail(
        ido: String,
        authen: String
    ): Flow<NetworkResponse<Notification>> = safeFlowCall {
        val response = repository.getNotificationDetail("khachhang", "getbyid", ido, authen)
        when (response) {
            is NetworkResponse.Success -> {
                val customer = response.data.firstOrNull()
                customer?.let { NetworkResponse.Success(it) }
                    ?: NetworkResponse.Error("Không tìm thấy dữ liệu khách hàng")
            }

            is NetworkResponse.Error -> NetworkResponse.Error(response.message)
            is NetworkResponse.Loading -> NetworkResponse.Loading
        }
    }

}
