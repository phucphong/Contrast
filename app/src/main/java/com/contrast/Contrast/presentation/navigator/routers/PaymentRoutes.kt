package com.contrast.Contrast.presentation.navigator.routers

import android.net.Uri
import androidx.navigation.NavType
import androidx.navigation.navArgument

object PaymentRoutes {
    object Payment {
        const val route = "payment/{totalIntoMoney}/{oderKey}/{idOder}/{discount}/{address}/{isOpportunity}"
        fun withArgs(
            totalIntoMoney: String,
            oderKey: String,
            idOder: String,
            discount: String,
            address: String,
            isOpportunity: String
        ): String {
            return "payment/${Uri.encode(totalIntoMoney)}/${Uri.encode(oderKey)}/${Uri.encode(idOder)}/${Uri.encode(discount)}/${Uri.encode(address)}/${Uri.encode(isOpportunity)}"
        }

        val arguments = listOf(
            navArgument("totalIntoMoney") { type = NavType.StringType },
            navArgument("oderKey") { type = NavType.StringType },
            navArgument("idOder") { type = NavType.StringType },
            navArgument("discount") { type = NavType.StringType },
            navArgument("address") { type = NavType.StringType },
            navArgument("isOpportunity") { type = NavType.StringType },
        )
    }
}