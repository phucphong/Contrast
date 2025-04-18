package com.itechpro.data.repository





import com.itechpro.data.api.NotificationAPI
import com.itechpro.domain.model.NetworkResponse
import com.itechpro.domain.model.Notification
import com.itechpro.domain.repository.NotificationRepository
import javax.inject.Inject

class NotificationRepositoryImpl @Inject constructor(
    private val api: NotificationAPI
) : NotificationRepository {






    override suspend fun getNotifications(startDate: String,endDate: String, authen: String): NetworkResponse<List<Notification>> {
        val response = api.getNotifications("laydsthongbao","modelaydsthongbao",startDate,endDate, authen)
        return if (response.isSuccessful) {
            NetworkResponse.Success(response.body() ?: emptyList())
        } else {
            NetworkResponse.Error("Lỗi: ${response.message()}")
        }
    }




    override suspend fun getNotificationDetail( obj: String, mode: String,ido: String,authen: String): NetworkResponse<List<Notification>> {
        val response = api.getNotificationDetail("layctthongbao","modelayctthongbao",ido, authen)
        return if (response.isSuccessful) {
            NetworkResponse.Success(response.body() ?: emptyList())
        } else {
            NetworkResponse.Error("Lỗi: ${response.message()}")
        }
    }

}
