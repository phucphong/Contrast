package com.contrast.Contrast.presentation.navigator

import com.itechpro.domain.model.notifications.Notification
import com.itechpro.domain.model.navigationEvent.NotificationNavEvent


object NotificationEventMapper {

    private val crmMap = mapOf(
        "khachhang" to { obj: Notification -> NotificationNavEvent.GoToCustomerDetail(obj.iddoituong ?: "") },
        "cohoikinhdoanh" to { obj: Notification -> NotificationNavEvent.GoToOpportunityDetail(obj.iddoituong ?: "") }
    )

    private val taskMap = mapOf(
        "congvieckhac" to { obj: Notification -> NotificationNavEvent.GoToTaskDetail(obj.iddoituong ?: "") },
        "congvieccohoikinhdoanh" to { obj: Notification -> NotificationNavEvent.GoToTaskDetail(obj.iddoituong ?: "") },
        "congviecduan" to { obj: Notification -> NotificationNavEvent.GoToTaskDetail(obj.iddoituong ?: "") }
    )

    private val projectMap = mapOf(
        "duan" to { obj: Notification -> NotificationNavEvent.GoToProjectDetail(obj.iddoituong ?: "") }
    )

    private val orderMap = mapOf(
        "thongbaodonhangmoi" to { obj: Notification -> NotificationNavEvent.GoToOrderDetail(obj.iddoituong ?: "") }
    )

    private val allMap: Map<String, (Notification) -> NotificationNavEvent> =
        crmMap + taskMap + projectMap + orderMap

    fun getEventFromNotification(obj: Notification): NotificationNavEvent {
        return allMap[obj.loai]?.invoke(obj) ?: NotificationNavEvent.Unknown
    }
}
