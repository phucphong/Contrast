package com.contrast.Contrast.presentation.navigator.router

import android.net.Uri
import androidx.navigation.NavType
import androidx.navigation.navArgument

object NewsRoutes {
    const val News = "news"

    object NewDetail {
        const val route = "newDetail/{id}"
        fun withArgs(id: String): String = "newDetail/${Uri.encode(id)}"
        val arguments = listOf(
            navArgument("id") { type = NavType.StringType }
        )
    }
}