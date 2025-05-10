package com.itechpro.domain.model.notification

import com.itechpro.domain.model.notifications.Notification


data class NotificationResult(
    val items: List<Notification>,
    val totalCount: Int
)