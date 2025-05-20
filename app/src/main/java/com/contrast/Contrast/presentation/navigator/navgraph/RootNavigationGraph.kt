package com.contrast.Contrast.presentation.navigator.navgraph


import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.contrast.Contrast.presentation.navigator.router.routes.AffiliateRoutes
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun RootNavigationGraph(
    navController: NavHostController
) {
    NavHost(
        navController = navController,
        startDestination = AffiliateRoutes.AffiliateRoot.route,
        modifier = Modifier.padding(0.dp)

    ) {

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
