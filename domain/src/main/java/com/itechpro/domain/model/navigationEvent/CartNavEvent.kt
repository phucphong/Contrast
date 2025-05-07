package com.itechpro.domain.model.navigationEvent





sealed class CartNavEvent : NavEvent {
    object GoToCats : CartNavEvent()  // ✅ KHÔNG cần tham số nữa
    data class GoToPayment(val  totalIntoMoney :String,val  discount :String,val  address :String,val  isOpportitue :String) : CartNavEvent()
    // CRM




    object None : ProductNavEvent() // trạng thái mặc định
}
