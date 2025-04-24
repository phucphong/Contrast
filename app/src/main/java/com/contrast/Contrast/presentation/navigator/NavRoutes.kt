package com.contrast.Contrast.presentation.navigator

import android.net.Uri
import androidx.navigation.NavType
import androidx.navigation.navArgument


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
        const val baseRoute = "product_detail"
        const val fullRoute = "$baseRoute?id={id}&idUnit={idUnit}"

        fun createRoute(id: String, idUnit: String): String {
            return "$baseRoute?id=$id&idUnit=$idUnit"
        }

        val arguments = listOf(
            navArgument("id") { type = NavType.StringType; defaultValue = "" },
            navArgument("idUnit") { type = NavType.StringType; defaultValue = "" }
        )
    }
    object Evaluates {
        const val baseRoute = "product_Evaluates"
        const val fullRoute = "$baseRoute?id={id}"

        fun createRoute(id: String): String {
            return "$baseRoute?id=$id"
        }

        val arguments = listOf(
            navArgument("id") { type = NavType.StringType; defaultValue = "" },

        )
    }
    object MediaPicker {
        const val route = "media_picker/{maxCount}/{allowImage}/{allowVideo}"
        val arguments = listOf(
            navArgument("maxCount") { type = NavType.IntType },
            navArgument("allowImage") { type = NavType.BoolType },
            navArgument("allowVideo") { type = NavType.BoolType }
        )

        fun withArgs(maxCount: Int, allowImage: Boolean, allowVideo: Boolean) =
            "media_picker/$maxCount/$allowImage/$allowVideo"
    }
    object AddEvaluate {
        const val route = "add_evaluate/{id}"
        fun withId(id: String) = "add_evaluate/$id"

        val arguments = listOf(
            navArgument("id") { type = NavType.StringType }
        )
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
