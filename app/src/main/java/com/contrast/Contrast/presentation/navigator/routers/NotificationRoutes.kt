package com.contrast.Contrast.presentation.navigator.routers

import android.net.Uri
import androidx.navigation.NavType
import androidx.navigation.navArgument

object NotificationRoutes {
    object Notifications {
        const val route = "notifications/{startDate}/{endDate}"
        fun withArgs(startDate: String, endDate: String): String {
            val safeStart = if (startDate.isNotBlank()) startDate else "1970-01-01"
            val safeEnd = if (endDate.isNotBlank()) endDate else "2099-12-31"
            return "notifications/${Uri.encode(safeStart)}/${Uri.encode(safeEnd)}"
        }
        val arguments = listOf(
            navArgument("startDate") { type = NavType.StringType },
            navArgument("endDate") { type = NavType.StringType }
        )
    }

    object Notification {
        const val route = "newNotification/{id}"
        fun withArgs(id: String) = "newNotification/${Uri.encode(id)}"
        val arguments = listOf(
            navArgument("id") { type = NavType.StringType }
        )
    }
}