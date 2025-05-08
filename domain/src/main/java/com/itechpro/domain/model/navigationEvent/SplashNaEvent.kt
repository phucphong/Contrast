package com.itechpro.domain.model.navigationEvent







sealed class SplashNaEvent : NavEvent {
    object GoToLogIn : CartNavEvent()  // ✅ KHÔNG cần tham số nữa


    data class GoToMain(
        val id: String,
        val idUnit: String,
        val introducerId: String,
    ) : ProductNavEvent()
    data class ShowAffiliateInfo(val id: String, val idUnit: String,val introducerId: String, val domain: String) : SplashNaEvent()
    object None : SplashNaEvent() // trạng thái mặc định
}
