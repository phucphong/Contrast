package com.itechpro.domain.model.navigationEvent






sealed class NewsNavEvent : NavEvent {
    data class GoToNewDetail(val id:String) : NewsNavEvent()

    object None : NewsNavEvent() // trạng thái mặc định
}
