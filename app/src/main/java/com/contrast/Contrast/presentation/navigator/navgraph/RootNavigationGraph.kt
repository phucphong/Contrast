package com.contrast.Contrast.presentation.navigator.navgraph


import android.os.Build
import androidx.annotation.RequiresApi
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.compose.runtime.Composable
import com.contrast.Contrast.presentation.navigator.router.routes.AffiliateRoutes
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun RootNavigationGraph(
    navController: NavHostController
) {
    NavHost(
        navController = navController,
        startDestination = AffiliateRoutes.AffiliateRoot.route

    ) {
        registerMainRoutes(navController)
        registerAuthRoutes(navController)
        registerCategoryProductRoutes(navController)
        registerNewsRoutes(navController)
        registerAccountRoutes(navController)
        registerProductRoutes(navController)
        registerReviewRoutes(navController)
        registerAffiliateRoutes(navController)
        registerCartRoutes(navController)
        registerNotificationRoutes(navController)
        registerServiceRequestRoutes(navController)

    }
}
