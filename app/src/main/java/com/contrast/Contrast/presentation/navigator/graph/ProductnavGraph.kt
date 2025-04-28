package com.contrast.Contrast.presentation.navigator.graph




import android.os.Build
import androidx.annotation.RequiresApi
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.contrast.Contrast.presentation.features.account.ui.AccountScreen
import com.contrast.Contrast.presentation.features.affiliate.category.CategoryAffiliatePage
import com.contrast.Contrast.presentation.features.cart.ui.CartScreen
import com.contrast.Contrast.presentation.features.product.detail.ProductDetailScreen

import com.contrast.Contrast.presentation.navigator.NavRoutes
@RequiresApi(Build.VERSION_CODES.O)
fun NavGraphBuilder.productnavGraph(navController: NavHostController) {
    navigation(
        startDestination = NavRoutes.AffiliateHome.route,
        route = NavRoutes.HomeRoot.route // ✅ route = review_root
    ) {



        composable(
            route = NavRoutes.ProductDetail.route,
            arguments = NavRoutes.ProductDetail.arguments
        ) { backStackEntry ->
            val id = backStackEntry.arguments?.getString("id") ?: ""
            val idUnit = backStackEntry.arguments?.getString("idUnit") ?: ""
            val introducerId = backStackEntry.arguments?.getString("introducerId") ?: ""
            ProductDetailScreen(navController, id, idUnit,introducerId)
        }




        composable(
            route = NavRoutes.ProductByCategory.route, arguments = NavRoutes.ProductByCategory.arguments
        ) { backStackEntry ->
            val categoryId = backStackEntry.arguments?.getString("categoryId") ?: ""
            CategoryAffiliatePage(navController, categoryId)
        }


        composable(
            route = NavRoutes.AddServiceRequest.route, arguments = NavRoutes.AddServiceRequest.arguments
        ) { backStackEntry ->
            val idService = backStackEntry.arguments?.getString("idService") ?: ""
            CategoryAffiliatePage(navController, idService)
        }

    }
}
