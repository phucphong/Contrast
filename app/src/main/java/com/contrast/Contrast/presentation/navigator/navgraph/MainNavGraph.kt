package com.contrast.Contrast.presentation.navigator.navgraph

import android.os.Build
import androidx.annotation.RequiresApi

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable

import com.contrast.Contrast.presentation.features.affiliate.AffiliateMainScreen

import com.contrast.Contrast.presentation.navigator.router.routes.MainRoutes


@RequiresApi(Build.VERSION_CODES.O)
fun NavGraphBuilder.registerMainRoutes(navController: NavHostController
) {



    composable(
        route = MainRoutes.Main.route,
        arguments = MainRoutes.Main.arguments
    ) { backStackEntry ->
        val id = backStackEntry.arguments?.getString("id") ?: ""
        val idUnit = backStackEntry.arguments?.getString("idUnit") ?: ""
        val introducerId = backStackEntry.arguments?.getString("introducerId") ?: ""
        AffiliateMainScreen(navController, id, idUnit,introducerId)
    }


}