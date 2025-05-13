package com.contrast.Contrast.presentation.navigator.navgraph

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.contrast.Contrast.presentation.components.media.MediaCreateViewModel
import com.contrast.Contrast.presentation.features.affiliate.category.CategoryAffiliatePage
import com.contrast.Contrast.presentation.features.paymentProduct.PaymentScreen
import com.contrast.Contrast.presentation.features.product.detail.ProductDetailScreen
import com.contrast.Contrast.presentation.features.report.AddReportProductScreen
import com.contrast.Contrast.presentation.navigator.router.routes.AffiliateRoutes
import com.contrast.Contrast.presentation.navigator.router.routes.CartRoutes
import com.contrast.Contrast.presentation.navigator.router.routes.MainRoutes
import com.contrast.Contrast.presentation.navigator.router.routes.ProductRoutes


@RequiresApi(Build.VERSION_CODES.O)
fun NavGraphBuilder.registerProductRoutes(navController: NavHostController) {


    composable(
        route = ProductRoutes.ProductByCategory.route, arguments = ProductRoutes.ProductByCategory.arguments
    ) { backStackEntry ->
        val categoryId = backStackEntry.arguments?.getString("categoryId") ?: ""
        CategoryAffiliatePage(navController, categoryId)
    }


    composable(
        route = ProductRoutes.ProductDetail.route,
        arguments = ProductRoutes.ProductDetail.arguments
    ) { backStackEntry ->
        val id = backStackEntry.arguments?.getString("id") ?: ""
        val idUnit = backStackEntry.arguments?.getString("idUnit") ?: ""
        val introducerId = backStackEntry.arguments?.getString("introducerId") ?: ""
        ProductDetailScreen(navController, id, idUnit,introducerId)
    }


    composable(
        route = ProductRoutes.AddReportProduct.route,
        arguments = ProductRoutes.AddReportProduct.arguments
    ) { backStackEntry ->

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





}