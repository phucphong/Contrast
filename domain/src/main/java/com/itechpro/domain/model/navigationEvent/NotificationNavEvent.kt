package com.itechpro.domain.model.navigationEvent



sealed class NotificationNavEvent : NavEvent {
    data class GoToNotifications(val  startDate :String,val  endDate :String) : NotificationNavEvent()
    // CRM
    data class GoToCustomerDetail(val id: String) : NotificationNavEvent()
    data class GoToOpportunityDetail(val id: String) : NotificationNavEvent()

    // Task
    data class GoToTaskDetail(val id: String) : NotificationNavEvent()

    // Project
    data class GoToProjectDetail(val id: String) : NotificationNavEvent()

    // Order
    data class GoToOrderDetail(val id: String) : NotificationNavEvent()

    // Default fallback
    object Unknown : NotificationNavEvent()
}
