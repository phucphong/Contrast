package com.contrast.Contrast.presentation.navigator.routers

import android.net.Uri
import androidx.navigation.NavType
import androidx.navigation.navArgument


object ReportPersonalSalesRoutes {
    object PersonalConsumptionSales {
        private const val baseRoute = "personalConsumptionSales"
        const val route = "$baseRoute/{title}"
        fun withArgs(title: String) = "$baseRoute/${Uri.encode(title)}"
        val arguments = listOf(
            navArgument("title") { type = NavType.StringType }
        )
    }
        object UpToLevelSales {
        private const val baseRoute = "upToLevelSales"
        const val route = "$baseRoute/{type}/{title}"
        fun withArgs(type: String,title: String) = "$baseRoute/${Uri.encode(type)}/${Uri.encode(title)}"
        val arguments = listOf(
            navArgument("type") { type = NavType.StringType },
            navArgument("title") { type = NavType.StringType }
        )
    }


    object PassiveCommissionReport {
        private const val baseRoute = "passiveCommissionReport"
        const val route = "$baseRoute/{title}"
        fun withArgs(title: String) = "$baseRoute/${Uri.encode(title)}"
        val arguments = listOf(
            navArgument("title") { type = NavType.StringType }
        )
    }

    object SalesReportByAgency {
        private const val baseRoute = "salesReportByAgency"
        const val route = "$baseRoute/{title}"
        fun withArgs(title: String) = "$baseRoute/${Uri.encode(title)}"
        val arguments = listOf(
            navArgument("title") { type = NavType.StringType }
        )
    }


    object RankAdvancementBonusReport {
        private const val baseRoute = "rankAdvancementBonusReport"
        const val route = "$baseRoute/{type}/{title}"
        fun withArgs(type: String,title: String) = "$baseRoute/${Uri.encode(type)}/${Uri.encode(title)}"
        val arguments = listOf(
            navArgument("type") { type = NavType.StringType }
            ,
            navArgument("title") { type = NavType.StringType }
        )
    }

}