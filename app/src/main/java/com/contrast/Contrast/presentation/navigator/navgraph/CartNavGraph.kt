package com.contrast.Contrast.presentation.navigator.navgraph

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.contrast.Contrast.presentation.features.cart.ui.CartScreen
import com.contrast.Contrast.presentation.features.paymentProduct.PaymentScreen
import com.contrast.Contrast.presentation.navigator.router.routes.CartRoutes


@RequiresApi(Build.VERSION_CODES.O)
fun NavGraphBuilder.registerCartRoutes(navController: NavHostController) {
    composable(CartRoutes.Carts.route) {  CartScreen(navController) }


    composable(
        route = CartRoutes.Payment.route,
        arguments = CartRoutes.Payment.arguments
    ) { backStackEntry ->
        val totalIntoMoney = backStackEntry.arguments?.getString("totalIntoMoney") ?: ""
        val oderKey = backStackEntry.arguments?.getString("oderKey") ?: ""
        val idOder = backStackEntry.arguments?.getString("idOder") ?: ""
        val discount = backStackEntry.arguments?.getString("discount") ?: ""
        val address = backStackEntry.arguments?.getString("address") ?: ""
        val isOpportunity = backStackEntry.arguments?.getString("isOpportunity") ?: ""
        PaymentScreen(navController, totalIntoMoney,oderKey,idOder, discount,address,isOpportunity)
    }


}