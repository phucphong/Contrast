package com.itechpro.domain.repository



import com.itechpro.domain.model.NetworkResponse
import com.itechpro.domain.model.notifications.Notification

interface NotificationRepository {


    suspend fun getNotifications(

        startDate: String,
        endDate: String,
        authen: String
    ): NetworkResponse<List<Notification>>


    suspend fun getNotificationDetail(
        obj: String,
        mode: String,
        ido: String,
        authen: String
    ): NetworkResponse<List<Notification>>




}
