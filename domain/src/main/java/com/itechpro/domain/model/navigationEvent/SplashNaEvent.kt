package com.itechpro.domain.model.navigationEvent







sealed class SplashNaEvent : NavEvent {

    data class GoToLogIn(
        val isClose: String,
        ) : ProductNavEvent()

    object GoToRegister : SplashNaEvent()

    object GoToDomain : SplashNaEvent()
    object GoToForgotPassword : SplashNaEvent()


    data class GoToMain(
        val id: String,
        val idUnit: String,
        val introducerId: String,
    ) : SplashNaEvent()
    data class ShowAffiliateInfo(val id: String, val idUnit: String,val introducerId: String, val domain: String) : SplashNaEvent()
    object None : SplashNaEvent() // trạng thái mặc định
}
