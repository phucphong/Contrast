package com.itechpro.domain.model.navigationEvent

import com.itechpro.domain.model.profile.ProfileNaEvent


sealed class OpportunityNavEvent: NavEvent {
    data class GoToOpportunityDetail(val id: String,

                                     val customerEdit: String,
        ) : OrderNavEvent()

    data class GoToOpportunity(
        val type: String,
        val title: String,
    ) : OpportunityNavEvent()

    data class GoToEdit(
        val id: String,
    ) : OpportunityNavEvent()

    object None : OrderNavEvent() // trạng thái mặc định
}
