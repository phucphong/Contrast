package com.itechpro.domain.model.navigationEvent





sealed class HomeNavEvent : NavEvent {
    object GoToHome  : HomeNavEvent()  // ✅ KHÔNG cần tham số nữa
    object GoToLogout  : HomeNavEvent()  // ✅ KHÔNG cần tham số nữa
    object None : HomeNavEvent() // trạng thái mặc định
}
