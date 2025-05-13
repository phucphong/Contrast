package com.contrast.Contrast.presentation.navigator.navgraph


import android.content.Intent
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.contrast.Contrast.presentation.features.affiliate.AffiliateMainScreen

import com.contrast.Contrast.presentation.features.splas.SplashScreen
import com.contrast.Contrast.presentation.features.splas.SplashViewModel
import com.contrast.Contrast.presentation.navigator.router.routes.MainRoutes


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun AppNavGraph(navController: NavHostController, startIntent: Intent?) {
    NavHost(navController = navController, startDestination = "splash") {

        composable("splash") {
            val viewModel: SplashViewModel = hiltViewModel()
            SplashScreen( navController, viewModel,startIntent)
        }


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
}
