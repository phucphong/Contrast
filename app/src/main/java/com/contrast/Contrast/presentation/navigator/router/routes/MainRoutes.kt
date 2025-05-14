package com.contrast.Contrast.presentation.navigator.router.routes

import android.net.Uri
import androidx.navigation.NavType
import androidx.navigation.navArgument

object MainRoutes {



    object Category {
        const val route = "category"
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
