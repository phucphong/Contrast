package com.contrast.Contrast.presentation.navigator.routers


import android.net.Uri
import androidx.navigation.NavType
import androidx.navigation.navArgument

sealed class OpportunityRoutes(val route: String) {


    object Opportunity {
        private const val baseRoute = "opportunity"
        const val route = "$baseRoute/{type}/{title}/{customer}/{customerEdit}/{customerDelete}"
        fun withArgs(type: String, title: String, customer: String, customerEdit: String, customerDelete: String) =
            "$baseRoute/${Uri.encode(type)}/${Uri.encode(title)}" +
                    "/${Uri.encode(customer)}/${Uri.encode(customerEdit)}/${Uri.encode(customerDelete)}"

        val arguments = listOf(
            navArgument("type") { type = NavType.StringType },
            navArgument("title") { type = NavType.StringType },
            navArgument("customer") { type = NavType.StringType },
        )
    }

    object OpportunityDetail : OrderRoutes("opportunityDetail/{id}/{customer}/{customerEdit}") {
        fun withArgs(id: String,customer:String,customerEdit:String): String {
            return "opportunityDetail/${Uri.encode(id)}/${Uri.encode(customer)}/${Uri.encode(customerEdit)}"
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