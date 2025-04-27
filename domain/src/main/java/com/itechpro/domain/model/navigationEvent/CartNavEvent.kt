package com.itechpro.domain.model.navigationEvent





sealed class CartNavEvent : NavEvent {
    object GoToCats : CartNavEvent()  // ✅ KHÔNG cần tham số nữa
    data class GoToPayment(val  startDate :String,val  endDate :String) : CartNavEvent()
    // CRM




    object None : ProductNavEvent() // trạng thái mặc định
}
