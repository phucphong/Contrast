package com.contrast.Contrast.presentation.navigator.router


sealed class AccountRoutes(val route: String) {
    object Account : AccountRoutes("account")
    object PersonalInfo : AccountRoutes("personalInfo")
    object Carts : AccountRoutes("carts")
    object Membership : AccountRoutes("membership")
    object Logout : AccountRoutes("logout")
}
