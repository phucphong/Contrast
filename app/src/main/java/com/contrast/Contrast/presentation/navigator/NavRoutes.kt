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
    object category : NavRoutes("category")
    object Video : NavRoutes("video")
    object Location : NavRoutes("location")
    object StoreList : NavRoutes("storeList")
    object Membership : NavRoutes("membership")
    object Account : NavRoutes("account")
    object Carts : NavRoutes("carts")

    object PersonalInfo : NavRoutes("personalInfo")
//    object Notifications : NavRoutes("notifications")
    object NotificationDetail : NavRoutes("notificationDetail")
    object OrderDetail : NavRoutes("notificationDetail")
    object CustomerDetail : NavRoutes("customerDetail")
    object OpportunityDetail : NavRoutes("opportunityDetail")
    object ProjectDetail : NavRoutes("projectDetail")
    object TaskDetail : NavRoutes("taskDetail")
    object ReviewRoot : NavRoutes("review_root")
    object HomeRoot : NavRoutes("review_root")


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
        const val route = "product_detail/{id}/{idUnit}/{introducerId}"

        fun withArgs(id: String, idUnit: String, introducerId: String): String {
            return "product_detail/${Uri.encode(id)}/${Uri.encode(idUnit)}/${Uri.encode(introducerId)}"
        }

        val arguments = listOf(
            navArgument("id") { type = NavType.StringType },
            navArgument("idUnit") { type = NavType.StringType },
            navArgument("introducerId") { type = NavType.StringType },
        )
    }

    object Payment {
       
        const val route = "payment/{totalIntoMoney}/{discount}/{address}/{isOpportitue}"

        fun withArgs(totalIntoMoney: String, discount: String, address: String, isOpportitue: String): String {
            return "payment/${Uri.encode(totalIntoMoney)}/${Uri.encode(discount)}/${Uri.encode(address)}/${Uri.encode(isOpportitue)}"
        }

        val arguments = listOf(
            navArgument("totalIntoMoney") { type = NavType.StringType },
            navArgument("discount") { type = NavType.StringType },
            navArgument("address") { type = NavType.StringType },
            navArgument("isOpportitue") { type = NavType.BoolType },
        )
    }




    object Reviews {
        const val baseRoute = "product_Reviews"
        const val route = "$baseRoute/{id}"

        fun withArgs(id: String): String {
            return "$baseRoute/${Uri.encode(id)}"
        }

        val arguments = listOf(
            navArgument("id") { type = NavType.StringType }
        )
    }

    object MediaPicker {
        const val route = "media_picker/{maxCount}/{allowImage}/{allowVideo}/{compressedFiles}"
        val arguments = listOf(
            navArgument("maxCount") { type = NavType.IntType },
            navArgument("allowImage") { type = NavType.BoolType },
            navArgument("allowVideo") { type = NavType.BoolType },
            navArgument("compressedFiles") { type = NavType.BoolType }
        )

        fun withArgs(maxCount: Int, allowImage: Boolean, allowVideo: Boolean, compressedFiles: Boolean) =
            "media_picker/$maxCount/$allowImage/$allowVideo/$compressedFiles"
    }
    object AddReview {
        const val route = "add_Review/{id}/{idUnit}/{fileTxt}/{name}"
        val arguments = listOf(
            navArgument("id") { type = NavType.StringType },
            navArgument("idUnit") { type = NavType.StringType },
            navArgument("fileTxt") { type = NavType.StringType },
            navArgument("name") { type = NavType.StringType }
        )
        fun withArgs(id: String,idUnit: String, fileTxt: String, name: String): String {
            return "add_Review/${Uri.encode(id)}/${Uri.encode(idUnit)}/${Uri.encode(fileTxt)}/${Uri.encode(name)}"
        }

    }
    object AddReportProduct {
        const val route = "add_Report_Product/{id}/{idUnit}/{fileTxt}/{name}"
        val arguments = listOf(
            navArgument("id") { type = NavType.StringType },
            navArgument("idUnit") { type = NavType.StringType },
            navArgument("fileTxt") { type = NavType.StringType },
            navArgument("name") { type = NavType.StringType }
        )
        fun withArgs(id: String,idUnit: String, fileTxt: String, name: String): String {
            return "add_Report_Product/${Uri.encode(id)}/${Uri.encode(idUnit)}/${Uri.encode(fileTxt)}/${Uri.encode(name)}"
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
