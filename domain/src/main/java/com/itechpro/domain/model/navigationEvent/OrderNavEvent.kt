package com.itechpro.domain.model.navigationEvent

import com.itechpro.domain.model.profile.ProfileNaEvent


sealed class OrderNavEvent: NavEvent {
    data class GoToOderDetail(val id: String,val type: String) : OrderNavEvent()

    data class GoToOderType(
        val type: String,
        val title: String,
    ) : OrderNavEvent()

    object None : OrderNavEvent() // trạng thái mặc định
}
