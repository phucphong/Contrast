package com.contrast.Contrast.presentation.navigator.router

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
        fun withArgs(id: String, idUnit: String, introducerId: String) =
            "product_detail/${Uri.encode(id)}/${Uri.encode(idUnit)}/${Uri.encode(introducerId)}"
        val arguments = listOf(
            navArgument("id") { type = NavType.StringType },
            navArgument("idUnit") { type = NavType.StringType },
            navArgument("introducerId") { type = NavType.StringType }
        )
    }
}