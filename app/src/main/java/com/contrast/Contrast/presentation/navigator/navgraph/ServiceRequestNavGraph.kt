package com.contrast.Contrast.presentation.navigator.navgraph

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.contrast.Contrast.presentation.features.affiliate.category.CategoryAffiliatePage
import com.contrast.Contrast.presentation.navigator.router.routes.ProductRoutes
import com.contrast.Contrast.presentation.navigator.router.routes.ServiceRequestRoutes


@RequiresApi(Build.VERSION_CODES.O)
fun NavGraphBuilder.registerServiceRequestRoutes(navController: NavHostController) {


    composable(
        route = ServiceRequestRoutes.AddServiceRequest.route, arguments = ServiceRequestRoutes.AddServiceRequest.arguments
    ) { backStackEntry ->
        val idService = backStackEntry.arguments?.getString("idService") ?: ""
        CategoryAffiliatePage(navController, idService)
    }

}