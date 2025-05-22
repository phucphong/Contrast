package com.contrast.Contrast.presentation.navigator.routers


import android.net.Uri
import androidx.navigation.NavType
import androidx.navigation.navArgument

object ProductRoutes {
    object ProductByCategory {
        private const val baseRoute = "product"
        const val route = "$baseRoute/{categoryId}"
        fun withArgs(categoryId: String) = "$baseRoute/${Uri.encode(categoryId)}"
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

    object AddReportProduct {
        const val route = "add_Report_Product/{id}/{idUnit}/{fileTxt}/{name}"
        fun withArgs(id: String, idUnit: String, fileTxt: String, name: String): String {
            return "add_Report_Product/${Uri.encode(id)}/${Uri.encode(idUnit)}/${Uri.encode(fileTxt)}/${Uri.encode(name)}"
        }
        val arguments = listOf(
            navArgument("id") { type = NavType.StringType },
            navArgument("idUnit") { type = NavType.StringType },
            navArgument("fileTxt") { type = NavType.StringType },
            navArgument("name") { type = NavType.StringType }
        )
    }



}