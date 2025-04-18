package com.contrast.Contrast.presentation.navigator

import android.net.Uri


sealed class NavRoutes(val route: String) {
    object Home : NavRoutes("home")
    object Category : NavRoutes("category")
    object Video : NavRoutes("video")
    object Location : NavRoutes("location")
    object StoreList : NavRoutes("storeList")
    object Membership : NavRoutes("membership")
    object Account : NavRoutes("account")
    object PersonalInfo : NavRoutes("personalInfo")
    object Notifications : NavRoutes("notifications")
    object NotificationDetail : NavRoutes("notificationDetail")
    object OrderDetail : NavRoutes("notificationDetail")
    object CustomerDetail : NavRoutes("customerDetail")
    object OpportunityDetail : NavRoutes("opportunityDetail")
    object ProjectDetail : NavRoutes("projectDetail")
    object TaskDetail : NavRoutes("taskDetail")


    // Affiliate + Product
    object AffiliateHome : NavRoutes("affiliateHome")
    object ProductByCategory : NavRoutes("product/{categoryId}") {
        fun createRoute(categoryId: String) = "product/$categoryId"
    }


    object ProductDetail {
        const val route = "product_detail"

        fun createRoute(
            id: String,
            idUnit: String
        ): String {
            return "$route" +
                    "?id=$id" +
                    "&idUnit=$idUnit"
        }
    }
    object AddServiceRequest {
        const val route = "add_service_request"

        fun createRoute(
            categoryId: String,
            idService: String,
            serviceName: String,
            idUnit: String,
            discount: String
        ): String {
            return "$route" +
                    "?categoryId=$categoryId" +
                    "&idService=$idService" +
                    "&serviceName=${Uri.encode(serviceName)}" +
                    "&idUnit=$idUnit" +
                    "&discount=$discount"
        }
    }

    // Root Graph
    object MainRoot : NavRoutes("main")
    object AccountRoot : NavRoutes("accountRoot")
    object AffiliateRoot : NavRoutes("affiliateRoot")
}
