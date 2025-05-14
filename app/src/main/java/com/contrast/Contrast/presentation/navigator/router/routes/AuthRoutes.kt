package com.contrast.Contrast.presentation.navigator.router.routes

import android.net.Uri
import androidx.navigation.NavType
import androidx.navigation.navArgument


object AuthRoutes {



    object Login {
        private const val baseRoute = "login"
        const val route = "$baseRoute/{isClose}"
        fun withArgs(isClose: String) = "$baseRoute/${Uri.encode(isClose)}"
        val arguments = listOf(
            navArgument("isClose") { type = NavType.StringType }
        )
    }



    object Register {
        const val route = "register"
    }

    object ForgotPassword {
        const val route = "forgotPassword"
    }

    object Domain {
        const val route = "domain"
    }


}