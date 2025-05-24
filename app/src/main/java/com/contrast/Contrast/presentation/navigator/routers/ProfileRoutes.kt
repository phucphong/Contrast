package com.contrast.Contrast.presentation.navigator.routers

import android.net.Uri
import androidx.navigation.NavType
import androidx.navigation.navArgument


object ProfileRoutes {



    object ShareQrcode {
        private const val baseRoute = "share_rcode"
        const val route = "$baseRoute/{title}"
        fun withArgs(title: String) = "$baseRoute/${Uri.encode(title)}"
        val arguments = listOf(
            navArgument("title") { type = NavType.StringType }
        )
    }


    object ShareProduct {
        private const val baseRoute = "share_product"
        const val route = "$baseRoute/{title}"
        fun withArgs(title: String) = "$baseRoute/${Uri.encode(title)}"
        val arguments = listOf(
            navArgument("title") { type = NavType.StringType }
        )
    }

    object InCome {
        private const val baseRoute = "income"
        const val route = "$baseRoute/{title}"
        fun withArgs(title: String) = "$baseRoute/${Uri.encode(title)}"
        val arguments = listOf(
            navArgument("title") { type = NavType.StringType }
        )
    }

    object ServiceProgress {
        private const val baseRoute = "service_progress"
        const val route = "$baseRoute/{title}"
        fun withArgs(title: String) = "$baseRoute/${Uri.encode(title)}"
        val arguments = listOf(
            navArgument("title") { type = NavType.StringType }
        )
    }

    object ServiceCalendar {
        private const val baseRoute = "service_calendar"
        const val route = "$baseRoute/{title}"
        fun withArgs(title: String) = "$baseRoute/${Uri.encode(title)}"
        val arguments = listOf(
            navArgument("title") { type = NavType.StringType }
        )
    }
    object SpaAtHome {
        private const val baseRoute = "spa_at_home"
        const val route = "$baseRoute/{title}"
        fun withArgs(title: String) = "$baseRoute/${Uri.encode(title)}"
        val arguments = listOf(
            navArgument("title") { type = NavType.StringType }
        )
    }
    object Agencys {
        private const val baseRoute = "agencys"
        const val route = "$baseRoute/{title}"
        fun withArgs(title: String) = "$baseRoute/${Uri.encode(title)}"
        val arguments = listOf(
            navArgument("title") { type = NavType.StringType }
        )
    }




    object ProductViewSave {
        private const val baseRoute = "productViewSave"
        const val route = "$baseRoute/{type}/{title}"
        fun withArgs(type: String,title: String) = "$baseRoute/${Uri.encode(type)}/${Uri.encode(title)}"
        val arguments = listOf(
            navArgument("type") { type = NavType.StringType },
            navArgument("title") { type = NavType.StringType }
        )
    }

    object OderType {
        private const val baseRoute = "oderType"
        const val route = "$baseRoute/{type}/{title}"
        fun withArgs(type: String,title: String) = "$baseRoute/${Uri.encode(type)}/${Uri.encode(title)}"
        val arguments = listOf(
            navArgument("type") { type = NavType.StringType }
            ,
            navArgument("title") { type = NavType.StringType }
        )
    }


}