package com.contrast.Contrast.presentation.navigator.router.routes

import android.net.Uri
import androidx.navigation.NavType
import androidx.navigation.navArgument

object MainRoutes {

    object HomeRoot {
        const val route = "review_root"
    }

    object Category {
        const val route = "category"
    }

    object News {
        const val route = "news"
    }

    object Videos {
        const val route = "videos"
    }

    object Carts {
        const val route = "carts"
    }

    object Main {
        const val route = "main/{id}/{idUnit}/{introducerId}"

        fun withArgs(id: String, idUnit: String, introducerId: String): String {
            return "main/${Uri.encode(id)}/${Uri.encode(idUnit)}/${Uri.encode(introducerId)}"
        }

        val arguments = listOf(
            navArgument("id") { type = NavType.StringType },
            navArgument("idUnit") { type = NavType.StringType },
            navArgument("introducerId") { type = NavType.StringType }
        )
    }
}
