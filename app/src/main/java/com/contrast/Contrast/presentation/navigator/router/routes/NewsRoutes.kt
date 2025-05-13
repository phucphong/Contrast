package com.contrast.Contrast.presentation.navigator.router.routes

import android.net.Uri
import androidx.navigation.NavType
import androidx.navigation.navArgument


object NewsRoutes {
    object NewsList {
        const val route = "news_list"
    }


    object NewsDetail {
        const val route = "newDetail/{id}"
        fun withArgs(id: String) = "newDetail/${Uri.encode(id)}"
        val arguments = listOf(
            navArgument("id") { type = NavType.StringType }
        )
    }
}