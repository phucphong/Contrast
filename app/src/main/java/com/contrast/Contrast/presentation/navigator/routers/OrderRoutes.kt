package com.contrast.Contrast.presentation.navigator.routers

import android.net.Uri
import androidx.navigation.NavType
import androidx.navigation.navArgument

sealed class OrderRoutes(val route: String) {
    object OderDetail : OrderRoutes("oderDetail/{id}/{type}") {
        fun withArgs(id: String,type: String): String {
            return "oderDetail/${Uri.encode(id)}/${Uri.encode(type)}"
        }
        val arguments = listOf(
            navArgument("id") { type = NavType.StringType },
            navArgument("type") { type = NavType.StringType },

        )
    }
}