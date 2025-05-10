package com.contrast.Contrast.presentation.navigator.graph




import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.contrast.Contrast.presentation.components.media.MediaCreateViewModel
import com.contrast.Contrast.presentation.features.account.ui.AccountScreen
import com.contrast.Contrast.presentation.features.affiliate.category.CategoryAffiliatePage
import com.contrast.Contrast.presentation.features.cart.ui.CartScreen
import com.contrast.Contrast.presentation.features.notification.ui.NotificationScreen
import com.contrast.Contrast.presentation.features.paymentProduct.PaymentScreen
import com.contrast.Contrast.presentation.features.product.detail.ProductDetailScreen
import com.contrast.Contrast.presentation.features.register.ui.info.RegisterAccountScreen
import com.contrast.Contrast.presentation.features.report.AddReportProductScreen
import com.contrast.Contrast.presentation.features.review.ui.AddReviewScreen

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
            route = NavRoutes.Payment.route,
            arguments = NavRoutes.Payment.arguments
        ) { backStackEntry ->
            val totalIntoMoney = backStackEntry.arguments?.getString("totalIntoMoney") ?: ""
            val oderKey = backStackEntry.arguments?.getString("oderKey") ?: ""
            val idOder = backStackEntry.arguments?.getString("idOder") ?: ""
            val discount = backStackEntry.arguments?.getString("discount") ?: ""
            val address = backStackEntry.arguments?.getString("address") ?: ""
            val isOpportunity = backStackEntry.arguments?.getString("isOpportunity") ?: ""
            PaymentScreen(navController, totalIntoMoney,oderKey,idOder, discount,address,isOpportunity)
        }



        composable(
            route = NavRoutes.AddReportProduct.route,
            arguments = NavRoutes.AddReportProduct.arguments
        ) { backStackEntry ->
            val parentEntry = remember(backStackEntry) {
                navController.getBackStackEntry(NavRoutes.ReviewRoot.route)
            }
            val sharedViewModel = hiltViewModel<MediaCreateViewModel>(parentEntry)
            val id = backStackEntry.arguments?.getString("id") ?: ""
            val idUnit =backStackEntry.arguments?.getString("idUnit") ?: ""
            val fileTxt = backStackEntry.arguments?.getString("fileTxt") ?: ""
            val name = backStackEntry.arguments?.getString("name") ?: ""
            AddReportProductScreen(
                navHostController = navController,
                id = id,
                idUnit = idUnit,
                fileTxt = fileTxt,
                name = name

            )
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
