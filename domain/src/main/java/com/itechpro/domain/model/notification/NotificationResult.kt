package com.itechpro.domain.model.notification

import com.itechpro.domain.model.Notification
import com.itechpro.domain.model.cart.CartItem




data class NotificationResult(
    val items: List<Notification>,
    val totalCount: Int
)