package com.contrast.Contrast.presentation.navigator.routers




import android.net.Uri
import androidx.navigation.NavType
import androidx.navigation.navArgument

sealed class ContactRoutes(val route: String) {




    object ContactOpportunityProject : ContactRoutes("contactOpportunityProject/{id}/{type}") {
        fun withArgs(id: String, type:String): String {
            return "contactOpportunityProject/${Uri.encode(id)}/${Uri.encode(type)}"
        }

        val arguments = listOf(
            navArgument("id") { type = NavType.StringType },
            navArgument("type") { type = NavType.StringType },
            )
    }

    object ContactDetail : ContactRoutes("contactDetail/{id}/{customer}/{customerEdit}") {
        fun withArgs(id: String, customer:String, customerEdit:String): String {
            return "contactDetail/${Uri.encode(id)}/${Uri.encode(customer)}/${Uri.encode(customerEdit)}"
        }

        val arguments = listOf(
            navArgument("id") { type = NavType.StringType },
            navArgument("customer") { type = NavType.StringType },
            navArgument("customerEdit") { type = NavType.StringType },
            )
    }
}