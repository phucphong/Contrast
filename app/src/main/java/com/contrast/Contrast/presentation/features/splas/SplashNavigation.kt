package com.contrast.Contrast.presentation.features.splas

sealed class SplashNavigation {
    object GoToLogin : SplashNavigation()
    object GoToMain : SplashNavigation()
    data class ShowAffiliateInfo(val id: String, val idUnit: String,val introducerId: String, val domain: String) : SplashNavigation()

    data class OpenAffiliatePage(val id: String, val idUnit: String, val introducerId: String) : SplashNavigation()
}