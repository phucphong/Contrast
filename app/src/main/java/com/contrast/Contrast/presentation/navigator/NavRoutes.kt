package com.contrast.Contrast.presentation.navigator



sealed class NavRoutes(val route: String) {
    object Home : NavRoutes("home")
    object Category : NavRoutes("category")
    object Video : NavRoutes("video")
    object Location : NavRoutes("location")
    object StoreList : NavRoutes("storeList")
    object Membership : NavRoutes("membership")
    object Account : NavRoutes("account")
    object PersonalInfo : NavRoutes("personalInfo")

    // Affiliate + Product
    object AffiliateHome : NavRoutes("affiliateHome")
    object ProductByCategory : NavRoutes("product/{categoryId}") {
        fun createRoute(categoryId: String) = "product/$categoryId"
    }


    // Root Graph
    object MainRoot : NavRoutes("main")
    object AccountRoot : NavRoutes("accountRoot")
    object AffiliateRoot : NavRoutes("affiliateRoot")
}
