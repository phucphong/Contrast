package com.contrast.Contrast.presentation.navigator

import android.net.Uri
import androidx.navigation.NavType
import androidx.navigation.navArgument


sealed class NavRoutes(val route: String) {
    // Root Graph
    object MainRoot : NavRoutes("main")
    object AccountRoot : NavRoutes("accountRoot")
    object AffiliateRoot : NavRoutes("affiliateRoot")
    object Home : NavRoutes("home")
    object Category : NavRoutes("category")
    object Video : NavRoutes("video")
    object Location : NavRoutes("location")
    object StoreList : NavRoutes("storeList")
    object Membership : NavRoutes("membership")
    object Account : NavRoutes("account")
    object PersonalInfo : NavRoutes("personalInfo")
//    object Notifications : NavRoutes("notifications")
    object NotificationDetail : NavRoutes("notificationDetail")
    object OrderDetail : NavRoutes("notificationDetail")
    object CustomerDetail : NavRoutes("customerDetail")
    object OpportunityDetail : NavRoutes("opportunityDetail")
    object ProjectDetail : NavRoutes("projectDetail")
    object TaskDetail : NavRoutes("taskDetail")


    // Affiliate + Product
    object AffiliateHome : NavRoutes("affiliateHome")
    object ProductByCategory {
        private const val baseRoute = "product"
        const val route = "$baseRoute/{categoryId}"

        fun withArgs(categoryId: String): String {
            return "$baseRoute/${Uri.encode(categoryId)}"
        }

        val arguments = listOf(
            navArgument("categoryId") { type = NavType.StringType }
        )
    }


    object ProductDetail {
        const val route = "product_detail/{id}/{idUnit}"

        fun withArgs(id: String, idUnit: String): String {
            return "product_detail/${Uri.encode(id)}/${Uri.encode(idUnit)}"
        }

        val arguments = listOf(
            navArgument("id") { type = NavType.StringType },
            navArgument("idUnit") { type = NavType.StringType }
        )
    }

    object Evaluates {
        const val baseRoute = "product_evaluates"
        const val route = "$baseRoute/{id}"

        fun withArgs(id: String): String {
            return "$baseRoute/${Uri.encode(id)}"
        }

        val arguments = listOf(
            navArgument("id") { type = NavType.StringType }
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
        const val route = "add_evaluate/{id}/{fileTxt}/{name}"
        val arguments = listOf(
            navArgument("id") { type = NavType.StringType },
            navArgument("fileTxt") { type = NavType.StringType },
            navArgument("name") { type = NavType.StringType }
        )
        fun withArgs(id: String, fileTxt: String, name: String): String {
            return "add_evaluate/${Uri.encode(id)}/${Uri.encode(fileTxt)}/${Uri.encode(name)}"
        }

    }

    object AddServiceRequest {
        const val route = "add_service_request/{id}/{serviceName}/{idUnit}/{discount}"

        fun withArgs(id: String, serviceName: String, idUnit: String, discount: String): String {
            return "add_service_request/${Uri.encode(id)}/${Uri.encode(serviceName)}/${Uri.encode(idUnit)}/${Uri.encode(discount)}"
        }

        val arguments = listOf(
            navArgument("id") { type = NavType.StringType },
            navArgument("serviceName") { type = NavType.StringType },
            navArgument("idUnit") { type = NavType.StringType },
            navArgument("discount") { type = NavType.StringType }
        )
    }

    object Notifications {
        const val route = "notifications/{startDate}/{endDate}"

        fun withArgs(startDate: String, endDate: String): String {
            return "notifications/${Uri.encode(startDate)}/${Uri.encode(endDate)}"
        }

        val arguments = listOf(
            navArgument("startDate") { type = NavType.StringType },
            navArgument("endDate") { type = NavType.StringType }
        )
    }


}
