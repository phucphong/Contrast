package com.contrast.Contrast.presentation.navigator.routers

import android.net.Uri
import androidx.navigation.NavType
import androidx.navigation.navArgument

object ServiceRoutes {
    object AddServiceRequest {
        const val route = "add_service_request/{id}/{serviceName}/{idUnit}/{discount}"
        fun withArgs(id: String, serviceName: String, idUnit: String, discount: String): String {
            return "add_service_request/${Uri.encode(id)}/${Uri.encode(serviceName)}/${Uri.encode(idUnit)}/${Uri.encode(discount)}"
        }

        val arguments = listOf(
            navArgument("id") { type = NavType.StringType },
            navArgument("serviceName") { type = NavType.StringType },
            navArgument("idUnit") { type = NavType.StringType },
            navArgument("discount") { type = NavType.StringType }
        )
    }
}