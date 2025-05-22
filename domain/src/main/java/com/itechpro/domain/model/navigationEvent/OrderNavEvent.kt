package com.itechpro.domain.model.navigationEvent


sealed class OrderNavEvent: NavEvent {
    data class GoToOderDetail(val id: String,val type: String) : OrderNavEvent()



    object None : OrderNavEvent() // trạng thái mặc định
}
