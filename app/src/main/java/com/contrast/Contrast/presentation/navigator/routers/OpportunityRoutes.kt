package com.contrast.Contrast.presentation.navigator.routers


import android.net.Uri
import androidx.navigation.NavType
import androidx.navigation.navArgument

sealed class OpportunityRoutes(val route: String) {


    object Opportunity {
        private const val baseRoute = "opportunity"
        const val route = "$baseRoute/{type}/{title}"
        fun withArgs(type: String, title: String) =
            "$baseRoute/${Uri.encode(type)}/${Uri.encode(title)}"

        val arguments = listOf(
            navArgument("type") { type = NavType.StringType },
            navArgument("title") { type = NavType.StringType },
        )
    }

    object OpportunityDetail : OrderRoutes("opportunityDetail/{id}") {
        fun withArgs(id: String): String {
            return "opportunityDetail/${Uri.encode(id)}"
        }

        val arguments = listOf(
            navArgument("id") { type = NavType.StringType },


            )
    }
    object OpportunityEdit : OrderRoutes("opportunityEdit/{id}") {
        fun withArgs(id: String): String {
            return "opportunityEdit/${Uri.encode(id)}"
        }

        val arguments = listOf(
            navArgument("id") { type = NavType.StringType },


            )
    }
}