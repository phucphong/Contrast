package com.itechpro.domain.model.notifications

import com.itechpro.domain.model.navigationEvent.NavEvent
import com.itechpro.domain.model.navigationEvent.ProductNavEvent
import com.itechpro.domain.model.notifications.Notification

data class NotificationUiState(
    val isLoading: Boolean = false,
    val notifications: List<Notification> = emptyList(),
    val notificationDetail: Notification? = null,
    val validationError: String = "",
    val domain: String = "",
    val totalNotificationItems: Int = 0,
    val navEvent: NavEvent = ProductNavEvent.None,
)