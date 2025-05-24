package com.contrast.Contrast.presentation.navigator.navgraph

import android.os.Build
import androidx.annotation.RequiresApi

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable

import com.contrast.Contrast.presentation.features.product.detail.ProductDetailScreen
import com.contrast.Contrast.presentation.features.report.add_report_product.AddReportProductScreen

import com.contrast.Contrast.presentation.navigator.routers.ProductRoutes


@RequiresApi(Build.VERSION_CODES.O)
fun NavGraphBuilder.registerProductRoutes(navController: NavHostController) {

    registerCategoryProductRoutes(navController)

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