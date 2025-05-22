package com.contrast.Contrast.presentation.navigator.navgraph

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.contrast.Contrast.presentation.features.notification.ui.NotificationScreen
import com.contrast.Contrast.presentation.navigator.routers.NotificationRoutes


@RequiresApi(Build.VERSION_CODES.O)
fun NavGraphBuilder.registerNotificationRoutes(navController: NavHostController) {

    composable(
        route = NotificationRoutes.Notification.route,
        arguments = NotificationRoutes.Notification.arguments
    ) {



    }


    composable(
        route = NotificationRoutes.Notifications.route,
        arguments = NotificationRoutes.Notifications.arguments
    ) { backStackEntry ->
        val startDate = backStackEntry.arguments?.getString("startDate") ?: ""
        val endDate = backStackEntry.arguments?.getString("endDate") ?: ""
        NotificationScreen(navController, startDate,endDate)
    }
}