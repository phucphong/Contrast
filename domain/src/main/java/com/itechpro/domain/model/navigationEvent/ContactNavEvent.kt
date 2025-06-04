package com.itechpro.domain.model.navigationEvent




sealed class ContactNavEvent: NavEvent {
    data class GoToContactOpportunityProject(
        val id: String,
    ) : ContactNavEvent()

    data class GoToContactDetail(
        val id: String,
        val customer: String,
        val customerEdit: String,
    ) : ContactNavEvent()

    data class GoToEdit(
        val id: String,
    ) : ContactNavEvent()

    object None : ContactNavEvent() // trạng thái mặc định
}
